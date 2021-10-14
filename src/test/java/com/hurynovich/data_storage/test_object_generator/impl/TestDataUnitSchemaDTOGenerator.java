package com.hurynovich.data_storage.test_object_generator.impl;

import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;

import java.util.Arrays;
import java.util.List;

public class TestDataUnitSchemaDTOGenerator extends AbstractTestDataUnitSchemaGenerator<DataUnitSchemaDTO> {

	private final TestObjectGenerator<DataUnitPropertySchemaDTO> propertySchemaGenerator =
			new TestDataUnitPropertySchemaDTOGenerator();

	@Override
	public DataUnitSchemaDTO generateSingleObject() {
		return generateSingleObject(id1, name1);
	}

	private DataUnitSchemaDTO generateSingleObject(final Long id, final String name) {
		final DataUnitSchemaDTO schemaDTO = new DataUnitSchemaDTO();

		schemaDTO.setId(id);
		schemaDTO.setName(name);

		schemaDTO.setPropertySchemas(propertySchemaGenerator.generateMultipleObjects());

		return schemaDTO;
	}

	@Override
	public List<DataUnitSchemaDTO> generateMultipleObjects() {
		final DataUnitSchemaDTO schema1 = generateSingleObject(id2, name2);
		final DataUnitSchemaDTO schema2 = generateSingleObject(id3, name3);
		final DataUnitSchemaDTO schema3 = generateSingleObject(id4, name4);

		return Arrays.asList(schema1, schema2, schema3);
	}

}
