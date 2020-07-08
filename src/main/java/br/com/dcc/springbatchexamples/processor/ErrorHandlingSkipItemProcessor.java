package br.com.dcc.springbatchexamples.processor;

import org.springframework.batch.item.ItemProcessor;

import br.com.dcc.springbatchexamples.exception.ErrorHandlingException;
import lombok.Data;

@Data
public class ErrorHandlingSkipItemProcessor implements ItemProcessor<String, String> {

	private int attemptCount = 0;

	@Override
	public String process(String item) throws Exception {
		if (item.equalsIgnoreCase("42")) {
			throw new ErrorHandlingException("Process failed. Attempt: " + attemptCount);
		} else {
			return String.valueOf(Integer.valueOf(item)*-1);
		}
	}

}