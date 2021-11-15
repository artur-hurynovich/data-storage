package com.hurynovich.data_storage.controller.model;

import com.hurynovich.data_storage.validator.model.ValidationResult;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.concurrent.Immutable;
import java.util.Objects;

@Immutable
public class GenericValidatedResponse<T> {

	private final ValidationResult validationResult;

	private final T body;

	public GenericValidatedResponse(final @NonNull ValidationResult validationResult, final @Nullable T body) {
		this.validationResult = Objects.requireNonNull(validationResult);
		this.body = body;
	}

	public ValidationResult getValidationResult() {
		return validationResult;
	}

	public T getBody() {
		return body;
	}

}
