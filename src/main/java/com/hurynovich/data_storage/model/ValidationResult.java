package com.hurynovich.data_storage.model;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

	private ValidationResultType type = ValidationResultType.SUCCESS;

	private final List<Error> errors = new ArrayList<>();

	public ValidationResultType getType() {
		return type;
	}

	public void setType(final ValidationResultType type) {
		this.type = type;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void addError(final Error error) {
		errors.add(error);
	}

}
