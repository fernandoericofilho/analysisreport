package com.example.analisysreport;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@SpringBootApplication
public class AnalysisReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalysisReportApplication.class, args);
	}

}
