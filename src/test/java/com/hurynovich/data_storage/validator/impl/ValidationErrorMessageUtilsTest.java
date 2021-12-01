package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.utils.ValidationErrorMessageUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidationErrorMessageUtilsTest {

	private static final String TARGET_NAME = "targetName";
	private static final int MAX_LENGTH = 100;
	private static final String DUPLICATE = "duplicate";
	private static final Long ID = 100L;

	@Test
	void applyIsNotNullErrorTest() {
		Assertions.assertEquals("'" + TARGET_NAME + "' should be null",
				ValidationErrorMessageUtils.buildIsNotNullErrorMessage(TARGET_NAME));
	}

	@Test
	void applyIsNullErrorTest() {
		Assertions.assertEquals("'" + TARGET_NAME + "' can't be null",
				ValidationErrorMessageUtils.buildIsNullErrorMessage(TARGET_NAME));
	}

	@Test
	void applyIsEmptyErrorTest() {
		Assertions.assertEquals("'" + TARGET_NAME + "' can't be null or empty",
				ValidationErrorMessageUtils.buildIsEmptyErrorMessage(TARGET_NAME));
	}

	@Test
	void applyIsBlankErrorTest() {
		Assertions.assertEquals("'" + TARGET_NAME + "' can't be null, empty or blank",
				ValidationErrorMessageUtils.buildIsBlankErrorMessage(TARGET_NAME));
	}

	@Test
	void applyMaxLengthExceededErrorTest() {
		Assertions.assertEquals("'" + TARGET_NAME + "' can't exceed " + MAX_LENGTH + " characters",
				ValidationErrorMessageUtils.buildMaxLengthExceededErrorMessage(TARGET_NAME, MAX_LENGTH));
	}

	@Test
	void applyFoundDuplicateErrorTest() {
		Assertions.assertEquals("Found duplicate '" + DUPLICATE + "' for '" + TARGET_NAME + "'",
				ValidationErrorMessageUtils.buildFoundDuplicateErrorMessage(TARGET_NAME, DUPLICATE));
	}

	@Test
	void applyNotFoundByIdErrorTest() {
		Assertions.assertEquals("'" + TARGET_NAME + "' with id = '" + ID + "' not found",
				ValidationErrorMessageUtils.buildNotFoundByIdErrorMessage(TARGET_NAME, ID));
	}
}
