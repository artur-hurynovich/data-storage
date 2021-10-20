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
	void convertDTONullTest() {
		final DataUnitSchemaEntity schemaEntity = converter.convert((DataUnitSchemaDTO) null);
		Assertions.assertNull(schemaEntity);
	}

	@Test
	void convertDTONotNullTest() {
		final DataUnitSchemaDTO dto = dtoGenerator.generateSingleObject();
		final DataUnitSchemaEntity entity = converter.convert(dto);
		checkDTOConversion(dto, entity);
	}

	private void checkDTOConversion(final DataUnitSchemaDTO dto, final DataUnitSchemaEntity entity) {
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(dto.getId(), entity.getId());
		Assertions.assertEquals(dto.getName(), entity.getName());

		final List<DataUnitPropertySchemaDTO> propertySchemaDTOs = dto.getPropertySchemas();
		final List<DataUnitPropertySchemaEntity> propertySchemaEntities = entity.getPropertySchemas();
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
	void convertEntityNullTest() {
		final DataUnitSchemaDTO schemaDTO = converter.convert((DataUnitSchemaEntity) null);
		Assertions.assertNull(schemaDTO);
	}

	@Test
	void convertEntityNotNullTest() {
		final DataUnitSchemaEntity entity = entityGenerator.generateSingleObject();
		final DataUnitSchemaDTO dto = converter.convert(entity);
		checkEntityConversion(entity, dto, false);
	}

	private void checkEntityConversion(final DataUnitSchemaEntity entity,
									   final DataUnitSchemaDTO dto,
									   boolean ignorePropertySchemas) {
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(entity.getId(), dto.getId());
		Assertions.assertEquals(entity.getName(), dto.getName());

		final List<DataUnitPropertySchemaEntity> propertySchemaEntities = entity.getPropertySchemas();
		final List<DataUnitPropertySchemaDTO> propertySchemaDTOs = dto.getPropertySchemas();
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
	void convertEntitiesNullTest() {
		final List<DataUnitSchemaDTO> dtos = converter.convert((Iterable<DataUnitSchemaEntity>) null);
		Assertions.assertNotNull(dtos);
		Assertions.assertTrue(dtos.isEmpty());
	}

	@Test
	void convertEntitiesNotNullTest() {
		final List<DataUnitSchemaEntity> schemaEntities = entityGenerator.generateMultipleObjects();
		final List<DataUnitSchemaDTO> schemaDTOs = converter.convert(schemaEntities);
		Assertions.assertNotNull(schemaDTOs);
		Assertions.assertEquals(schemaEntities.size(), schemaDTOs.size());

		for (int i = 0; i < schemaEntities.size(); i++) {
			final DataUnitSchemaEntity schemaEntity = schemaEntities.get(i);
			final DataUnitSchemaDTO schemaDTO = schemaDTOs.get(i);
			checkEntityConversion(schemaEntity, schemaDTO, true);
		}
	}

}
