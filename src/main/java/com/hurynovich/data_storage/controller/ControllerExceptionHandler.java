package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.controller.exception.ControllerValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ControllerValidationException.class)
	public ResponseEntity<Object> handleControllerValidationException(
			final ControllerValidationException exception) {
		return new ResponseEntity<>(exception.getErrors(), exception.getStatus());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(
			final EntityNotFoundException exception) {
		return new ResponseEntity<>(Set.of(exception.getMessage()), HttpStatus.NOT_FOUND);
	}
}
