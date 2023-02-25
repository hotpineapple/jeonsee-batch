package com.jeonsee.jeonsee.tasklet;

import com.jeonsee.jeonsee.model.Alarm;
import com.jeonsee.jeonsee.model.Exhibition;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SendAlarmTasklet implements Tasklet {
    private final MongoTemplate mongoTemplate;
    private final JavaMailSenderImpl mailSender;
    @Value("${spring.mail.username}")
    private String username;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<Alarm> alarmList = mongoTemplate.findAll(Alarm.class);
        List<Exhibition> exhibitionList = (List<Exhibition>) (chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("info"));

        for (Alarm alarm : alarmList) {
            for (Exhibition exhibition : exhibitionList) {
                if (exhibition.getTitle().contains(alarm.getWord())) {
                    SimpleMailMessage smm = new SimpleMailMessage();
                    smm.setFrom(username);
                    smm.setTo(alarm.getEmail());
                    smm.setSubject(exhibition.getTitle() + " 등록 알림");
                    smm.setText(exhibition.getDetail().toString());

                    mailSender.send(smm);
                }
            }
        }

        ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
        jobExecutionContext.put("info", exhibitionList);

        return RepeatStatus.FINISHED;
    }
}
