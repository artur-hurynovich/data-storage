package com.hurynovich.data_storage.model.document;

import com.hurynovich.data_storage.model.Identified;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class DataUnitDocument implements Identified<String> {

	@Id
	private String id;

	private Long schemaId;

	private List<DataUnitPropertyDocument> properties;

	@Override
	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Long getSchemaId() {
		return schemaId;
	}

	public void setSchemaId(final Long schemaId) {
		this.schemaId = schemaId;
	}

	public List<DataUnitPropertyDocument> getProperties() {
		return properties;
	}

	public void setProperties(final List<DataUnitPropertyDocument> properties) {
		this.properties = properties;
	}

	public static class DataUnitPropertyDocument {

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
