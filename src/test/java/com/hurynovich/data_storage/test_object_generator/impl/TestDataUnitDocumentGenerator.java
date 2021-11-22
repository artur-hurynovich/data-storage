package com.hurynovich.data_storage.test_object_generator.impl;

import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument.DataUnitPropertyDocument;
import com.hurynovich.data_storage.test_object_generator.TestIdentifiedObjectGenerator;

import java.util.Arrays;
import java.util.List;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_BOOLEAN_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_DATE_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_DATE_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_FLOAT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_ID_1;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_ID_2;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_ID_3;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_INTEGER_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_ID_1;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_ID_2;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_ID_3;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TIME_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TIME_PROPERTY_VALUE;

public class TestDataUnitDocumentGenerator implements TestIdentifiedObjectGenerator<DataUnitDocument> {

	@Override
	public DataUnitDocument generateObject() {
		return generateDataUnit(DATA_UNIT_ID_1, DATA_UNIT_SCHEMA_ID_1);
	}

	@Override
	public DataUnitDocument generateObjectNullId() {
		return generateDataUnit(null, DATA_UNIT_SCHEMA_ID_1);
	}

	private DataUnitDocument generateDataUnit(final String id, final Long schemaId) {
		final DataUnitDocument dataUnit = new DataUnitDocument();

		dataUnit.setId(id);
		dataUnit.setSchemaId(schemaId);

		dataUnit.setProperties(generateProperties());

		return dataUnit;
	}

	private List<DataUnitPropertyDocument> generateProperties() {
		final DataUnitPropertyDocument dataUnitProperty1 =
				generateProperty(DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID, DATA_UNIT_TEXT_PROPERTY_VALUE);
		final DataUnitPropertyDocument dataUnitProperty2 =
				generateProperty(DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID, DATA_UNIT_INTEGER_PROPERTY_VALUE);
		final DataUnitPropertyDocument dataUnitProperty3 =
				generateProperty(DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID, DATA_UNIT_FLOAT_PROPERTY_VALUE);
		final DataUnitPropertyDocument dataUnitProperty4 =
				generateProperty(DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID, DATA_UNIT_BOOLEAN_PROPERTY_VALUE);
		final DataUnitPropertyDocument dataUnitProperty5 =
				generateProperty(DATA_UNIT_DATE_PROPERTY_SCHEMA_ID, DATA_UNIT_DATE_PROPERTY_VALUE);
		final DataUnitPropertyDocument dataUnitProperty6 =
				generateProperty(DATA_UNIT_TIME_PROPERTY_SCHEMA_ID, DATA_UNIT_TIME_PROPERTY_VALUE);

		return Arrays.asList(dataUnitProperty1, dataUnitProperty2, dataUnitProperty3, dataUnitProperty4,
				dataUnitProperty5, dataUnitProperty6);
	}

	private DataUnitPropertyDocument generateProperty(final Long schemaId, final Object value) {
		final DataUnitPropertyDocument property = new DataUnitPropertyDocument();

		property.setSchemaId(schemaId);
		property.setValue(value);

		return property;
	}

	@Override
	public List<DataUnitDocument> generateObjects() {
		final DataUnitDocument dataUnit1 = generateDataUnit(DATA_UNIT_ID_1, DATA_UNIT_SCHEMA_ID_1);
		final DataUnitDocument dataUnit2 = generateDataUnit(DATA_UNIT_ID_2, DATA_UNIT_SCHEMA_ID_2);
		final DataUnitDocument dataUnit3 = generateDataUnit(DATA_UNIT_ID_3, DATA_UNIT_SCHEMA_ID_3);

		return Arrays.asList(dataUnit1, dataUnit2, dataUnit3);
	}

	@Override
	public List<DataUnitDocument> generateObjectsNullId() {
		final DataUnitDocument dataUnit1 = generateDataUnit(null, DATA_UNIT_SCHEMA_ID_1);
		final DataUnitDocument dataUnit2 = generateDataUnit(null, DATA_UNIT_SCHEMA_ID_2);
		final DataUnitDocument dataUnit3 = generateDataUnit(null, DATA_UNIT_SCHEMA_ID_3);

		return Arrays.asList(dataUnit1, dataUnit2, dataUnit3);
	}

}
