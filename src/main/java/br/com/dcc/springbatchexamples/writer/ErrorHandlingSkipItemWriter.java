package br.com.dcc.springbatchexamples.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import br.com.dcc.springbatchexamples.exception.ErrorHandlingException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ErrorHandlingSkipItemWriter implements ItemWriter<String> {

	private int attemptCount = 0;

	@Override
	public void write(List<? extends String> items) throws Exception {
		for (String item : items) {
			if (item.equalsIgnoreCase("-84")) {
				throw new ErrorHandlingException("Write failed. Attempt: " + attemptCount);
			} else {
				log.info("Item: {}", item);
			}
		}

	}

}
