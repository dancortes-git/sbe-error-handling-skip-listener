package br.com.dcc.springbatchexamples.listener;

import org.springframework.batch.core.SkipListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorHandlingSkipListener implements SkipListener<String, String> {

	@Override
	public void onSkipInRead(Throwable t) {
	}

	@Override
	public void onSkipInWrite(String item, Throwable t) {
		log.info(">> Skiping item: {} because writing it caused the error: {}", item, t.getMessage());
	}

	@Override
	public void onSkipInProcess(String item, Throwable t) {
		log.info(">> Skiping item: {} because processing it caused the error: {}", item, t.getMessage());

	}

}
