package com.hurynovich.data_storage.cache.impl;

import com.hurynovich.data_storage.cache.Cache;
import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class DataUnitSchemaCacheTest {

	private final Cache<Long, DataUnitSchemaServiceModel> cache =
			new DataUnitSchemaCache();

	private final ModelGenerator<DataUnitSchemaServiceModel> schemaGenerator =
			new DataUnitSchemaServiceModelGenerator();

	@Test
	void test() {
		final DataUnitSchemaServiceModel schema = schemaGenerator.generate();
		final Long id = schema.getId();
		cache.store(id, schema);
		Assertions.assertTrue(cache.contains(id));

		Optional<DataUnitSchemaServiceModel> schemaOptional = cache.get(id);
		Assertions.assertNotNull(schemaOptional);
		Assertions.assertTrue(schemaOptional.isPresent());
		Assertions.assertEquals(schema, schemaOptional.get());

		cache.invalidate(id);
		Assertions.assertFalse(cache.contains(id));
		schemaOptional = cache.get(id);
		Assertions.assertNotNull(schemaOptional);
		Assertions.assertTrue(schemaOptional.isEmpty());
	}
}
