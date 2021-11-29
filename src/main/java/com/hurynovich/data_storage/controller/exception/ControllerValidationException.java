package com.hurynovich.data_storage.controller.exception;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Set;

public class ControllerValidationException extends RuntimeException {

	private final HttpStatus status;

	private final Set<String> errors;

	public ControllerValidationException(final HttpStatus status, final Set<String> errors) {
		this.status = status;
		this.errors = Collections.unmodifiableSet(errors);
	}

	public ControllerValidationException(final Set<String> errors) {
		status = HttpStatus.BAD_REQUEST;
		this.errors = Collections.unmodifiableSet(errors);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public Set<String> getErrors() {
		return errors;
	}
}
