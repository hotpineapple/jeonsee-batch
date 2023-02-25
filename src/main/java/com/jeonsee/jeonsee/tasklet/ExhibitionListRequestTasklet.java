package com.jeonsee.jeonsee.tasklet;

import com.jeonsee.jeonsee.model.Exhibition;
import com.jeonsee.jeonsee.model.PerformanceDisplay;
import com.jeonsee.jeonsee.util.HttpXmlParser;
import com.squareup.okhttp.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Component
@RequiredArgsConstructor
public class ExhibitionListRequestTasklet implements Tasklet {
    @Value("${api.key}")
    private String key;

    private final String baseUrl = "http://www.culture.go.kr/openapi/rest/publicperformancedisplays/period?serviceKey=";
    private final HttpXmlParser<PerformanceDisplay> performanceDisplayParser = new HttpXmlParser<>();
    private final int rowPerPage = 100;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final MongoTemplate mongoTemplate;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        try {
            PerformanceDisplay performanceDisplay = performanceDisplayParser.parseList(getRequest(0));
            if (!performanceDisplay.getComMsgHeader().isValid()) {
                throw new Exception(performanceDisplay.getComMsgHeader().getErrMsg());
            }

            int totalCnt = performanceDisplay.getMsgBody().getTotalCount();
            int totalPage = totalCnt % rowPerPage == 0 ? totalCnt / rowPerPage : totalCnt / rowPerPage + 1;

            List<Exhibition> exhibitionList = IntStream.rangeClosed(1, totalPage)
                    .mapToObj((page) -> getRequest(page))
                    .map(req -> performanceDisplayParser.parseList(req).getMsgBody().getExhibitionList())
                    .filter(exhibitions -> exhibitions != null)
                    .flatMap(exhibitions -> exhibitions.stream())
                    .filter(exhibition -> LocalDate.now().compareTo(exhibition.getEndDate()) <= 0)
                    .collect(Collectors.toList());

            List<Exhibition> oldList = mongoTemplate.findAll(Exhibition.class);
            List<Exhibition> newList = exhibitionList.stream().filter((ex) -> !oldList.contains(ex)).collect(Collectors.toList());

            ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
            jobExecutionContext.put("info", newList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RepeatStatus.FINISHED;
    }

    private Request getRequest(int page) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusYears(1);
        LocalDate endDate = now.plusYears(1);
        String uri = baseUrl + key + "&from=" + startDate.format(dateTimeFormatter) + "&to=" + endDate.format(dateTimeFormatter) + "&cPage=" + page + "&rows=" + rowPerPage;

        return new Request.Builder().get().url(uri).build();
    }
}
