package com.jeonsee.jeonsee.tasklet;

import com.jeonsee.jeonsee.model.Exhibition;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateDBTasklet implements Tasklet {
    private final MongoTemplate mongoTemplate;

    @Transactional
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Query q = new BasicQuery("{ endDate : { $lte : new Date() } }");
        mongoTemplate.findAllAndRemove(q, Exhibition.class);

        List<Exhibition> exhibitionList = (List<Exhibition>) (chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("info"));

        if(exhibitionList == null) return RepeatStatus.FINISHED;
        exhibitionList.forEach(exhibition -> mongoTemplate.save(exhibition));

        return RepeatStatus.FINISHED;
    }
}
