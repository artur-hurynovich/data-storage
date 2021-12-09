package com.hurynovich.data_storage.model.data_unit;

import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModelImpl.DataUnitPropertyServiceModelImpl;

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

public class DataUnitServiceModelGenerator implements ModelGenerator<DataUnitServiceModel> {

	@Override
	public DataUnitServiceModel generate() {
		return new DataUnitServiceModelImpl(DATA_UNIT_ID_1, DATA_UNIT_SCHEMA_ID_1, generateProperties());
	}

	private List<DataUnitPropertyServiceModel> generateProperties() {
		final DataUnitPropertyServiceModel dataUnitProperty1 = generateProperty(DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID,
				DATA_UNIT_TEXT_PROPERTY_VALUE);
		final DataUnitPropertyServiceModel dataUnitProperty2 = generateProperty(DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID,
				DATA_UNIT_INTEGER_PROPERTY_VALUE);
		final DataUnitPropertyServiceModel dataUnitProperty3 = generateProperty(DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID,
				DATA_UNIT_FLOAT_PROPERTY_VALUE);
		final DataUnitPropertyServiceModel dataUnitProperty4 = generateProperty(DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID,
				DATA_UNIT_BOOLEAN_PROPERTY_VALUE);
		final DataUnitPropertyServiceModel dataUnitProperty5 = generateProperty(DATA_UNIT_DATE_PROPERTY_SCHEMA_ID,
				DATA_UNIT_DATE_PROPERTY_VALUE);
		final DataUnitPropertyServiceModel dataUnitProperty6 = generateProperty(DATA_UNIT_TIME_PROPERTY_SCHEMA_ID,
				DATA_UNIT_TIME_PROPERTY_VALUE);

		return Arrays.asList(dataUnitProperty1, dataUnitProperty2, dataUnitProperty3, dataUnitProperty4,
				dataUnitProperty5, dataUnitProperty6);
	}

	private DataUnitPropertyServiceModel generateProperty(final Long schemaId, final Object value) {
		return new DataUnitPropertyServiceModelImpl(schemaId, value);
	}

	@Override
	public DataUnitServiceModel generateNullId() {
		return new DataUnitServiceModelImpl(null, DATA_UNIT_SCHEMA_ID_1, generateProperties());
	}

	@Override
	public List<DataUnitServiceModel> generateList() {
		final DataUnitServiceModel dataUnit1 = new DataUnitServiceModelImpl(DATA_UNIT_ID_1, DATA_UNIT_SCHEMA_ID_1,
				generateProperties());
		final DataUnitServiceModel dataUnit2 = new DataUnitServiceModelImpl(DATA_UNIT_ID_2, DATA_UNIT_SCHEMA_ID_2,
				generateProperties());
		final DataUnitServiceModel dataUnit3 = new DataUnitServiceModelImpl(DATA_UNIT_ID_3, DATA_UNIT_SCHEMA_ID_3,
				generateProperties());

		return Arrays.asList(dataUnit1, dataUnit2, dataUnit3);
	}

	@Override
	public List<DataUnitServiceModel> generateListNullId() {
		final DataUnitServiceModel dataUnit1 = new DataUnitServiceModelImpl(null, DATA_UNIT_SCHEMA_ID_1,
				generateProperties());
		final DataUnitServiceModel dataUnit2 = new DataUnitServiceModelImpl(null, DATA_UNIT_SCHEMA_ID_2,
				generateProperties());
		final DataUnitServiceModel dataUnit3 = new DataUnitServiceModelImpl(null, DATA_UNIT_SCHEMA_ID_3,
				generateProperties());

		return Arrays.asList(dataUnit1, dataUnit2, dataUnit3);
	}
}
