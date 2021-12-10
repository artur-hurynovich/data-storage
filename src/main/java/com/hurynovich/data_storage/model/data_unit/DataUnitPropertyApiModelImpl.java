package com.hurynovich.data_storage.model.data_unit;

public class DataUnitPropertyApiModelImpl implements DataUnitPropertyApiModel {

	private final Long schemaId;

	private final Object value;

	public DataUnitPropertyApiModelImpl(final Long schemaId, final Object value) {
		this.schemaId = schemaId;
		this.value = value;
	}

	@Override
	public Long getSchemaId() {
		return schemaId;
	}

	@Override
	public Object getValue() {
		return value;
	}
}
