package com.hurynovich.data_storage.test_object_generator.impl;

import com.hurynovich.data_storage.model.DataUnitPropertyType;
import com.hurynovich.data_storage.model.entity.DataUnitPropertySchemaEntity;

import java.util.Arrays;
import java.util.List;

public class TestDataUnitPropertySchemaEntityGenerator extends
		AbstractTestDataUnitPropertySchemaGenerator<DataUnitPropertySchemaEntity> {

	@Override
	public DataUnitPropertySchemaEntity generateSingleObject() {
		return generateSingleObject(id1, name1, type1);
	}

	private DataUnitPropertySchemaEntity generateSingleObject(final Long id, final String name,
															  final DataUnitPropertyType type) {
		final DataUnitPropertySchemaEntity schemaDTO = new DataUnitPropertySchemaEntity();

		schemaDTO.setId(id);
		schemaDTO.setName(name);
		schemaDTO.setType(type);

		return schemaDTO;
	}

	@Override
	public List<DataUnitPropertySchemaEntity> generateMultipleObjects() {
		final DataUnitPropertySchemaEntity schema1 = generateSingleObject(id2, name2, type2);
		final DataUnitPropertySchemaEntity schema2 = generateSingleObject(id3, name3, type3);
		final DataUnitPropertySchemaEntity schema3 = generateSingleObject(id4, name4, type4);

		return Arrays.asList(schema1, schema2, schema3);
	}

}
