package com.hurynovich.data_storage.test_objects_asserter.impl;

import com.hurynovich.data_storage.test_objects_asserter.Asserter;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import org.junit.jupiter.api.Assertions;

public class ValidationResultAsserter implements Asserter<ValidationResult> {

	@Override
	public void assertEquals(final ValidationResult expected, final ValidationResult actual,
							 final String... ignoreProperties) {
		Assertions.assertEquals(expected.getType(), actual.getType());
		Assertions.assertEquals(expected.getErrors(), actual.getErrors());
	}

}
