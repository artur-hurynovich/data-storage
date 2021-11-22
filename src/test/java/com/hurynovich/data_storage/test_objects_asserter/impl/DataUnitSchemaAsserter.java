package com.hurynovich.data_storage.test_objects_asserter.impl;

import com.hurynovich.data_storage.model.AbstractEntity_;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.test_objects_asserter.Asserter;
import com.hurynovich.data_storage.test_objects_asserter.model.DataUnitPropertySchemaWrapper;
import com.hurynovich.data_storage.test_objects_asserter.model.DataUnitSchemaWrapper;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DataUnitSchemaAsserter implements Asserter<DataUnitSchemaWrapper> {

	@Override
	public void assertEquals(final DataUnitSchemaWrapper expected, final DataUnitSchemaWrapper actual,
							 final String... ignoreProperties) {
		final Set<String> ignorePropertiesSet = (ignoreProperties != null ?
				Set.of(ignoreProperties) : Collections.emptySet());
		if (!ignorePropertiesSet.contains(AbstractEntity_.ID)) {
			Assertions.assertEquals(expected.getId(), actual.getId());
		}

		if (!ignorePropertiesSet.contains(DataUnitSchemaEntity_.NAME)) {
			Assertions.assertEquals(expected.getName(), actual.getName());
		}

		if (!ignorePropertiesSet.contains(DataUnitSchemaEntity_.PROPERTY_SCHEMAS)) {
			final List<DataUnitPropertySchemaWrapper> expectedPropertySchemas = expected.getPropertySchemas();
			final List<DataUnitPropertySchemaWrapper> actualPropertySchemas = actual.getPropertySchemas();
			Assertions.assertEquals(expectedPropertySchemas.size(), actualPropertySchemas.size());
			for (int i = 0; i < expectedPropertySchemas.size(); i++) {
				final DataUnitPropertySchemaWrapper expectedPropertySchema = expectedPropertySchemas.get(i);
				final DataUnitPropertySchemaWrapper actualPropertySchema = actualPropertySchemas.get(i);

				if (!ignorePropertiesSet.contains(AbstractEntity_.ID)) {
					Assertions.assertEquals(expectedPropertySchema.getId(), actualPropertySchema.getId());
				}

				Assertions.assertEquals(expectedPropertySchema.getName(), actualPropertySchema.getName());
				Assertions.assertEquals(expectedPropertySchema.getType(), actualPropertySchema.getType());
			}
		}
	}

}
