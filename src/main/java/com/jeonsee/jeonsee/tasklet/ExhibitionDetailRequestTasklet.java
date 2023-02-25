package com.jeonsee.jeonsee.tasklet;

import com.jeonsee.jeonsee.model.Exhibition;
import com.jeonsee.jeonsee.model.PerformanceDetailDisplay;
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
import org.springframework.stereotype.Component;

import java.awt.datatransfer.SystemFlavorMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Component
@RequiredArgsConstructor
public class ExhibitionDetailRequestTasklet implements Tasklet {
    @Value("${api.key}")
    private String key;

    private final String baseUrl = "http://www.culture.go.kr/openapi/rest/publicperformancedisplays/d/?serviceKey=";
    private final HttpXmlParser<PerformanceDetailDisplay> performanceDetailDisplayParser = new HttpXmlParser<>();
    private final int rowPerPage = 100;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<Exhibition> newList = (List<Exhibition>) (chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("info"));

        try {
            for(int i = 0; i < newList.size(); i++){
                Exhibition exhibition = newList.get(i);
                PerformanceDetailDisplay performanceDetailDisplay = performanceDetailDisplayParser.parseDetail(getRequest(exhibition.getSeq()));
                if (!performanceDetailDisplay.getComMsgHeader().isValid()) {
                    System.out.println(performanceDetailDisplay.getComMsgHeader().getErrMsg());
                    throw new Exception("api response has problem");
                }
                exhibition.setDetail(performanceDetailDisplay.getMsgBody().getExhibitionDetail());
            }

            ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
            jobExecutionContext.put("info", newList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RepeatStatus.FINISHED;
    }

    private Request getRequest(int seq) {
        String uri = baseUrl + key + "&seq=" + seq;

        return new Request.Builder().get().url(uri).build();
    }
}
