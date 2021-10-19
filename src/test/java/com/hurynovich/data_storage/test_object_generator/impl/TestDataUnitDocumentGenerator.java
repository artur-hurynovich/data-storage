package com.hurynovich.data_storage.test_object_generator.impl;

import com.hurynovich.data_storage.model.document.DataUnitDocument;
import com.hurynovich.data_storage.model.document.DataUnitDocument.DataUnitPropertyDocument;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;

import java.util.Arrays;
import java.util.List;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_ID_1;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_ID_2;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_ID_3;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_ID_4;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_PROPERTY_SCHEMA_ID_1;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_PROPERTY_SCHEMA_ID_2;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_PROPERTY_SCHEMA_ID_3;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_PROPERTY_VALUE_1;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_PROPERTY_VALUE_2;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_PROPERTY_VALUE_3;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_ID_1;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_ID_2;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_ID_3;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_ID_4;

public class TestDataUnitDocumentGenerator implements TestObjectGenerator<DataUnitDocument> {

	@Override
	public DataUnitDocument generateSingleObject() {
		return generateDataUnit(DATA_UNIT_ID_1, DATA_UNIT_SCHEMA_ID_1);
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
				generateProperty(DATA_UNIT_PROPERTY_SCHEMA_ID_1, DATA_UNIT_PROPERTY_VALUE_1);
		final DataUnitPropertyDocument dataUnitProperty2 =
				generateProperty(DATA_UNIT_PROPERTY_SCHEMA_ID_2, DATA_UNIT_PROPERTY_VALUE_2);
		final DataUnitPropertyDocument dataUnitProperty3 =
				generateProperty(DATA_UNIT_PROPERTY_SCHEMA_ID_3, DATA_UNIT_PROPERTY_VALUE_3);

		return Arrays.asList(dataUnitProperty1, dataUnitProperty2, dataUnitProperty3);
	}

	private DataUnitPropertyDocument generateProperty(final Long schemaId, final Object value) {
		final DataUnitPropertyDocument property = new DataUnitPropertyDocument();

		property.setSchemaId(schemaId);
		property.setValue(value);

		return property;
	}

	@Override
	public List<DataUnitDocument> generateMultipleObjects() {
		final DataUnitDocument dataUnit1 = generateDataUnit(DATA_UNIT_ID_2, DATA_UNIT_SCHEMA_ID_2);
		final DataUnitDocument dataUnit2 = generateDataUnit(DATA_UNIT_ID_3, DATA_UNIT_SCHEMA_ID_3);
		final DataUnitDocument dataUnit3 = generateDataUnit(DATA_UNIT_ID_4, DATA_UNIT_SCHEMA_ID_4);

		return Arrays.asList(dataUnit1, dataUnit2, dataUnit3);
	}

}
