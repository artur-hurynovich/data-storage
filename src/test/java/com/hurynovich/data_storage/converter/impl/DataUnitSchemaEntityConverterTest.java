package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaEntityGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataUnitSchemaEntityConverterTest {

	protected final TestObjectGenerator<DataUnitSchemaEntity> schemaGenerator =
			new TestDataUnitSchemaEntityGenerator();

	protected final Converter<DataUnitSchemaEntity, DataUnitSchemaDTO> converter =
			new DataUnitSchemaEntityConverter();

	@Test
	void convertNullTest() {
		Assertions.assertNull(converter.convert(null));
	}

	@Test
	void convertNotNullTest() {
		final DataUnitSchemaEntity entity = schemaGenerator.generateSingleObject();
		final DataUnitSchemaDTO dto = converter.convert(entity);
		checkConversion(entity, dto, true);
	}

	@Test
	void convertNotNullIgnoreTest() {
		final DataUnitSchemaEntity entity = schemaGenerator.generateSingleObject();
		final DataUnitSchemaDTO dto = converter.convert(entity, "propertySchemas");
		checkConversion(entity, dto, false);
	}

	protected void checkConversion(final DataUnitSchemaEntity entity,
								   final DataUnitSchemaDTO dto,
								   final boolean convertPropertySchemas) {
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(entity.getId(), dto.getId());
		Assertions.assertEquals(entity.getName(), dto.getName());

		final List<DataUnitPropertySchemaEntity> propertySchemaEntities = entity.getPropertySchemas();
		final List<DataUnitPropertySchemaDTO> propertySchemaDTOs = dto.getPropertySchemas();
		if (convertPropertySchemas) {
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
		} else {
			Assertions.assertNull(propertySchemaDTOs);
		}
	}

}
