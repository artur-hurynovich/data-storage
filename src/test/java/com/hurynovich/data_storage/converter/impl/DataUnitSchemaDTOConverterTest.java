package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaEntityGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataUnitSchemaDTOConverterTest {

	private final DTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity> converter =
			new DataUnitSchemaDTOConverter();

	private final TestObjectGenerator<DataUnitSchemaDTO> dtoGenerator =
			new TestDataUnitSchemaDTOGenerator();

	private final TestObjectGenerator<DataUnitSchemaEntity> entityGenerator =
			new TestDataUnitSchemaEntityGenerator();

	@Test
	void convertFromDTONullTest() {
		final DataUnitSchemaEntity schemaEntity = converter.convert((DataUnitSchemaDTO) null);
		Assertions.assertNull(schemaEntity);
	}

	@Test
	void convertFromDTONotNullTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final DataUnitSchemaEntity schemaEntity = converter.convert(schemaDTO);
		checkDTOToEntityConversion(schemaDTO, schemaEntity);
	}

	private void checkDTOToEntityConversion(final DataUnitSchemaDTO schemaDTO, final DataUnitSchemaEntity schemaEntity) {
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
	void convertToDTONullTest() {
		final DataUnitSchemaDTO schemaDTO = converter.convert((DataUnitSchemaEntity) null);
		Assertions.assertNull(schemaDTO);
	}

	@Test
	void convertToDTONotNullTest() {
		final DataUnitSchemaEntity schemaEntity = entityGenerator.generateSingleObject();
		final DataUnitSchemaDTO schemaDTO = converter.convert(schemaEntity);
		checkEntityToDTOConversion(schemaEntity, schemaDTO, false);
	}

	private void checkEntityToDTOConversion(final DataUnitSchemaEntity schemaEntity,
											final DataUnitSchemaDTO schemaDTO,
											boolean ignorePropertySchemas) {
		Assertions.assertNotNull(schemaDTO);
		Assertions.assertEquals(schemaEntity.getId(), schemaDTO.getId());
		Assertions.assertEquals(schemaEntity.getName(), schemaDTO.getName());

		final List<DataUnitPropertySchemaEntity> propertySchemaEntities = schemaEntity.getPropertySchemas();
		final List<DataUnitPropertySchemaDTO> propertySchemaDTOs = schemaDTO.getPropertySchemas();
		if (ignorePropertySchemas) {
			Assertions.assertNull(propertySchemaDTOs);
		} else {
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
	}

	@Test
	void convertAllToDTOsNullTest() {
		final List<DataUnitSchemaDTO> schemaDTOs = converter.convert((Iterable<DataUnitSchemaEntity>) null);
		Assertions.assertNotNull(schemaDTOs);
		Assertions.assertTrue(schemaDTOs.isEmpty());
	}

	@Test
	void convertAllToDTOsNotNullTest() {
		final List<DataUnitSchemaEntity> schemaEntities = entityGenerator.generateMultipleObjects();
		final List<DataUnitSchemaDTO> schemaDTOs = converter.convert(schemaEntities);
		Assertions.assertNotNull(schemaDTOs);
		Assertions.assertEquals(schemaEntities.size(), schemaDTOs.size());

		for (int i = 0; i < schemaEntities.size(); i++) {
			final DataUnitSchemaEntity schemaEntity = schemaEntities.get(i);
			final DataUnitSchemaDTO schemaDTO = schemaDTOs.get(i);
			checkEntityToDTOConversion(schemaEntity, schemaDTO, true);
		}
	}

}
