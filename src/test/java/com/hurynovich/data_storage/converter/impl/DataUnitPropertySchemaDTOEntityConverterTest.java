package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.DataUnitPropertyType;
import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitPropertySchemaEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class DataUnitPropertySchemaDTOEntityConverterTest {

	private final Converter<DataUnitPropertySchemaDTO, DataUnitPropertySchemaEntity> converter =
			new DataUnitPropertySchemaDTOEntityConverter();

	@Test
	public void convertNullTest() {
		final DataUnitPropertySchemaEntity schemaEntity = converter.convert(null);
		Assertions.assertNull(schemaEntity);
	}

	@Test
	public void convertNotNullTest() {
		final DataUnitPropertySchemaDTO schemaDTO = buildSchemaDTO1();

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
		final DataUnitPropertySchemaDTO schemaDTO1 = buildSchemaDTO1();
		final DataUnitPropertySchemaDTO schemaDTO2 = buildSchemaDTO2();

		final List<DataUnitPropertySchemaDTO> schemaDTOs = Arrays.asList(schemaDTO1, schemaDTO2);
		final List<DataUnitPropertySchemaEntity> schemaEntities = converter.
				convertAll(schemaDTOs);
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

	private DataUnitPropertySchemaDTO buildSchemaDTO1() {
		return buildSchemaDTO(1000L, "Data Unit Property Schema 1 Test Name", DataUnitPropertyType.BOOLEAN);
	}

	private DataUnitPropertySchemaDTO buildSchemaDTO2() {
		return buildSchemaDTO(2000L, "Data Unit Property Schema 2 Test Name", DataUnitPropertyType.LABEL);
	}

	private DataUnitPropertySchemaDTO buildSchemaDTO(final Long id, final String name, final DataUnitPropertyType type) {
		final DataUnitPropertySchemaDTO schemaDTO = new DataUnitPropertySchemaDTO();

		schemaDTO.setId(id);
		schemaDTO.setName(name);
		schemaDTO.setType(type);

		return schemaDTO;
	}

}
