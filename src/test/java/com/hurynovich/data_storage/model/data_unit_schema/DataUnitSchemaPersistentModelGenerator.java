package com.hurynovich.data_storage.model.data_unit_schema;

import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;

import java.util.Arrays;
import java.util.List;

import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_NAME;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_TYPE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_DATE_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_DATE_PROPERTY_SCHEMA_NAME;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_DATE_PROPERTY_SCHEMA_TYPE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_FLOAT_PROPERTY_SCHEMA_NAME;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_FLOAT_PROPERTY_SCHEMA_TYPE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_INTEGER_PROPERTY_SCHEMA_NAME;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_INTEGER_PROPERTY_SCHEMA_TYPE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_SCHEMA_ID_1;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_SCHEMA_ID_2;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_SCHEMA_ID_3;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_SCHEMA_NAME_1;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_SCHEMA_NAME_2;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_SCHEMA_NAME_3;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_NAME;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_TYPE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TIME_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TIME_PROPERTY_SCHEMA_NAME;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TIME_PROPERTY_SCHEMA_TYPE;

public class DataUnitSchemaPersistentModelGenerator implements ModelGenerator<DataUnitSchemaPersistentModel> {

	@Override
	public DataUnitSchemaPersistentModel generate() {
		return generatePersistentSchema(DATA_UNIT_SCHEMA_ID_1, DATA_UNIT_SCHEMA_NAME_1,
				generatePersistentPropertySchemas());
	}

	private DataUnitSchemaPersistentModel generatePersistentSchema(final Long id, final String name,
																   final List<DataUnitPropertySchemaPersistentModel> propertySchemas) {
		final DataUnitSchemaEntity schema = new DataUnitSchemaEntity();

		schema.setId(id);
		schema.setName(name);
		schema.setPropertySchemas(propertySchemas);

		return schema;
	}

	private List<DataUnitPropertySchemaPersistentModel> generatePersistentPropertySchemas() {
		final DataUnitPropertySchemaPersistentModel propertySchema1 = generatePersistentPropertySchema(
				DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID, DATA_UNIT_TEXT_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_TEXT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaPersistentModel propertySchema2 = generatePersistentPropertySchema(
				DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_INTEGER_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaPersistentModel propertySchema3 = generatePersistentPropertySchema(
				DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_FLOAT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaPersistentModel propertySchema4 = generatePersistentPropertySchema(
				DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaPersistentModel propertySchema5 = generatePersistentPropertySchema(
				DATA_UNIT_DATE_PROPERTY_SCHEMA_ID, DATA_UNIT_DATE_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_DATE_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaPersistentModel propertySchema6 = generatePersistentPropertySchema(
				DATA_UNIT_TIME_PROPERTY_SCHEMA_ID, DATA_UNIT_TIME_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_TIME_PROPERTY_SCHEMA_TYPE);

		return Arrays.asList(propertySchema1, propertySchema2, propertySchema3, propertySchema4,
				propertySchema5, propertySchema6);
	}

	private DataUnitPropertySchemaPersistentModel generatePersistentPropertySchema(final Long id, final String name,
																				   final DataUnitPropertyType type) {
		final DataUnitPropertySchemaEntity propertySchema = new DataUnitPropertySchemaEntity();

		propertySchema.setId(id);
		propertySchema.setName(name);
		propertySchema.setType(type);

		return propertySchema;
	}

	@Override
	public DataUnitSchemaPersistentModel generateNullId() {
		return generatePersistentSchema(null, DATA_UNIT_SCHEMA_NAME_1, generatePersistentPropertySchemasNullId());
	}

	private List<DataUnitPropertySchemaPersistentModel> generatePersistentPropertySchemasNullId() {
		final DataUnitPropertySchemaPersistentModel propertySchema1 = generatePersistentPropertySchema(
				null, DATA_UNIT_TEXT_PROPERTY_SCHEMA_NAME, DATA_UNIT_TEXT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaPersistentModel propertySchema2 = generatePersistentPropertySchema(
				null, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_NAME, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaPersistentModel propertySchema3 = generatePersistentPropertySchema(
				null, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_NAME, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaPersistentModel propertySchema4 = generatePersistentPropertySchema(
				null, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_NAME, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaPersistentModel propertySchema5 = generatePersistentPropertySchema(
				null, DATA_UNIT_DATE_PROPERTY_SCHEMA_NAME, DATA_UNIT_DATE_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaPersistentModel propertySchema6 = generatePersistentPropertySchema(
				null, DATA_UNIT_TIME_PROPERTY_SCHEMA_NAME, DATA_UNIT_TIME_PROPERTY_SCHEMA_TYPE);

		return Arrays.asList(propertySchema1, propertySchema2, propertySchema3, propertySchema4,
				propertySchema5, propertySchema6);
	}

	@Override
	public List<DataUnitSchemaPersistentModel> generateList() {
		final DataUnitSchemaPersistentModel schema1 = generatePersistentSchema(DATA_UNIT_SCHEMA_ID_1,
				DATA_UNIT_SCHEMA_NAME_1, generatePersistentPropertySchemas());
		final DataUnitSchemaPersistentModel schema2 = generatePersistentSchema(DATA_UNIT_SCHEMA_ID_2,
				DATA_UNIT_SCHEMA_NAME_2, generatePersistentPropertySchemas());
		final DataUnitSchemaPersistentModel schema3 = generatePersistentSchema(DATA_UNIT_SCHEMA_ID_3,
				DATA_UNIT_SCHEMA_NAME_3, generatePersistentPropertySchemas());

		return Arrays.asList(schema1, schema2, schema3);
	}

	@Override
	public List<DataUnitSchemaPersistentModel> generateListNullId() {
		final DataUnitSchemaPersistentModel schema1 = generatePersistentSchema(null, DATA_UNIT_SCHEMA_NAME_1,
				generatePersistentPropertySchemasNullId());
		final DataUnitSchemaPersistentModel schema2 = generatePersistentSchema(null, DATA_UNIT_SCHEMA_NAME_2,
				generatePersistentPropertySchemasNullId());
		final DataUnitSchemaPersistentModel schema3 = generatePersistentSchema(null, DATA_UNIT_SCHEMA_NAME_3,
				generatePersistentPropertySchemasNullId());

		return Arrays.asList(schema1, schema2, schema3);
	}
}
