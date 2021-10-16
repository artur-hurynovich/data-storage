package com.hurynovich.data_storage.test_object_generator.impl;

import com.hurynovich.data_storage.model.DataUnitPropertyType;
import com.hurynovich.data_storage.model.entity.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;

import java.util.Arrays;
import java.util.List;

public class TestDataUnitSchemaEntityGenerator extends AbstractTestDataUnitSchemaGenerator
		implements TestObjectGenerator<DataUnitSchemaEntity> {

	@Override
	public DataUnitSchemaEntity generateSingleObject() {
		return generateSchema(DATA_UNIT_SCHEMA_ID_1, DATA_UNIT_SCHEMA_NAME_1);
	}

	private DataUnitSchemaEntity generateSchema(final Long id, final String name) {
		final DataUnitSchemaEntity schema = new DataUnitSchemaEntity();

		schema.setId(id);
		schema.setName(name);

		schema.setPropertySchemas(generatePropertySchemas());

		return schema;
	}

	private List<DataUnitPropertySchemaEntity> generatePropertySchemas() {
		final DataUnitPropertySchemaEntity propertySchema1 =
				generatePropertySchema(DATA_UNIT_PROPERTY_SCHEMA_ID_1, DATA_UNIT_PROPERTY_SCHEMA_NAME_1, DATA_UNIT_PROPERTY_SCHEMA_TYPE_1);
		final DataUnitPropertySchemaEntity propertySchema2 =
				generatePropertySchema(DATA_UNIT_PROPERTY_SCHEMA_ID_2, DATA_UNIT_PROPERTY_SCHEMA_NAME_2, DATA_UNIT_PROPERTY_SCHEMA_TYPE_2);
		final DataUnitPropertySchemaEntity propertySchema3 =
				generatePropertySchema(DATA_UNIT_PROPERTY_SCHEMA_ID_3, DATA_UNIT_PROPERTY_SCHEMA_NAME_3, DATA_UNIT_PROPERTY_SCHEMA_TYPE_3);

		return Arrays.asList(propertySchema1, propertySchema2, propertySchema3);
	}

	private DataUnitPropertySchemaEntity generatePropertySchema(final Long id, final String name,
																final DataUnitPropertyType type) {
		final DataUnitPropertySchemaEntity propertySchema = new DataUnitPropertySchemaEntity();

		propertySchema.setId(id);
		propertySchema.setName(name);
		propertySchema.setType(type);

		return propertySchema;
	}

	@Override
	public List<DataUnitSchemaEntity> generateMultipleObjects() {
		final DataUnitSchemaEntity schema1 = generateSchema(DATA_UNIT_SCHEMA_ID_2, DATA_UNIT_SCHEMA_NAME_2);
		final DataUnitSchemaEntity schema2 = generateSchema(DATA_UNIT_SCHEMA_ID_3, DATA_UNIT_SCHEMA_NAME_3);
		final DataUnitSchemaEntity schema3 = generateSchema(DATA_UNIT_SCHEMA_ID_4, DATA_UNIT_SCHEMA_NAME_4);

		return Arrays.asList(schema1, schema2, schema3);
	}

}
