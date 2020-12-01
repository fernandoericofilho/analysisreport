package com.example.analisysreport.batch;

import com.example.analisysreport.batch.listener.JobCompletionListener;
import com.example.analisysreport.batch.step.ReportProcessor;
import com.example.analisysreport.batch.step.ReportReader;
import com.example.analisysreport.batch.step.ReportWriter;
import com.example.analisysreport.vo.ReportVO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public ReportReader reportReader;

    @Autowired
    public ReportProcessor reportProcessor;

    @Autowired
    public ReportWriter reportWriter;

    @Bean
    public Job processJob() {
        return jobBuilderFactory.get("processJob")
                .incrementer(new RunIdIncrementer()).listener(listener())
                .flow(step1()).end().build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<List<Path>, ReportVO> chunk(1)
                .reader(reportReader).processor(reportProcessor)
                .writer(reportWriter).build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionListener();
    }

}
