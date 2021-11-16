package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.validator.ValidationHelper;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Objects;

@Service
class ValidationHelperImpl implements ValidationHelper {

	private static final String SINGLE_QUOTE = "'";

	@Override
	public void applyIsNotNullError(final @NonNull String targetName, final @NonNull ValidationResult result) {
		Objects.requireNonNull(targetName);

		result.setType(ValidationResultType.FAILURE);
		result.addError(quote(targetName) + " should be null");
	}

	@Override
	public void applyIsNullError(final @NonNull String targetName, final @NonNull ValidationResult result) {
		Objects.requireNonNull(targetName);

		result.setType(ValidationResultType.FAILURE);
		result.addError(quote(targetName) + " can't be null");
	}

	@Override
	public void applyIsEmptyError(final @NonNull String targetName, final @NonNull ValidationResult result) {
		Objects.requireNonNull(targetName);

		result.setType(ValidationResultType.FAILURE);
		result.addError(quote(targetName) + " can't be null or empty");
	}

	@Override
	public void applyIsBlankError(final @NonNull String targetName, final @NonNull ValidationResult result) {
		Objects.requireNonNull(targetName);

		result.setType(ValidationResultType.FAILURE);
		result.addError(quote(targetName) + " can't be null, empty or blank");
	}

	@Override
	public void applyMaxLengthExceededError(final @NonNull String targetName, final int maxLength,
											final @NonNull ValidationResult result) {
		Objects.requireNonNull(targetName);

		result.setType(ValidationResultType.FAILURE);
		result.addError(quote(targetName) + " can't exceed " + maxLength + " characters");
	}

	@Override
	public void applyFoundDuplicateError(final @NonNull String targetName, final @NonNull Object duplicateValue,
										 final @NonNull ValidationResult result) {
		Objects.requireNonNull(targetName);
		Objects.requireNonNull(duplicateValue);

		result.setType(ValidationResultType.FAILURE);
		result.addError("Found duplicate " + quote(duplicateValue) + " for " + quote(targetName));
	}

	@Override
	public void applyNotFoundByIdError(final @NonNull String targetName, final @NonNull Serializable id,
									   final @NonNull ValidationResult result) {
		Objects.requireNonNull(targetName);
		Objects.requireNonNull(id);

		result.setType(ValidationResultType.FAILURE);
		result.addError(quote(targetName) + " with id = " + quote(id) + " not found");
	}

	private String quote(final @NonNull Object target) {
		return SINGLE_QUOTE + target + SINGLE_QUOTE;
	}

}
