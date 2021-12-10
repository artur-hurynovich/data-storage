package com.hurynovich.data_storage.model.data_unit;

import com.hurynovich.data_storage.model.ModelGenerator;

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

public class DataUnitApiModelGenerator implements ModelGenerator<DataUnitApiModel> {

	@Override
	public DataUnitApiModel generate() {
		return new DataUnitApiModelImpl(DATA_UNIT_ID_1, DATA_UNIT_SCHEMA_ID_1, generateProperties());
	}

	private List<DataUnitPropertyApiModel> generateProperties() {
		final DataUnitPropertyApiModel dataUnitProperty1 = generateProperty(DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID,
				DATA_UNIT_TEXT_PROPERTY_VALUE);
		final DataUnitPropertyApiModel dataUnitProperty2 = generateProperty(DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID,
				DATA_UNIT_INTEGER_PROPERTY_VALUE);
		final DataUnitPropertyApiModel dataUnitProperty3 = generateProperty(DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID,
				DATA_UNIT_FLOAT_PROPERTY_VALUE);
		final DataUnitPropertyApiModel dataUnitProperty4 = generateProperty(DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID,
				DATA_UNIT_BOOLEAN_PROPERTY_VALUE);
		final DataUnitPropertyApiModel dataUnitProperty5 = generateProperty(DATA_UNIT_DATE_PROPERTY_SCHEMA_ID,
				DATA_UNIT_DATE_PROPERTY_VALUE);
		final DataUnitPropertyApiModel dataUnitProperty6 = generateProperty(DATA_UNIT_TIME_PROPERTY_SCHEMA_ID,
				DATA_UNIT_TIME_PROPERTY_VALUE);

		return Arrays.asList(dataUnitProperty1, dataUnitProperty2, dataUnitProperty3, dataUnitProperty4,
				dataUnitProperty5, dataUnitProperty6);
	}

	private DataUnitPropertyApiModel generateProperty(final Long schemaId, final Object value) {
		return new DataUnitPropertyApiModelImpl(schemaId, value);
	}

	@Override
	public DataUnitApiModel generateNullId() {
		return new DataUnitApiModelImpl(null, DATA_UNIT_SCHEMA_ID_1, generateProperties());
	}

	@Override
	public List<DataUnitApiModel> generateList() {
		final DataUnitApiModel dataUnit1 = new DataUnitApiModelImpl(DATA_UNIT_ID_1, DATA_UNIT_SCHEMA_ID_1,
				generateProperties());
		final DataUnitApiModel dataUnit2 = new DataUnitApiModelImpl(DATA_UNIT_ID_2, DATA_UNIT_SCHEMA_ID_2,
				generateProperties());
		final DataUnitApiModel dataUnit3 = new DataUnitApiModelImpl(DATA_UNIT_ID_3, DATA_UNIT_SCHEMA_ID_3,
				generateProperties());

		return Arrays.asList(dataUnit1, dataUnit2, dataUnit3);
	}

	@Override
	public List<DataUnitApiModel> generateListNullId() {
		final DataUnitApiModel dataUnit1 = new DataUnitApiModelImpl(null, DATA_UNIT_SCHEMA_ID_1,
				generateProperties());
		final DataUnitApiModel dataUnit2 = new DataUnitApiModelImpl(null, DATA_UNIT_SCHEMA_ID_2,
				generateProperties());
		final DataUnitApiModel dataUnit3 = new DataUnitApiModelImpl(null, DATA_UNIT_SCHEMA_ID_3,
				generateProperties());

		return Arrays.asList(dataUnit1, dataUnit2, dataUnit3);
	}
}
