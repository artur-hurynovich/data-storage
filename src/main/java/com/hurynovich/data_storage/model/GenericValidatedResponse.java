package com.hurynovich.data_storage.model;

import com.hurynovich.data_storage.validator.model.ValidationResult;

public class GenericValidatedResponse<T> {

	private final ValidationResult validationResult;

	private final T body;

	public GenericValidatedResponse(final ValidationResult validationResult, final T body) {
		this.validationResult = validationResult;
		this.body = body;
	}

	public ValidationResult getValidationResult() {
		return validationResult;
	}

	public T getBody() {
		return body;
	}

}
