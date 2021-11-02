package com.nttdata.nttdatadarmytasksms.controller;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerAdvisor {
	
	private final static Logger logger=LoggerFactory.getLogger(ControllerAdvisor.class);
	
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus (HttpStatus.NOT_FOUND)
	public void handleNoSuchElementException(NoSuchElementException e) {
		logger.info("No se ha encontrado el elemento");
	}

}
