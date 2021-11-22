package com.hurynovich.data_storage.test_object_generator.impl;

import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
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

public class TestDataUnitSchemaDTOGenerator implements TestIdentifiedObjectGenerator<DataUnitSchemaDTO> {

	@Override
	public DataUnitSchemaDTO generateObject() {
		return generateSchema(DATA_UNIT_SCHEMA_ID_1, DATA_UNIT_SCHEMA_NAME_1, this::generatePropertySchemas);
	}

	@Override
	public DataUnitSchemaDTO generateObjectNullId() {
		return generateSchema(null, DATA_UNIT_SCHEMA_NAME_1, this::generatePropertySchemasNullId);
	}

	private DataUnitSchemaDTO generateSchema(final Long id, final String name,
											 final Supplier<List<DataUnitPropertySchemaDTO>> propertySchemasSupplier) {
		return new DataUnitSchemaDTO(id, name, propertySchemasSupplier.get());
	}

	private List<DataUnitPropertySchemaDTO> generatePropertySchemas() {
		final DataUnitPropertySchemaDTO propertySchema1 = generatePropertySchema(
				DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID, DATA_UNIT_TEXT_PROPERTY_SCHEMA_NAME, DATA_UNIT_TEXT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaDTO propertySchema2 = generatePropertySchema(
				DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_NAME, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaDTO propertySchema3 = generatePropertySchema(
				DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_NAME, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaDTO propertySchema4 = generatePropertySchema(
				DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_NAME, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaDTO propertySchema5 = generatePropertySchema(
				DATA_UNIT_DATE_PROPERTY_SCHEMA_ID, DATA_UNIT_DATE_PROPERTY_SCHEMA_NAME, DATA_UNIT_DATE_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaDTO propertySchema6 = generatePropertySchema(
				DATA_UNIT_TIME_PROPERTY_SCHEMA_ID, DATA_UNIT_TIME_PROPERTY_SCHEMA_NAME, DATA_UNIT_TIME_PROPERTY_SCHEMA_TYPE);

		return Arrays.asList(propertySchema1, propertySchema2, propertySchema3, propertySchema4,
				propertySchema5, propertySchema6);
	}

	private List<DataUnitPropertySchemaDTO> generatePropertySchemasNullId() {
		final DataUnitPropertySchemaDTO propertySchema1 = generatePropertySchema(
				null, DATA_UNIT_TEXT_PROPERTY_SCHEMA_NAME, DATA_UNIT_TEXT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaDTO propertySchema2 = generatePropertySchema(
				null, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_NAME, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaDTO propertySchema3 = generatePropertySchema(
				null, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_NAME, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaDTO propertySchema4 = generatePropertySchema(
				null, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_NAME, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaDTO propertySchema5 = generatePropertySchema(
				null, DATA_UNIT_DATE_PROPERTY_SCHEMA_NAME, DATA_UNIT_DATE_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaDTO propertySchema6 = generatePropertySchema(
				null, DATA_UNIT_TIME_PROPERTY_SCHEMA_NAME, DATA_UNIT_TIME_PROPERTY_SCHEMA_TYPE);

		return Arrays.asList(propertySchema1, propertySchema2, propertySchema3, propertySchema4,
				propertySchema5, propertySchema6);
	}

	private DataUnitPropertySchemaDTO generatePropertySchema(final Long id, final String name,
															 final DataUnitPropertyType type) {
		return new DataUnitPropertySchemaDTO(id, name, type);
	}

	@Override
	public List<DataUnitSchemaDTO> generateObjects() {
		final DataUnitSchemaDTO schema1 = generateSchema(
				DATA_UNIT_SCHEMA_ID_1, DATA_UNIT_SCHEMA_NAME_1, this::generatePropertySchemas);
		final DataUnitSchemaDTO schema2 = generateSchema(
				DATA_UNIT_SCHEMA_ID_2, DATA_UNIT_SCHEMA_NAME_2, this::generatePropertySchemas);
		final DataUnitSchemaDTO schema3 = generateSchema(
				DATA_UNIT_SCHEMA_ID_3, DATA_UNIT_SCHEMA_NAME_3, this::generatePropertySchemas);

		return Arrays.asList(schema1, schema2, schema3);
	}

	@Override
	public List<DataUnitSchemaDTO> generateObjectsNullId() {
		final DataUnitSchemaDTO schema1 = generateSchema(
				null, DATA_UNIT_SCHEMA_NAME_1, this::generatePropertySchemasNullId);
		final DataUnitSchemaDTO schema2 = generateSchema(
				null, DATA_UNIT_SCHEMA_NAME_1, this::generatePropertySchemasNullId);
		final DataUnitSchemaDTO schema3 = generateSchema(
				null, DATA_UNIT_SCHEMA_NAME_3, this::generatePropertySchemasNullId);

		return Arrays.asList(schema1, schema2, schema3);
	}

}
