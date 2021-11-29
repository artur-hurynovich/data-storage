package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.validator.ValidationErrorMessageBuilder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Objects;

@Service
class ValidationErrorMessageBuilderImpl implements ValidationErrorMessageBuilder {

	private static final String SINGLE_QUOTE = "'";

	@Override
	public String buildIsNotNullErrorMessage(final @NonNull String targetName) {
		Objects.requireNonNull(targetName);

		return quote(targetName) + " should be null";
	}

	@Override
	public String buildIsNullErrorMessage(final @NonNull String targetName) {
		Objects.requireNonNull(targetName);

		return quote(targetName) + " can't be null";
	}

	@Override
	public String buildIsEmptyErrorMessage(final @NonNull String targetName) {
		Objects.requireNonNull(targetName);

		return quote(targetName) + " can't be null or empty";
	}

	@Override
	public String buildIsBlankErrorMessage(final @NonNull String targetName) {
		Objects.requireNonNull(targetName);

		return quote(targetName) + " can't be null, empty or blank";
	}

	@Override
	public String buildMaxLengthExceededErrorMessage(final @NonNull String targetName, final int maxLength) {
		Objects.requireNonNull(targetName);

		return quote(targetName) + " can't exceed " + maxLength + " characters";
	}

	@Override
	public String buildFoundDuplicateErrorMessage(final @NonNull String targetName, final @NonNull Object duplicateValue) {
		Objects.requireNonNull(targetName);
		Objects.requireNonNull(duplicateValue);

		return "Found duplicate " + quote(duplicateValue) + " for " + quote(targetName);
	}

	@Override
	public String buildNotFoundByIdErrorMessage(final @NonNull String targetName, final @NonNull Serializable id) {
		Objects.requireNonNull(targetName);
		Objects.requireNonNull(id);

		return quote(targetName) + " with id = " + quote(id) + " not found";
	}

	private String quote(final @NonNull Object target) {
		return SINGLE_QUOTE + target + SINGLE_QUOTE;
	}

}
