package com.hurynovich.data_storage.model.data_unit;

import com.hurynovich.GenerateMetamodel;
import com.hurynovich.data_storage.model.AbstractDocument;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@GenerateMetamodel
@Document
public class DataUnitDocument extends AbstractDocument<String> {

	private Long schemaId;

	private List<DataUnitPropertyDocument> properties;

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

	@GenerateMetamodel
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
