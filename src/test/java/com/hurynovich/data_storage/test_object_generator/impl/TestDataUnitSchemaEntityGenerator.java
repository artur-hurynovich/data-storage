package com.hurynovich.data_storage.test_object_generator.impl;

import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.test_object_generator.TestIdentifiedObjectGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_NAME;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_TYPE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_DATE_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_DATE_PROPERTY_SCHEMA_NAME;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_DATE_PROPERTY_SCHEMA_TYPE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_FLOAT_PROPERTY_SCHEMA_NAME;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_FLOAT_PROPERTY_SCHEMA_TYPE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_INTEGER_PROPERTY_SCHEMA_NAME;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_INTEGER_PROPERTY_SCHEMA_TYPE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_ID_1;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_ID_2;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_ID_3;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_NAME_1;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_NAME_2;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_NAME_3;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_NAME;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_TYPE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TIME_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TIME_PROPERTY_SCHEMA_NAME;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TIME_PROPERTY_SCHEMA_TYPE;

public class TestDataUnitSchemaEntityGenerator implements TestIdentifiedObjectGenerator<DataUnitSchemaEntity> {

	@Override
	public DataUnitSchemaEntity generateObject() {
		return generateSchema(DATA_UNIT_SCHEMA_ID_1, DATA_UNIT_SCHEMA_NAME_1, this::generateNewPropertySchemas);
	}

	@Override
	public DataUnitSchemaEntity generateObjectNullId() {
		return generateSchema(null, DATA_UNIT_SCHEMA_NAME_1, this::generateNewPropertySchemasNullId);
	}

	private DataUnitSchemaEntity generateSchema(final Long id, final String name,
												final Supplier<List<DataUnitPropertySchemaEntity>> propertySchemasSupplier) {
		final DataUnitSchemaEntity schema = new DataUnitSchemaEntity();

		schema.setId(id);
		schema.setName(name);

		schema.setPropertySchemas(propertySchemasSupplier.get());

		return schema;
	}

	private List<DataUnitPropertySchemaEntity> generateNewPropertySchemas() {
		final DataUnitPropertySchemaEntity propertySchema1 = generatePropertySchema(
				DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID, DATA_UNIT_TEXT_PROPERTY_SCHEMA_NAME, DATA_UNIT_TEXT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaEntity propertySchema2 = generatePropertySchema(
				DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_NAME, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaEntity propertySchema3 = generatePropertySchema(
				DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_NAME, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaEntity propertySchema4 = generatePropertySchema(
				DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_NAME, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaEntity propertySchema5 = generatePropertySchema(
				DATA_UNIT_DATE_PROPERTY_SCHEMA_ID, DATA_UNIT_DATE_PROPERTY_SCHEMA_NAME, DATA_UNIT_DATE_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaEntity propertySchema6 = generatePropertySchema(
				DATA_UNIT_TIME_PROPERTY_SCHEMA_ID, DATA_UNIT_TIME_PROPERTY_SCHEMA_NAME, DATA_UNIT_TIME_PROPERTY_SCHEMA_TYPE);

		return Arrays.asList(propertySchema1, propertySchema2, propertySchema3, propertySchema4,
				propertySchema5, propertySchema6);
	}

	private List<DataUnitPropertySchemaEntity> generateNewPropertySchemasNullId() {
		final DataUnitPropertySchemaEntity propertySchema1 = generatePropertySchema(
				null, DATA_UNIT_TEXT_PROPERTY_SCHEMA_NAME, DATA_UNIT_TEXT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaEntity propertySchema2 = generatePropertySchema(
				null, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_NAME, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaEntity propertySchema3 = generatePropertySchema(
				null, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_NAME, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaEntity propertySchema4 = generatePropertySchema(
				null, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_NAME, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaEntity propertySchema5 = generatePropertySchema(
				null, DATA_UNIT_DATE_PROPERTY_SCHEMA_NAME, DATA_UNIT_DATE_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaEntity propertySchema6 = generatePropertySchema(
				null, DATA_UNIT_TIME_PROPERTY_SCHEMA_NAME, DATA_UNIT_TIME_PROPERTY_SCHEMA_TYPE);

		return Arrays.asList(propertySchema1, propertySchema2, propertySchema3, propertySchema4,
				propertySchema5, propertySchema6);
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
	public List<DataUnitSchemaEntity> generateObjects() {
		final DataUnitSchemaEntity schema1 = generateSchema(
				DATA_UNIT_SCHEMA_ID_1, DATA_UNIT_SCHEMA_NAME_1, this::generateNewPropertySchemas);
		final DataUnitSchemaEntity schema2 = generateSchema(
				DATA_UNIT_SCHEMA_ID_2, DATA_UNIT_SCHEMA_NAME_2, this::generateNewPropertySchemas);
		final DataUnitSchemaEntity schema3 = generateSchema(
				DATA_UNIT_SCHEMA_ID_3, DATA_UNIT_SCHEMA_NAME_3, this::generateNewPropertySchemas);

		return Arrays.asList(schema1, schema2, schema3);
	}

}
