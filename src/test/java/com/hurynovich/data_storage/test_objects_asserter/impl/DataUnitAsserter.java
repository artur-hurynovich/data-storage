package com.hurynovich.data_storage.test_objects_asserter.impl;

import com.hurynovich.data_storage.model.AbstractDocument_;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument_;
import com.hurynovich.data_storage.test_objects_asserter.Asserter;
import com.hurynovich.data_storage.test_objects_asserter.model.DataUnitPropertyWrapper;
import com.hurynovich.data_storage.test_objects_asserter.model.DataUnitWrapper;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DataUnitAsserter implements Asserter<DataUnitWrapper> {

	@Override
	public void assertEquals(final DataUnitWrapper expected, final DataUnitWrapper actual,
							 final String... ignoreProperties) {
		final Set<String> ignorePropertiesSet = (ignoreProperties != null ?
				Set.of(ignoreProperties) : Collections.emptySet());
		if (!ignorePropertiesSet.contains(AbstractDocument_.ID)) {
			Assertions.assertEquals(expected.getId(), actual.getId());
		}

		if (!ignorePropertiesSet.contains(DataUnitDocument_.SCHEMA_ID)) {
			Assertions.assertEquals(expected.getSchemaId(), actual.getSchemaId());
		}

		if (!ignorePropertiesSet.contains(DataUnitDocument_.PROPERTIES)) {
			final List<DataUnitPropertyWrapper> expectedProperties = expected.getProperties();
			final List<DataUnitPropertyWrapper> actualProperties = actual.getProperties();
			Assertions.assertEquals(expectedProperties.size(), actualProperties.size());
			for (int i = 0; i < expectedProperties.size(); i++) {
				final DataUnitPropertyWrapper expectedProperty = expectedProperties.get(i);
				final DataUnitPropertyWrapper actualProperty = actualProperties.get(i);
				Assertions.assertEquals(expectedProperty.getSchemaId(), actualProperty.getSchemaId());
				Assertions.assertEquals(expectedProperty.getValue(), actualProperty.getValue());
			}
		}
	}

}
