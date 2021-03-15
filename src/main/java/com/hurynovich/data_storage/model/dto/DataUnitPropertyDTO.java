package com.hurynovich.data_storage.model.dto;

import java.io.Serializable;

public class DataUnitPropertyDTO extends AbstractDTO<String> {

	private Long schemaId;

	private Serializable value;

	public Long getSchemaId() {
		return schemaId;
	}

	public void setSchemaId(final Long schemaId) {
		this.schemaId = schemaId;
	}

	public Serializable getValue() {
		return value;
	}

	public void setValue(final Serializable value) {
		this.value = value;
	}

}
