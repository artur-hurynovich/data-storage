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

	public class DataUnitPropertyDTO {

		private Long schemaId;

		private Object value;

		public Long getSchemaId() {
			return schemaId;
		}

		public void setSchemaId(final Long schemaId) {
			this.schemaId = schemaId;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(final Object value) {
			this.value = value;
		}

	}

}
