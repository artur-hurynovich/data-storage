package com.hurynovich.data_storage.test_object_generator.impl;

import com.hurynovich.data_storage.model.entity.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;

import java.util.Arrays;
import java.util.List;

public class TestDataUnitSchemaEntityGenerator extends AbstractTestDataUnitSchemaGenerator<DataUnitSchemaEntity> {

	private final TestObjectGenerator<DataUnitPropertySchemaEntity> propertySchemaGenerator =
			new TestDataUnitPropertySchemaEntityGenerator();

	@Override
	public DataUnitSchemaEntity generateSingleObject() {
		return generateSingleObject(id1, name1);
	}

	private DataUnitSchemaEntity generateSingleObject(final Long id, final String name) {
		final DataUnitSchemaEntity schemaEntity = new DataUnitSchemaEntity();

		schemaEntity.setId(id);
		schemaEntity.setName(name);

		schemaEntity.setPropertySchemas(propertySchemaGenerator.generateMultipleObjects());

		return schemaEntity;
	}

	@Override
	public List<DataUnitSchemaEntity> generateMultipleObjects() {
		final DataUnitSchemaEntity schema1 = generateSingleObject(id2, name2);
		final DataUnitSchemaEntity schema2 = generateSingleObject(id3, name3);
		final DataUnitSchemaEntity schema3 = generateSingleObject(id4, name4);

		return Arrays.asList(schema1, schema2, schema3);
	}

}
