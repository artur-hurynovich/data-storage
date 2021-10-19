package com.hurynovich.data_storage.test_object_generator.impl;

import com.hurynovich.data_storage.model.dto.DataUnitDTO;
import com.hurynovich.data_storage.model.dto.DataUnitDTO.DataUnitPropertyDTO;
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

public class TestDataUnitDTOGenerator implements TestObjectGenerator<DataUnitDTO> {

	@Override
	public DataUnitDTO generateSingleObject() {
		return generateDataUnit(DATA_UNIT_ID_1, DATA_UNIT_SCHEMA_ID_1);
	}

	private DataUnitDTO generateDataUnit(final String id, final Long schemaId) {
		final DataUnitDTO dataUnit = new DataUnitDTO();

		dataUnit.setId(id);
		dataUnit.setSchemaId(schemaId);

		dataUnit.setProperties(generateProperties());

		return dataUnit;
	}

	private List<DataUnitPropertyDTO> generateProperties() {
		final DataUnitPropertyDTO dataUnitProperty1 =
				generateProperty(DATA_UNIT_PROPERTY_SCHEMA_ID_1, DATA_UNIT_PROPERTY_VALUE_1);
		final DataUnitPropertyDTO dataUnitProperty2 =
				generateProperty(DATA_UNIT_PROPERTY_SCHEMA_ID_2, DATA_UNIT_PROPERTY_VALUE_2);
		final DataUnitPropertyDTO dataUnitProperty3 =
				generateProperty(DATA_UNIT_PROPERTY_SCHEMA_ID_3, DATA_UNIT_PROPERTY_VALUE_3);

		return Arrays.asList(dataUnitProperty1, dataUnitProperty2, dataUnitProperty3);
	}

	private DataUnitPropertyDTO generateProperty(final Long schemaId, final Object value) {
		final DataUnitPropertyDTO property = new DataUnitPropertyDTO();

		property.setSchemaId(schemaId);
		property.setValue(value);

		return property;
	}

	@Override
	public List<DataUnitDTO> generateMultipleObjects() {
		final DataUnitDTO dataUnit1 = generateDataUnit(DATA_UNIT_ID_2, DATA_UNIT_SCHEMA_ID_2);
		final DataUnitDTO dataUnit2 = generateDataUnit(DATA_UNIT_ID_3, DATA_UNIT_SCHEMA_ID_3);
		final DataUnitDTO dataUnit3 = generateDataUnit(DATA_UNIT_ID_4, DATA_UNIT_SCHEMA_ID_4);

		return Arrays.asList(dataUnit1, dataUnit2, dataUnit3);
	}

}
