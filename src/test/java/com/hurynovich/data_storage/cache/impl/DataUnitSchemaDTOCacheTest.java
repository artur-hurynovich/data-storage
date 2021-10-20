package com.hurynovich.data_storage.cache.impl;

import com.hurynovich.data_storage.cache.Cache;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class DataUnitSchemaDTOCacheTest {

	private final Cache<Long, DataUnitSchemaDTO> cache =
			new DataUnitSchemaDTOCache();

	private final TestObjectGenerator<DataUnitSchemaDTO> schemaGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@Test
	void test() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		final Long id = schema.getId();
		cache.store(id, schema);
		Assertions.assertTrue(cache.contains(id));

		Optional<DataUnitSchemaDTO> schemaOptional = cache.get(id);
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
