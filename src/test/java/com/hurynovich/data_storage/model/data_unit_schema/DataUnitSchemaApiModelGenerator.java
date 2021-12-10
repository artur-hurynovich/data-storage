package com.hurynovich.data_storage.model.data_unit_schema;

import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaApiModelImpl;
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

public class DataUnitSchemaApiModelGenerator implements ModelGenerator<DataUnitSchemaApiModel> {

	@Override
	public DataUnitSchemaApiModel generate() {
		return new DataUnitSchemaApiModelImpl(DATA_UNIT_SCHEMA_ID_1, DATA_UNIT_SCHEMA_NAME_1,
				generatePropertySchemaApiModels()) {
		};
	}

	private List<DataUnitPropertySchemaApiModel> generatePropertySchemaApiModels() {
		final DataUnitPropertySchemaApiModel propertySchema1 = generatePropertySchemaApiModel(
				DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID, DATA_UNIT_TEXT_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_TEXT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaApiModel propertySchema2 = generatePropertySchemaApiModel(
				DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_INTEGER_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaApiModel propertySchema3 = generatePropertySchemaApiModel(
				DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_FLOAT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaApiModel propertySchema4 = generatePropertySchemaApiModel(
				DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaApiModel propertySchema5 = generatePropertySchemaApiModel(
				DATA_UNIT_DATE_PROPERTY_SCHEMA_ID, DATA_UNIT_DATE_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_DATE_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaApiModel propertySchema6 = generatePropertySchemaApiModel(
				DATA_UNIT_TIME_PROPERTY_SCHEMA_ID, DATA_UNIT_TIME_PROPERTY_SCHEMA_NAME,
				DATA_UNIT_TIME_PROPERTY_SCHEMA_TYPE);

		return Arrays.asList(propertySchema1, propertySchema2, propertySchema3, propertySchema4,
				propertySchema5, propertySchema6);
	}

	private DataUnitPropertySchemaApiModel generatePropertySchemaApiModel(final Long id, final String name,
																		  final DataUnitPropertyType type) {
		return new DataUnitPropertySchemaApiModelImpl(id, name, type);
	}

	@Override
	public DataUnitSchemaApiModel generateNullId() {
		return new DataUnitSchemaApiModelImpl(null, DATA_UNIT_SCHEMA_NAME_1,
				generatePropertySchemaApiModelsNullId());
	}

	private List<DataUnitPropertySchemaApiModel> generatePropertySchemaApiModelsNullId() {
		final DataUnitPropertySchemaApiModel propertySchema1 = generatePropertySchemaApiModel(
				null, DATA_UNIT_TEXT_PROPERTY_SCHEMA_NAME, DATA_UNIT_TEXT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaApiModel propertySchema2 = generatePropertySchemaApiModel(
				null, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_NAME, DATA_UNIT_INTEGER_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaApiModel propertySchema3 = generatePropertySchemaApiModel(
				null, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_NAME, DATA_UNIT_FLOAT_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaApiModel propertySchema4 = generatePropertySchemaApiModel(
				null, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_NAME, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaApiModel propertySchema5 = generatePropertySchemaApiModel(
				null, DATA_UNIT_DATE_PROPERTY_SCHEMA_NAME, DATA_UNIT_DATE_PROPERTY_SCHEMA_TYPE);
		final DataUnitPropertySchemaApiModel propertySchema6 = generatePropertySchemaApiModel(
				null, DATA_UNIT_TIME_PROPERTY_SCHEMA_NAME, DATA_UNIT_TIME_PROPERTY_SCHEMA_TYPE);

		return Arrays.asList(propertySchema1, propertySchema2, propertySchema3, propertySchema4,
				propertySchema5, propertySchema6);
	}

	@Override
	public List<DataUnitSchemaApiModel> generateList() {
		final DataUnitSchemaApiModel schema1 = new DataUnitSchemaApiModelImpl(DATA_UNIT_SCHEMA_ID_1,
				DATA_UNIT_SCHEMA_NAME_1, generatePropertySchemaApiModels());
		final DataUnitSchemaApiModel schema2 = new DataUnitSchemaApiModelImpl(DATA_UNIT_SCHEMA_ID_2,
				DATA_UNIT_SCHEMA_NAME_2, generatePropertySchemaApiModels());
		final DataUnitSchemaApiModel schema3 = new DataUnitSchemaApiModelImpl(DATA_UNIT_SCHEMA_ID_3,
				DATA_UNIT_SCHEMA_NAME_3, generatePropertySchemaApiModels());

		return Arrays.asList(schema1, schema2, schema3);
	}

	@Override
	public List<DataUnitSchemaApiModel> generateListNullId() {
		final DataUnitSchemaApiModel schema1 = new DataUnitSchemaApiModelImpl(null,
				DATA_UNIT_SCHEMA_NAME_1, generatePropertySchemaApiModelsNullId());
		final DataUnitSchemaApiModel schema2 = new DataUnitSchemaApiModelImpl(null,
				DATA_UNIT_SCHEMA_NAME_2, generatePropertySchemaApiModelsNullId());
		final DataUnitSchemaApiModel schema3 = new DataUnitSchemaApiModelImpl(null,
				DATA_UNIT_SCHEMA_NAME_3, generatePropertySchemaApiModelsNullId());

		return Arrays.asList(schema1, schema2, schema3);
	}
}
