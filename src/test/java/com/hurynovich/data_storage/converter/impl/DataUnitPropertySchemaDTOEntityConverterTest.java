package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitPropertySchemaDTOGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DataUnitPropertySchemaDTOEntityConverterTest {

	private final Converter<DataUnitPropertySchemaDTO, DataUnitPropertySchemaEntity> converter =
			new DataUnitPropertySchemaDTOEntityConverter();

	private final TestObjectGenerator<DataUnitPropertySchemaDTO> testObjectGenerator =
			new TestDataUnitPropertySchemaDTOGenerator();

	@Test
	public void convertNullTest() {
		final DataUnitPropertySchemaEntity schemaEntity = converter.convert(null);
		Assertions.assertNull(schemaEntity);
	}

	@Test
	public void convertNotNullTest() {
		final DataUnitPropertySchemaDTO schemaDTO = testObjectGenerator.generateSingleObject();

		final DataUnitPropertySchemaEntity schemaEntity = converter.convert(schemaDTO);
		Assertions.assertNotNull(schemaEntity);
		Assertions.assertEquals(schemaDTO.getId(), schemaEntity.getId());
		Assertions.assertEquals(schemaDTO.getName(), schemaEntity.getName());
		Assertions.assertEquals(schemaDTO.getType(), schemaEntity.getType());
	}

	@Test
	public void convertAllNullTest() {
		final List<DataUnitPropertySchemaEntity> schemaEntities = converter.convertAll(null);

		Assertions.assertNotNull(schemaEntities);
		Assertions.assertTrue(schemaEntities.isEmpty());
	}

	@Test
	public void convertAllNotNullTest() {
		final List<DataUnitPropertySchemaDTO> schemaDTOs = testObjectGenerator.generateMultipleObjects();
		final List<DataUnitPropertySchemaEntity> schemaEntities = converter.convertAll(schemaDTOs);
		Assertions.assertNotNull(schemaEntities);
		Assertions.assertEquals(schemaDTOs.size(), schemaEntities.size());

		for (int i = 0; i < schemaDTOs.size(); i++) {
			final DataUnitPropertySchemaDTO schemaDTO = schemaDTOs.get(i);
			final DataUnitPropertySchemaEntity schemaEntity = schemaEntities.get(i);

			Assertions.assertNotNull(schemaEntity);
			Assertions.assertEquals(schemaDTO.getId(), schemaEntity.getId());
			Assertions.assertEquals(schemaDTO.getName(), schemaEntity.getName());
			Assertions.assertEquals(schemaDTO.getType(), schemaEntity.getType());
		}
	}

}
