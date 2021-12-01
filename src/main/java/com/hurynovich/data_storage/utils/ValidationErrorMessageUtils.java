package com.hurynovich.data_storage.utils;

import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class ValidationErrorMessageUtils {

	private static final String SINGLE_QUOTE = "'";

	private ValidationErrorMessageUtils() {
		throw new AssertionError();
	}

	public static String buildIsNotNullErrorMessage(final @NonNull String targetName) {
		Objects.requireNonNull(targetName);

		return quote(targetName) + " should be null";
	}

	public static String buildIsNullErrorMessage(final @NonNull String targetName) {
		Objects.requireNonNull(targetName);

		return quote(targetName) + " can't be null";
	}

	public static String buildIsEmptyErrorMessage(final @NonNull String targetName) {
		Objects.requireNonNull(targetName);

		return quote(targetName) + " can't be null or empty";
	}

	public static String buildIsBlankErrorMessage(final @NonNull String targetName) {
		Objects.requireNonNull(targetName);

		return quote(targetName) + " can't be null, empty or blank";
	}

	public static String buildMaxLengthExceededErrorMessage(final @NonNull String targetName, final int maxLength) {
		Objects.requireNonNull(targetName);

		return quote(targetName) + " can't exceed " + maxLength + " characters";
	}

	public static String buildFoundDuplicateErrorMessage(final @NonNull String targetName, final @NonNull Object duplicateValue) {
		Objects.requireNonNull(targetName);
		Objects.requireNonNull(duplicateValue);

		return "Found duplicate " + quote(duplicateValue) + " for " + quote(targetName);
	}

	public static String buildNotFoundByIdErrorMessage(final @NonNull String targetName, final @NonNull Serializable id) {
		Objects.requireNonNull(targetName);
		Objects.requireNonNull(id);

		return quote(targetName) + " with id = " + quote(id) + " not found";
	}

	private static String quote(final @NonNull Object target) {
		return SINGLE_QUOTE + target + SINGLE_QUOTE;
	}
}
