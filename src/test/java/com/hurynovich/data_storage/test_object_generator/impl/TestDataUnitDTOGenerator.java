package com.hurynovich.data_storage.test_object_generator.impl;

import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO.DataUnitPropertyDTO;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;

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
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_ID_4;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_INTEGER_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_ID_1;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_ID_2;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_ID_3;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_SCHEMA_ID_4;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TIME_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TIME_PROPERTY_VALUE;

public class TestDataUnitDTOGenerator implements TestObjectGenerator<DataUnitDTO> {

	@Override
	public DataUnitDTO generateSingleObject() {
		return generateDataUnit(DATA_UNIT_ID_1, DATA_UNIT_SCHEMA_ID_1);
	}

	private DataUnitDTO generateDataUnit(final String id, final Long schemaId) {
		return new DataUnitDTO(id, schemaId, generateProperties());
	}

	private List<DataUnitPropertyDTO> generateProperties() {
		final DataUnitPropertyDTO dataUnitProperty1 =
				generateProperty(DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID, DATA_UNIT_TEXT_PROPERTY_VALUE);
		final DataUnitPropertyDTO dataUnitProperty2 =
				generateProperty(DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID, DATA_UNIT_INTEGER_PROPERTY_VALUE);
		final DataUnitPropertyDTO dataUnitProperty3 =
				generateProperty(DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID, DATA_UNIT_FLOAT_PROPERTY_VALUE);
		final DataUnitPropertyDTO dataUnitProperty4 =
				generateProperty(DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID, DATA_UNIT_BOOLEAN_PROPERTY_VALUE);
		final DataUnitPropertyDTO dataUnitProperty5 =
				generateProperty(DATA_UNIT_DATE_PROPERTY_SCHEMA_ID, DATA_UNIT_DATE_PROPERTY_VALUE);
		final DataUnitPropertyDTO dataUnitProperty6 =
				generateProperty(DATA_UNIT_TIME_PROPERTY_SCHEMA_ID, DATA_UNIT_TIME_PROPERTY_VALUE);

		return Arrays.asList(dataUnitProperty1, dataUnitProperty2, dataUnitProperty3, dataUnitProperty4, dataUnitProperty5, dataUnitProperty6);
	}

	private DataUnitPropertyDTO generateProperty(final Long schemaId, final Object value) {
		return new DataUnitPropertyDTO(schemaId, value);
	}

	@Override
	public List<DataUnitDTO> generateMultipleObjects() {
		final DataUnitDTO dataUnit1 = generateDataUnit(DATA_UNIT_ID_2, DATA_UNIT_SCHEMA_ID_2);
		final DataUnitDTO dataUnit2 = generateDataUnit(DATA_UNIT_ID_3, DATA_UNIT_SCHEMA_ID_3);
		final DataUnitDTO dataUnit3 = generateDataUnit(DATA_UNIT_ID_4, DATA_UNIT_SCHEMA_ID_4);

		return Arrays.asList(dataUnit1, dataUnit2, dataUnit3);
	}

}
