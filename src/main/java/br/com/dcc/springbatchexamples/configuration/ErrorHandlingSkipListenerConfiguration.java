package br.com.dcc.springbatchexamples.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.ListItemReader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.dcc.springbatchexamples.exception.ErrorHandlingException;
import br.com.dcc.springbatchexamples.listener.ErrorHandlingSkipListener;
import br.com.dcc.springbatchexamples.listener.SimpleJobListener;
import br.com.dcc.springbatchexamples.processor.ErrorHandlingSkipItemProcessor;
import br.com.dcc.springbatchexamples.writer.ErrorHandlingSkipItemWriter;

@Configuration
public class ErrorHandlingSkipListenerConfiguration {

	@Bean
	public ListItemReader<String> errorHandlingSkipListenerReader() {

		List<String> items = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			items.add(String.valueOf(i));
		}

		return new ListItemReader<>(items);

	}

	@Bean
	public ErrorHandlingSkipItemProcessor errorHandlingSkipListenerItemProcessor() {
		return new ErrorHandlingSkipItemProcessor();
	}

	@Bean
	public ErrorHandlingSkipItemWriter errorHandlingSkipListenerWriter() {
		return new ErrorHandlingSkipItemWriter();
	}

	@Bean
	public Step errorHandlingSkipListenerStep1(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("ErrorHandlingSkipListenerStep1")
				.<String, String>chunk(10)
				.reader(errorHandlingSkipListenerReader())
				.processor(errorHandlingSkipListenerItemProcessor())
				.writer(errorHandlingSkipListenerWriter())
				.faultTolerant()
				.skip(ErrorHandlingException.class)
				.skipLimit(15)
				.listener(new ErrorHandlingSkipListener())
				.build();
	}

	@Bean
	public Job errorHandlingSkipListenerJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		return jobBuilderFactory.get("ErrorHandlingSkipListenerJob")
				.start(errorHandlingSkipListenerStep1(stepBuilderFactory))
				.listener(new SimpleJobListener())
				.build();
	}

}
