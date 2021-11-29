package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.validator.ValidationErrorMessageBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidationErrorMessageBuilderImplTest {

	private static final String TARGET_NAME = "targetName";
	private static final int MAX_LENGTH = 100;
	private static final String DUPLICATE = "duplicate";
	private static final Long ID = 100L;

	private final ValidationErrorMessageBuilder errorMessageBuilder = new ValidationErrorMessageBuilderImpl();

	@Test
	void applyIsNotNullErrorTest() {
		Assertions.assertEquals("'" + TARGET_NAME + "' should be null",
				errorMessageBuilder.buildIsNotNullErrorMessage(TARGET_NAME));
	}

	@Test
	void applyIsNullErrorTest() {
		Assertions.assertEquals("'" + TARGET_NAME + "' can't be null",
				errorMessageBuilder.buildIsNullErrorMessage(TARGET_NAME));
	}

	@Test
	void applyIsEmptyErrorTest() {
		Assertions.assertEquals("'" + TARGET_NAME + "' can't be null or empty",
				errorMessageBuilder.buildIsEmptyErrorMessage(TARGET_NAME));
	}

	@Test
	void applyIsBlankErrorTest() {
		Assertions.assertEquals("'" + TARGET_NAME + "' can't be null, empty or blank",
				errorMessageBuilder.buildIsBlankErrorMessage(TARGET_NAME));
	}

	@Test
	void applyMaxLengthExceededErrorTest() {
		Assertions.assertEquals("'" + TARGET_NAME + "' can't exceed " + MAX_LENGTH + " characters",
				errorMessageBuilder.buildMaxLengthExceededErrorMessage(TARGET_NAME, MAX_LENGTH));
	}

	@Test
	void applyFoundDuplicateErrorTest() {
		Assertions.assertEquals("Found duplicate '" + DUPLICATE + "' for '" + TARGET_NAME + "'",
				errorMessageBuilder.buildFoundDuplicateErrorMessage(TARGET_NAME, DUPLICATE));
	}

	@Test
	void applyNotFoundByIdErrorTest() {
		Assertions.assertEquals("'" + TARGET_NAME + "' with id = '" + ID + "' not found",
				errorMessageBuilder.buildNotFoundByIdErrorMessage(TARGET_NAME, ID));
	}

}
