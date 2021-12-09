package com.hurynovich.data_storage.model.data_unit_schema;

import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModelImpl;
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

public class DataUnitSchemaServiceModelGenerator implements ModelGenerator<DataUnitSchemaServiceModel> {

	@Override
	public DataUnitSchemaServiceModel generate() {
		return new DataUnitSchemaServiceModelImpl(DATA_UNIT_SCHEMA_ID_1, DATA_UNIT_SCHEMA_NAME_1,
				generatePropertySchemaServiceModels());
	}

	private List<DataUnitPropertySchemaServiceModel> generatePropertySchemaServiceModels() {
		final DataUnitPropertySchemaServiceModel propertySchema1 = generatePropertySchemaServiceModel(
				DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID, DATA_UNIT_TEXT_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_TEXT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaServiceModel propertySchema2 = generatePropertySchemaServiceModel(
				DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_INTEGER_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaServiceModel propertySchema3 = generatePropertySchemaServiceModel(
				DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_FLOAT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaServiceModel propertySchema4 = generatePropertySchemaServiceModel(
				DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaServiceModel propertySchema5 = generatePropertySchemaServiceModel(
				DATA_UNIT_DATE_PROPERTY_SCHEMA_ID, DATA_UNIT_DATE_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_DATE_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaServiceModel propertySchema6 = generatePropertySchemaServiceModel(
				DATA_UNIT_TIME_PROPERTY_SCHEMA_ID, DATA_UNIT_TIME_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_TIME_PROPERTY_SCHEMA_TYPE);

		return Arrays.asList(propertySchema1, propertySchema2, propertySchema3, propertySchema4,
				propertySchema5, propertySchema6);
	}

	private DataUnitPropertySchemaServiceModel generatePropertySchemaServiceModel(final Long id, final String name,
																				  final DataUnitPropertyType type) {
		return new DataUnitPropertySchemaServiceModelImpl(id, name, type);
	}

	@Override
	public DataUnitSchemaServiceModel generateNullId() {
		return new DataUnitSchemaServiceModelImpl(null, DATA_UNIT_SCHEMA_NAME_1,
				generatePropertySchemaServiceModelsNullId());
	}

	private List<DataUnitPropertySchemaServiceModel> generatePropertySchemaServiceModelsNullId() {
		final DataUnitPropertySchemaServiceModel propertySchema1 = generatePropertySchemaServiceModel(
				null, DATA_UNIT_TEXT_PROPERTY_SCHEMA_NAME, DATA_UNIT_TEXT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaServiceModel propertySchema2 = generatePropertySchemaServiceModel(
				null, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_NAME, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaServiceModel propertySchema3 = generatePropertySchemaServiceModel(
				null, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_NAME, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaServiceModel propertySchema4 = generatePropertySchemaServiceModel(
				null, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_NAME, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaServiceModel propertySchema5 = generatePropertySchemaServiceModel(
				null, DATA_UNIT_DATE_PROPERTY_SCHEMA_NAME, DATA_UNIT_DATE_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaServiceModel propertySchema6 = generatePropertySchemaServiceModel(
				null, DATA_UNIT_TIME_PROPERTY_SCHEMA_NAME, DATA_UNIT_TIME_PROPERTY_SCHEMA_TYPE);

		return Arrays.asList(propertySchema1, propertySchema2, propertySchema3, propertySchema4,
				propertySchema5, propertySchema6);
	}

	@Override
	public List<DataUnitSchemaServiceModel> generateList() {
		final DataUnitSchemaServiceModel schema1 = new DataUnitSchemaServiceModelImpl(DATA_UNIT_SCHEMA_ID_1,
				DATA_UNIT_SCHEMA_NAME_1, generatePropertySchemaServiceModels());
		final DataUnitSchemaServiceModel schema2 = new DataUnitSchemaServiceModelImpl(DATA_UNIT_SCHEMA_ID_2,
				DATA_UNIT_SCHEMA_NAME_2, generatePropertySchemaServiceModels());
		final DataUnitSchemaServiceModel schema3 = new DataUnitSchemaServiceModelImpl(DATA_UNIT_SCHEMA_ID_3,
				DATA_UNIT_SCHEMA_NAME_3, generatePropertySchemaServiceModels());

		return Arrays.asList(schema1, schema2, schema3);
	}

	@Override
	public List<DataUnitSchemaServiceModel> generateListNullId() {
		final DataUnitSchemaServiceModel schema1 = new DataUnitSchemaServiceModelImpl(null,
				DATA_UNIT_SCHEMA_NAME_1, generatePropertySchemaServiceModelsNullId());
		final DataUnitSchemaServiceModel schema2 = new DataUnitSchemaServiceModelImpl(null,
				DATA_UNIT_SCHEMA_NAME_2, generatePropertySchemaServiceModelsNullId());
		final DataUnitSchemaServiceModel schema3 = new DataUnitSchemaServiceModelImpl(null,
				DATA_UNIT_SCHEMA_NAME_3, generatePropertySchemaServiceModelsNullId());

		return Arrays.asList(schema1, schema2, schema3);
	}
}
