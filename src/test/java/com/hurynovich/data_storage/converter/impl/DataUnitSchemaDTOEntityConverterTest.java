package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DataUnitSchemaDTOEntityConverterTest {

	private final Converter<DataUnitSchemaDTO, DataUnitSchemaEntity> converter =
			new DataUnitSchemaDTOEntityConverter();

	private final TestObjectGenerator<DataUnitSchemaDTO> testObjectGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@Test
	public void convertNullTest() {
		final DataUnitSchemaEntity schemaEntity = converter.convert(null);
		Assertions.assertNull(schemaEntity);
	}

	@Test
	public void convertNotNullTest() {
		final DataUnitSchemaDTO schemaDTO = testObjectGenerator.generateSingleObject();

		final DataUnitSchemaEntity schemaEntity = converter.convert(schemaDTO);
		Assertions.assertNotNull(schemaEntity);
		Assertions.assertEquals(schemaDTO.getId(), schemaEntity.getId());
		Assertions.assertEquals(schemaDTO.getName(), schemaEntity.getName());

		final List<DataUnitPropertySchemaDTO> propertySchemaDTOs = schemaDTO.getPropertySchemas();
		final List<DataUnitPropertySchemaEntity> propertySchemaEntities = schemaEntity.getPropertySchemas();
		Assertions.assertNotNull(propertySchemaDTOs);
		Assertions.assertNotNull(propertySchemaEntities);
		Assertions.assertEquals(propertySchemaDTOs.size(), propertySchemaEntities.size());

		for (int i = 0; i < propertySchemaDTOs.size(); i++) {
			final DataUnitPropertySchemaDTO propertySchemaDTO = propertySchemaDTOs.get(i);
			final DataUnitPropertySchemaEntity propertySchemaEntity = propertySchemaEntities.get(i);

			Assertions.assertNotNull(propertySchemaEntity);
			Assertions.assertEquals(propertySchemaDTO.getId(), propertySchemaEntity.getId());
			Assertions.assertEquals(propertySchemaDTO.getName(), propertySchemaEntity.getName());
			Assertions.assertEquals(propertySchemaDTO.getType(), propertySchemaEntity.getType());
		}
	}

	@Test
	public void convertAllNullTest() {
		final List<DataUnitSchemaEntity> schemaEntities = converter.convertAll(null);

		Assertions.assertNotNull(schemaEntities);
		Assertions.assertTrue(schemaEntities.isEmpty());
	}

	@Test
	public void convertAllNotNullTest() {
		final List<DataUnitSchemaDTO> schemaDTOs = testObjectGenerator.generateMultipleObjects();
		final List<DataUnitSchemaEntity> schemaEntities = converter.convertAll(schemaDTOs);
		Assertions.assertNotNull(schemaEntities);
		Assertions.assertEquals(schemaDTOs.size(), schemaEntities.size());

		for (int i = 0; i < schemaDTOs.size(); i++) {
			final DataUnitSchemaDTO schemaDTO = schemaDTOs.get(i);
			final DataUnitSchemaEntity schemaEntity = schemaEntities.get(i);

			Assertions.assertNotNull(schemaEntity);
			Assertions.assertEquals(schemaDTO.getId(), schemaEntity.getId());
			Assertions.assertEquals(schemaDTO.getName(), schemaEntity.getName());

			final List<DataUnitPropertySchemaDTO> propertySchemaDTOs = schemaDTO.getPropertySchemas();
			final List<DataUnitPropertySchemaEntity> propertySchemaEntities = schemaEntity.getPropertySchemas();
			Assertions.assertNotNull(propertySchemaDTOs);
			Assertions.assertNotNull(propertySchemaEntities);
			Assertions.assertEquals(propertySchemaDTOs.size(), propertySchemaEntities.size());

			for (int j = 0; j < propertySchemaDTOs.size(); j++) {
				final DataUnitPropertySchemaDTO propertySchemaDTO = propertySchemaDTOs.get(i);
				final DataUnitPropertySchemaEntity propertySchemaEntity = propertySchemaEntities.get(i);

				Assertions.assertNotNull(propertySchemaEntity);
				Assertions.assertEquals(propertySchemaDTO.getId(), propertySchemaEntity.getId());
				Assertions.assertEquals(propertySchemaDTO.getName(), propertySchemaEntity.getName());
				Assertions.assertEquals(propertySchemaDTO.getType(), propertySchemaEntity.getType());
			}
		}
	}

}
