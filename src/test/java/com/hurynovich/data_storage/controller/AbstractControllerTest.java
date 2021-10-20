package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.validator.model.ValidationResult;
import org.junit.jupiter.api.Assertions;

public abstract class AbstractControllerTest {

	protected void checkValidationResultsEquality(final ValidationResult expected, final ValidationResult actual) {
		Assertions.assertEquals(expected.getType(), actual.getType());
		Assertions.assertEquals(expected.getErrors(), actual.getErrors());
	}

}
