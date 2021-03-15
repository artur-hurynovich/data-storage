package com.hurynovich.data_storage.model.dto;

import java.util.List;

public class DataUnitDTO extends AbstractDTO<String> {

	private Long schemaId;

	private List<DataUnitPropertyDTO> properties;

	public Long getSchemaId() {
		return schemaId;
	}

	public void setSchemaId(final Long schemaId) {
		this.schemaId = schemaId;
	}

	public List<DataUnitPropertyDTO> getProperties() {
		return properties;
	}

	public void setProperties(final List<DataUnitPropertyDTO> properties) {
		this.properties = properties;
	}

}
