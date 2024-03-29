package com.example.springbatchguide.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleNextConditionalJobConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(SimpleNextConditionalJobConfiguration.class);
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	public SimpleNextConditionalJobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}

	@Bean
	public Job simpleNextConditionalJob() {
		return jobBuilderFactory.get("simpleNextConditionalJob")
			.start(conditionalJobStep1())
				.on("FAILED")
				.to(conditionalJobStep3())
				.on("*")
				.end()
			.from(conditionalJobStep1())
				.on("*")
				.to(conditionalJobStep2())
				.next(conditionalJobStep3())
				.on("*")
				.end()
			.end()
			.build();
	}

	@Bean
	public Step conditionalJobStep1() {
		return stepBuilderFactory.get("conditionalJobStep1")
			.tasklet(((contribution, chunkContext) -> {
				logger.info(">>> This is stepNextConditionalJob Step1");

				/**
				 * ExitStatus를 FAILED롤 설정한다
				 * 해당 Status를 보고 flow가 진행된다
				 * **/
				contribution.setExitStatus(ExitStatus.FAILED);
				return RepeatStatus.FINISHED;
			}))
			.build();
	}

	@Bean
	public Step conditionalJobStep2() {
		return stepBuilderFactory.get("conditionalJobStep2")
			.tasklet(((contribution, chunkContext) -> {
				logger.info(">>> This is stepNextConditionalJob Step2");
				return RepeatStatus.FINISHED;
			}))
			.build();
	}

	@Bean
	public Step conditionalJobStep3() {
		return stepBuilderFactory.get("conditionalJobStep2")
			.tasklet(((contribution, chunkContext) -> {
				logger.info(">>> This is stepNextConditionalJob Step3");
				return RepeatStatus.FINISHED;
			}))
			.build();
	}
}
