package com.hurynovich.data_storage.test_object_generator.impl;

import com.hurynovich.data_storage.model.DataUnitPropertyType;
import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;

import java.util.Arrays;
import java.util.List;

public class TestDataUnitSchemaDTOGenerator extends AbstractTestDataUnitSchemaGenerator
		implements TestObjectGenerator<DataUnitSchemaDTO> {

	@Override
	public DataUnitSchemaDTO generateSingleObject() {
		return generateSchema(DATA_UNIT_SCHEMA_ID_1, DATA_UNIT_SCHEMA_NAME_1);
	}

	private DataUnitSchemaDTO generateSchema(final Long id, final String name) {
		final DataUnitSchemaDTO schema = new DataUnitSchemaDTO();

		schema.setId(id);
		schema.setName(name);

		schema.setPropertySchemas(generatePropertySchemas());

		return schema;
	}

	private List<DataUnitPropertySchemaDTO> generatePropertySchemas() {
		final DataUnitPropertySchemaDTO propertySchema1 =
				generatePropertySchema(DATA_UNIT_PROPERTY_SCHEMA_ID_1, DATA_UNIT_PROPERTY_SCHEMA_NAME_1, DATA_UNIT_PROPERTY_SCHEMA_TYPE_1);
		final DataUnitPropertySchemaDTO propertySchema2 =
				generatePropertySchema(DATA_UNIT_PROPERTY_SCHEMA_ID_2, DATA_UNIT_PROPERTY_SCHEMA_NAME_2, DATA_UNIT_PROPERTY_SCHEMA_TYPE_2);
		final DataUnitPropertySchemaDTO propertySchema3 =
				generatePropertySchema(DATA_UNIT_PROPERTY_SCHEMA_ID_3, DATA_UNIT_PROPERTY_SCHEMA_NAME_3, DATA_UNIT_PROPERTY_SCHEMA_TYPE_3);

		return Arrays.asList(propertySchema1, propertySchema2, propertySchema3);
	}

	private DataUnitPropertySchemaDTO generatePropertySchema(final Long id, final String name,
															 final DataUnitPropertyType type) {
		final DataUnitPropertySchemaDTO propertySchema = new DataUnitPropertySchemaDTO();

		propertySchema.setId(id);
		propertySchema.setName(name);
		propertySchema.setType(type);

		return propertySchema;
	}

	@Override
	public List<DataUnitSchemaDTO> generateMultipleObjects() {
		final DataUnitSchemaDTO schema1 = generateSchema(DATA_UNIT_SCHEMA_ID_2, DATA_UNIT_SCHEMA_NAME_2);
		final DataUnitSchemaDTO schema2 = generateSchema(DATA_UNIT_SCHEMA_ID_3, DATA_UNIT_SCHEMA_NAME_3);
		final DataUnitSchemaDTO schema3 = generateSchema(DATA_UNIT_SCHEMA_ID_4, DATA_UNIT_SCHEMA_NAME_4);

		return Arrays.asList(schema1, schema2, schema3);
	}

}
