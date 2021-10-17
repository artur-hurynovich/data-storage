package com.hurynovich.data_storage.validator.model;

import java.util.HashSet;
import java.util.Set;

public class ValidationResult {

	private final Set<String> errors = new HashSet<>();

	private ValidationResultType type = ValidationResultType.SUCCESS;

	public Set<String> getErrors() {
		return errors;
	}

	public void addError(final String error) {
		errors.add(error);
	}

	public ValidationResultType getType() {
		return type;
	}

	public void setType(final ValidationResultType type) {
		this.type = type;
	}

}
