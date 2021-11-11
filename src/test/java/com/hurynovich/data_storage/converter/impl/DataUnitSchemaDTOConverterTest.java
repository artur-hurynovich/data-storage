package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaEntityGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataUnitSchemaDTOConverterTest {

	private final DTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity, Long> converter =
			new DataUnitSchemaDTOConverter();

	private final TestObjectGenerator<DataUnitSchemaDTO> dtoGenerator =
			new TestDataUnitSchemaDTOGenerator();

	private final TestObjectGenerator<DataUnitSchemaEntity> entityGenerator =
			new TestDataUnitSchemaEntityGenerator();

	@Test
	void convertDTONullTest() {
		Assertions.assertNull(converter.convert((DataUnitSchemaDTO) null));
	}

	@Test
	void convertDTONotNullTest() {
		final DataUnitSchemaDTO dto = dtoGenerator.generateSingleObject();
		final DataUnitSchemaEntity entity = converter.convert(dto);
		checkConversion(dto, entity);
	}

	private void checkConversion(final DataUnitSchemaDTO dto, final DataUnitSchemaEntity entity) {
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
		Assertions.assertNull(converter.convert((DataUnitSchemaEntity) null));
	}

	@Test
	void convertEntityNotNullTest() {
		final DataUnitSchemaEntity entity = entityGenerator.generateSingleObject();
		final DataUnitSchemaDTO dto = converter.convert(entity);
		checkConversion(entity, dto, false);
	}

	@Test
	void convertEntityNotNullIgnorePropertySchemasTest() {
		final DataUnitSchemaEntity entity = entityGenerator.generateSingleObject();
		final DataUnitSchemaDTO dto = converter.convert(entity, DataUnitSchemaEntity_.PROPERTY_SCHEMAS);
		checkConversion(entity, dto, true);
	}

	protected void checkConversion(final DataUnitSchemaEntity entity,
								   final DataUnitSchemaDTO dto,
								   final boolean ignorePropertySchemas) {
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(entity.getId(), dto.getId());
		Assertions.assertEquals(entity.getName(), dto.getName());

		final List<DataUnitPropertySchemaEntity> propertySchemaEntities = entity.getPropertySchemas();
		final List<DataUnitPropertySchemaDTO> propertySchemaDTOs = dto.getPropertySchemas();
		if (ignorePropertySchemas) {
			Assertions.assertTrue(propertySchemaDTOs.isEmpty());
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

}
