package com.jeonsee.jeonsee.jobs;

import com.jeonsee.jeonsee.tasklet.ExhibitionDetailRequestTasklet;
import com.jeonsee.jeonsee.tasklet.ExhibitionListRequestTasklet;
import com.jeonsee.jeonsee.tasklet.SendAlarmTasklet;
import com.jeonsee.jeonsee.tasklet.UpdateDBTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Config {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ExhibitionListRequestTasklet exhibitionListRequestTasklet;
    private final ExhibitionDetailRequestTasklet exhibitionDetailRequestTasklet;
    private final SendAlarmTasklet sendAlarmTasklet;
    private final UpdateDBTasklet updateDBTasklet;

    @Bean
    public Job exhibitionBatchJob() {
        return jobBuilderFactory.get("exhibitionBatchJob")
                .start(getList())
                .next(getDetail())
                .next(sendAlarm())
                .next(updateDb())
                .build();
    }

    @Bean
    public Step getList() {
        return stepBuilderFactory.get("getList")
                .tasklet(exhibitionListRequestTasklet)
                .build();
    }

    @Bean
    public Step getDetail() {
        return stepBuilderFactory.get("getDetail")
                .tasklet(exhibitionDetailRequestTasklet)
                .build();
    }

    @Bean
    public Step sendAlarm() {
        return stepBuilderFactory.get("sendAlarm")
                .tasklet(sendAlarmTasklet)
                .build();
    }

    @Bean
    public Step updateDb() {
        return stepBuilderFactory.get("updateDb")
                .tasklet(updateDBTasklet)
                .build();
    }

}