package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.DataUnitPropertyType;
import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitPropertySchemaEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class DataUnitPropertySchemaEntityDTOConverterTest {

	private final Converter<DataUnitPropertySchemaEntity, DataUnitPropertySchemaDTO> converter =
			new DataUnitPropertySchemaEntityDTOConverter();

	@Test
	public void convertNullTest() {
		final DataUnitPropertySchemaDTO schemaDTO = converter.convert(null);
		Assertions.assertNull(schemaDTO);
	}

	@Test
	public void convertNotNullTest() {
		final DataUnitPropertySchemaEntity schemaEntity = buildSchemaEntity1();

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
		final DataUnitPropertySchemaEntity schemaEntity1 = buildSchemaEntity1();
		final DataUnitPropertySchemaEntity schemaEntity2 = buildSchemaEntity2();

		final List<DataUnitPropertySchemaEntity> schemaEntities = Arrays.asList(schemaEntity1, schemaEntity2);
		final List<DataUnitPropertySchemaDTO> schemaDTOs = converter.
				convertAll(schemaEntities);
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

	private DataUnitPropertySchemaEntity buildSchemaEntity1() {
		return buildSchemaEntity(1000L, "Data Unit Property Schema 1 Test Name", DataUnitPropertyType.BOOLEAN);
	}

	private DataUnitPropertySchemaEntity buildSchemaEntity2() {
		return buildSchemaEntity(2000L, "Data Unit Property Schema 2 Test Name", DataUnitPropertyType.LABEL);
	}

	private DataUnitPropertySchemaEntity buildSchemaEntity(final Long id, final String name, final DataUnitPropertyType type) {
		final DataUnitPropertySchemaEntity schemaEntity = new DataUnitPropertySchemaEntity();

		schemaEntity.setId(id);
		schemaEntity.setName(name);
		schemaEntity.setType(type);

		return schemaEntity;
	}

}
