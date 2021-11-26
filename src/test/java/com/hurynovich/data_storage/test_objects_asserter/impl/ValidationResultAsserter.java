package com.hurynovich.data_storage.test_objects_asserter.impl;

import com.hurynovich.data_storage.test_objects_asserter.TestObjectsAsserter;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import org.junit.jupiter.api.Assertions;

public class ValidationResultAsserter implements TestObjectsAsserter<ValidationResult> {

	@Override
	public void assertEquals(final ValidationResult expected, final ValidationResult actual) {
		Assertions.assertEquals(expected.getType(), actual.getType());
		Assertions.assertEquals(expected.getErrors(), actual.getErrors());
	}

}
