package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitPropertySchemaEntityGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DataUnitPropertySchemaEntityDTOConverterTest {

	private final Converter<DataUnitPropertySchemaEntity, DataUnitPropertySchemaDTO> converter =
			new DataUnitPropertySchemaEntityDTOConverter();

	private final TestObjectGenerator<DataUnitPropertySchemaEntity> testObjectGenerator =
			new TestDataUnitPropertySchemaEntityGenerator();

	@Test
	public void convertNullTest() {
		final DataUnitPropertySchemaDTO schemaDTO = converter.convert(null);
		Assertions.assertNull(schemaDTO);
	}

	@Test
	public void convertNotNullTest() {
		final DataUnitPropertySchemaEntity schemaEntity = testObjectGenerator.generateSingleObject();

		final DataUnitPropertySchemaDTO schemaDTO = converter.convert(schemaEntity);
		Assertions.assertNotNull(schemaDTO);
		Assertions.assertEquals(schemaEntity.getId(), schemaDTO.getId());
		Assertions.assertEquals(schemaEntity.getName(), schemaDTO.getName());
		Assertions.assertEquals(schemaEntity.getType(), schemaDTO.getType());
	}

	@Test
	public void convertAllNullTest() {
		final List<DataUnitPropertySchemaDTO> schemaDTOs = converter.convertAll(null);

		Assertions.assertNotNull(schemaDTOs);
		Assertions.assertTrue(schemaDTOs.isEmpty());
	}

	@Test
	public void convertAllNotNullTest() {
		final List<DataUnitPropertySchemaEntity> schemaEntities = testObjectGenerator.generateMultipleObjects();
		final List<DataUnitPropertySchemaDTO> schemaDTOs = converter.convertAll(schemaEntities);
		Assertions.assertNotNull(schemaDTOs);
		Assertions.assertEquals(schemaEntities.size(), schemaDTOs.size());

		for (int i = 0; i < schemaEntities.size(); i++) {
			final DataUnitPropertySchemaEntity schemaEntity = schemaEntities.get(i);
			final DataUnitPropertySchemaDTO schemaDTO = schemaDTOs.get(i);

			Assertions.assertNotNull(schemaEntity);
			Assertions.assertEquals(schemaEntity.getId(), schemaDTO.getId());
			Assertions.assertEquals(schemaEntity.getName(), schemaDTO.getName());
			Assertions.assertEquals(schemaEntity.getType(), schemaDTO.getType());
		}
	}

}
