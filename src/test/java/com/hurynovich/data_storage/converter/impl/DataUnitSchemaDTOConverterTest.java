package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataUnitSchemaDTOConverterTest {

	private final Converter<DataUnitSchemaDTO, DataUnitSchemaEntity> converter =
			new DataUnitSchemaDTOConverter();

	private final TestObjectGenerator<DataUnitSchemaDTO> schemaGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@Test
	void convertNullTest() {
		Assertions.assertNull(converter.convert(null));
	}

	@Test
	void convertNotNullTest() {
		final DataUnitSchemaDTO dto = schemaGenerator.generateSingleObject();
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

}
