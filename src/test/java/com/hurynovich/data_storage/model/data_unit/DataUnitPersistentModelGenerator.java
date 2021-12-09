package com.hurynovich.data_storage.model.data_unit;

import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument.DataUnitPropertyDocument;

import java.util.Arrays;
import java.util.List;

import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_BOOLEAN_PROPERTY_VALUE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_DATE_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_DATE_PROPERTY_VALUE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_FLOAT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_ID_1;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_ID_2;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_ID_3;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_INTEGER_PROPERTY_VALUE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_SCHEMA_ID_1;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_SCHEMA_ID_2;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_SCHEMA_ID_3;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TEXT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TIME_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TIME_PROPERTY_VALUE;

public class DataUnitPersistentModelGenerator implements ModelGenerator<DataUnitPersistentModel> {

	@Override
	public DataUnitPersistentModel generate() {
		return generatePersistentDataUnit(DATA_UNIT_ID_1, DATA_UNIT_SCHEMA_ID_1);
	}

	private DataUnitPersistentModel generatePersistentDataUnit(final String id, final Long name) {
		final DataUnitDocument schema = new DataUnitDocument();

		schema.setId(id);
		schema.setSchemaId(name);
		schema.setProperties(generatePersistentProperties());

		return schema;
	}

	private List<DataUnitPropertyPersistentModel> generatePersistentProperties() {
		final DataUnitPropertyPersistentModel dataUnitProperty1 = generatePersistentProperty(
				DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID, DATA_UNIT_TEXT_PROPERTY_VALUE);
		final DataUnitPropertyPersistentModel dataUnitProperty2 = generatePersistentProperty(
				DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID, DATA_UNIT_INTEGER_PROPERTY_VALUE);
		final DataUnitPropertyPersistentModel dataUnitProperty3 = generatePersistentProperty(
				DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID, DATA_UNIT_FLOAT_PROPERTY_VALUE);
		final DataUnitPropertyPersistentModel dataUnitProperty4 = generatePersistentProperty(
				DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID, DATA_UNIT_BOOLEAN_PROPERTY_VALUE);
		final DataUnitPropertyPersistentModel dataUnitProperty5 = generatePersistentProperty(
				DATA_UNIT_DATE_PROPERTY_SCHEMA_ID, DATA_UNIT_DATE_PROPERTY_VALUE);
		final DataUnitPropertyPersistentModel dataUnitProperty6 = generatePersistentProperty(
				DATA_UNIT_TIME_PROPERTY_SCHEMA_ID, DATA_UNIT_TIME_PROPERTY_VALUE);

		return Arrays.asList(dataUnitProperty1, dataUnitProperty2, dataUnitProperty3, dataUnitProperty4,
				dataUnitProperty5, dataUnitProperty6);
	}

	private DataUnitPropertyPersistentModel generatePersistentProperty(final Long schemaId, final Object value) {
		final DataUnitPropertyDocument property = new DataUnitPropertyDocument();

		property.setSchemaId(schemaId);
		property.setValue(value);

		return property;
	}

	@Override
	public DataUnitPersistentModel generateNullId() {
		return generatePersistentDataUnit(null, DATA_UNIT_SCHEMA_ID_1);
	}

	@Override
	public List<DataUnitPersistentModel> generateList() {
		final DataUnitPersistentModel dataUnit1 = generatePersistentDataUnit(DATA_UNIT_ID_1, DATA_UNIT_SCHEMA_ID_1);
		final DataUnitPersistentModel dataUnit2 = generatePersistentDataUnit(DATA_UNIT_ID_2, DATA_UNIT_SCHEMA_ID_2);
		final DataUnitPersistentModel dataUnit3 = generatePersistentDataUnit(DATA_UNIT_ID_3, DATA_UNIT_SCHEMA_ID_3);

		return Arrays.asList(dataUnit1, dataUnit2, dataUnit3);
	}

	@Override
	public List<DataUnitPersistentModel> generateListNullId() {
		final DataUnitPersistentModel dataUnit1 = generatePersistentDataUnit(null, DATA_UNIT_SCHEMA_ID_1);
		final DataUnitPersistentModel dataUnit2 = generatePersistentDataUnit(null, DATA_UNIT_SCHEMA_ID_2);
		final DataUnitPersistentModel dataUnit3 = generatePersistentDataUnit(null, DATA_UNIT_SCHEMA_ID_3);

		return Arrays.asList(dataUnit1, dataUnit2, dataUnit3);
	}
}
