package com.hurynovich.data_storage.test_objects_asserter.model;

import com.hurynovich.data_storage.model.data_unit.DataUnitDTO.DataUnitPropertyDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument.DataUnitPropertyDocument;

public class DataUnitPropertyWrapper {

	private final Long schemaId;

	private final Object value;

	public DataUnitPropertyWrapper(final Long schemaId, final Object value) {
		this.schemaId = schemaId;
		this.value = value;
	}

	public static DataUnitPropertyWrapper of(final DataUnitPropertyDTO dataUnitProperty) {
		return new DataUnitPropertyWrapper(dataUnitProperty.getSchemaId(), dataUnitProperty.getValue());
	}

	public static DataUnitPropertyWrapper of(final DataUnitPropertyDocument dataUnitProperty) {
		return new DataUnitPropertyWrapper(dataUnitProperty.getSchemaId(), dataUnitProperty.getValue());
	}

	public Long getSchemaId() {
		return schemaId;
	}

	public Object getValue() {
		return value;
	}

}
