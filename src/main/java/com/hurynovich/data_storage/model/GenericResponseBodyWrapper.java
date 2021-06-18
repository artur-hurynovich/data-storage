package com.hurynovich.data_storage.model;

public class GenericResponseBodyWrapper {

	private final ValidationResult validationResult;

	private final Object responseBody;

	public GenericResponseBodyWrapper(final ValidationResult validationResult, final Object responseBody) {
		this.validationResult = validationResult;
		this.responseBody = responseBody;
	}

	public ValidationResult getValidationResult() {
		return validationResult;
	}

	public Object getResponseBody() {
		return responseBody;
	}

}
