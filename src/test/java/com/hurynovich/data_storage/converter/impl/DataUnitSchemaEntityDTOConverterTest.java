package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaEntityGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DataUnitSchemaEntityDTOConverterTest {

	private final Converter<DataUnitSchemaEntity, DataUnitSchemaDTO> converter =
			new DataUnitSchemaEntityDTOConverter(new DataUnitPropertySchemaEntityDTOConverter());

	private final TestObjectGenerator<DataUnitSchemaEntity> testObjectGenerator =
			new TestDataUnitSchemaEntityGenerator();

	@Test
	public void convertNullTest() {
		final DataUnitSchemaDTO schemaDTO = converter.convert(null);
		Assertions.assertNull(schemaDTO);
	}

	@Test
	public void convertNotNullTest() {
		final DataUnitSchemaEntity schemaEntity = testObjectGenerator.generateSingleObject();

		final DataUnitSchemaDTO schemaDTO = converter.convert(schemaEntity);
		Assertions.assertNotNull(schemaDTO);
		Assertions.assertEquals(schemaEntity.getId(), schemaDTO.getId());
		Assertions.assertEquals(schemaEntity.getName(), schemaDTO.getName());

		final List<DataUnitPropertySchemaEntity> propertySchemaEntities = schemaEntity.getPropertySchemas();
		final List<DataUnitPropertySchemaDTO> propertySchemaDTOs = schemaDTO.getPropertySchemas();
		Assertions.assertNotNull(propertySchemaDTOs);
		Assertions.assertNotNull(propertySchemaDTOs);
		Assertions.assertEquals(propertySchemaEntities.size(), propertySchemaDTOs.size());

		for (int i = 0; i < propertySchemaEntities.size(); i++) {
			final DataUnitPropertySchemaEntity propertySchemaEntity = propertySchemaEntities.get(i);
			final DataUnitPropertySchemaDTO propertySchemaDTO = propertySchemaDTOs.get(i);

			Assertions.assertNotNull(propertySchemaDTO);
			Assertions.assertEquals(propertySchemaEntity.getId(), propertySchemaDTO.getId());
			Assertions.assertEquals(propertySchemaEntity.getName(), propertySchemaDTO.getName());
			Assertions.assertEquals(propertySchemaEntity.getType(), propertySchemaDTO.getType());
		}
	}

	@Test
	public void convertAllNullTest() {
		final List<DataUnitSchemaDTO> schemaDTOs = converter.convertAll(null);

		Assertions.assertNotNull(schemaDTOs);
		Assertions.assertTrue(schemaDTOs.isEmpty());
	}

	@Test
	public void convertAllNotNullTest() {
		final List<DataUnitSchemaEntity> schemaEntities = testObjectGenerator.generateMultipleObjects();
		final List<DataUnitSchemaDTO> schemaDTOs = converter.convertAll(schemaEntities);
		Assertions.assertNotNull(schemaDTOs);
		Assertions.assertEquals(schemaEntities.size(), schemaDTOs.size());

		for (int i = 0; i < schemaEntities.size(); i++) {
			final DataUnitSchemaEntity schemaEntity = schemaEntities.get(i);
			final DataUnitSchemaDTO schemaDTO = schemaDTOs.get(i);

			Assertions.assertNotNull(schemaDTO);
			Assertions.assertEquals(schemaEntity.getId(), schemaDTO.getId());
			Assertions.assertEquals(schemaEntity.getName(), schemaDTO.getName());

			final List<DataUnitPropertySchemaEntity> propertySchemaEntities = schemaEntity.getPropertySchemas();
			final List<DataUnitPropertySchemaDTO> propertySchemaDTOs = schemaDTO.getPropertySchemas();
			Assertions.assertNotNull(propertySchemaEntities);
			Assertions.assertNotNull(propertySchemaDTOs);
			Assertions.assertEquals(propertySchemaEntities.size(), propertySchemaDTOs.size());

			for (int j = 0; j < propertySchemaEntities.size(); j++) {
				final DataUnitPropertySchemaEntity propertySchemaEntity = propertySchemaEntities.get(i);
				final DataUnitPropertySchemaDTO propertySchemaDTO = propertySchemaDTOs.get(i);

				Assertions.assertNotNull(propertySchemaDTO);
				Assertions.assertEquals(propertySchemaEntity.getId(), propertySchemaDTO.getId());
				Assertions.assertEquals(propertySchemaEntity.getName(), propertySchemaDTO.getName());
				Assertions.assertEquals(propertySchemaEntity.getType(), propertySchemaDTO.getType());
			}
		}
	}

}
