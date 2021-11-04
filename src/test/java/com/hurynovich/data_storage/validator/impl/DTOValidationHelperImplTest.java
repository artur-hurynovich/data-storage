package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.validator.DTOValidationHelper;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.function.Consumer;

class DTOValidationHelperImplTest {

	private static final String TARGET_NAME = "targetName";
	private static final int MAX_LENGTH = 100;
	private static final String DUPLICATE = "duplicate";
	private static final Long ID = 100L;

	private final DTOValidationHelper helper = new DTOValidationHelperImpl();

	@Test
	void applyIsNotNullErrorTest() {
		processTest(result -> helper.applyIsNotNullError(TARGET_NAME, result),
				"'" + TARGET_NAME + "' should be null");
	}

	@Test
	void applyIsNullErrorTest() {
		processTest(result -> helper.applyIsNullError(TARGET_NAME, result),
				"'" + TARGET_NAME + "' can't be null");
	}

	@Test
	void applyIsEmptyErrorTest() {
		processTest(result -> helper.applyIsEmptyError(TARGET_NAME, result),
				"'" + TARGET_NAME + "' can't be null or empty");
	}

	@Test
	void applyIsBlankErrorTest() {
		processTest(result -> helper.applyIsBlankError(TARGET_NAME, result),
				"'" + TARGET_NAME + "' can't be null, empty or blank");
	}

	@Test
	void applyMaxLengthExceededErrorTest() {
		processTest(result -> helper.applyMaxLengthExceededError(TARGET_NAME, MAX_LENGTH, result),
				"'" + TARGET_NAME + "' can't exceed " + MAX_LENGTH + " characters");
	}

	@Test
	void applyFoundDuplicateErrorTest() {
		processTest(result -> helper.applyFoundDuplicateError(TARGET_NAME, DUPLICATE, result),
				"Found duplicate '" + DUPLICATE + "' for '" + TARGET_NAME + "'");
	}

	@Test
	void applyNotFoundByIdErrorTest() {
		processTest(result -> helper.applyNotFoundByIdError(TARGET_NAME, ID, result),
				"'" + TARGET_NAME + "' with id = '" + ID + "' not found");
	}

	private void processTest(final Consumer<ValidationResult> helperMethodCall, final String expectedError) {
		final ValidationResult result = new ValidationResult();
		helperMethodCall.accept(result);

		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals(expectedError, errors.iterator().next());
	}

}
