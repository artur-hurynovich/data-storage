package com.hurynovich.data_storage.test_object_generator.impl;

import com.hurynovich.data_storage.model.DataUnitPropertyType;
import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;

import java.util.Arrays;
import java.util.List;

public class TestDataUnitPropertySchemaDTOGenerator extends
		AbstractTestDataUnitPropertySchemaGenerator<DataUnitPropertySchemaDTO> {

	@Override
	public DataUnitPropertySchemaDTO generateSingleObject() {
		return generateSingleObject(id1, name1, type1);
	}

	private DataUnitPropertySchemaDTO generateSingleObject(final Long id, final String name,
														   final DataUnitPropertyType type) {
		final DataUnitPropertySchemaDTO schemaDTO = new DataUnitPropertySchemaDTO();

		schemaDTO.setId(id);
		schemaDTO.setName(name);
		schemaDTO.setType(type);

		return schemaDTO;
	}

	@Override
	public List<DataUnitPropertySchemaDTO> generateMultipleObjects() {
		final DataUnitPropertySchemaDTO schema1 = generateSingleObject(id2, name2, type2);
		final DataUnitPropertySchemaDTO schema2 = generateSingleObject(id3, name3, type3);
		final DataUnitPropertySchemaDTO schema3 = generateSingleObject(id4, name4, type4);

		return Arrays.asList(schema1, schema2, schema3);
	}

}
