package com.hurynovich.data_storage.validator.model;

import org.springframework.lang.NonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ValidationResult {

	private final Set<String> errors = new HashSet<>();

	private ValidationResultType type = ValidationResultType.SUCCESS;

	public Set<String> getErrors() {
		return errors;
	}

	public void addError(final @NonNull String error) {
		errors.add(Objects.requireNonNull(error));
	}

	public ValidationResultType getType() {
		return type;
	}

	public void setType(final @NonNull ValidationResultType type) {
		this.type = Objects.requireNonNull(type);
	}

}
