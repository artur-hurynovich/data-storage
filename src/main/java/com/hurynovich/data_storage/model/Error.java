package com.hurynovich.data_storage.model;

public class Error {

	private final String errorCode;

	private final String errorMessage;

	public Error(final String errorCode, final String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
