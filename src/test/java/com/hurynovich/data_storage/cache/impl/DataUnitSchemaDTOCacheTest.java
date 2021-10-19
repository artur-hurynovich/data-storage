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

	private final TestObjectGenerator<DataUnitSchemaDTO> dtoGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@Test
	void test() {
		final DataUnitSchemaDTO dataUnitSchema = dtoGenerator.generateSingleObject();
		final Long id = dataUnitSchema.getId();
		cache.store(id, dataUnitSchema);
		Assertions.assertTrue(cache.contains(id));

		Optional<DataUnitSchemaDTO> dataUnitSchemaOptional = cache.get(id);
		Assertions.assertNotNull(dataUnitSchemaOptional);
		Assertions.assertTrue(dataUnitSchemaOptional.isPresent());
		Assertions.assertEquals(dataUnitSchema, dataUnitSchemaOptional.get());

		cache.invalidate(id);
		Assertions.assertFalse(cache.contains(id));
		dataUnitSchemaOptional = cache.get(id);
		Assertions.assertNotNull(dataUnitSchemaOptional);
		Assertions.assertTrue(dataUnitSchemaOptional.isEmpty());
	}

}
