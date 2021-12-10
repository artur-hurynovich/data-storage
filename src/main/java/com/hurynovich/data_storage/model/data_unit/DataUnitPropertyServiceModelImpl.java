package com.hurynovich.data_storage.model.data_unit;

import com.hurynovich.GenerateMetamodel;

@GenerateMetamodel
public class DataUnitPropertyServiceModelImpl implements DataUnitPropertyServiceModel {

	private final Long schemaId;

	private final Object value;

	public DataUnitPropertyServiceModelImpl(final Long schemaId, final Object value) {
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
