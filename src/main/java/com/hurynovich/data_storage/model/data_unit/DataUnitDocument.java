package com.hurynovich.data_storage.model.data_unit;

import com.hurynovich.data_storage.model.AbstractDocument;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class DataUnitDocument extends AbstractDocument<String> implements DataUnitPersistentModel {

	private Long schemaId;

	private List<DataUnitPropertyPersistentModel> properties;

	@Override
	public Long getSchemaId() {
		return schemaId;
	}

	public void setSchemaId(final Long schemaId) {
		this.schemaId = schemaId;
	}

	@Override
	public List<DataUnitPropertyPersistentModel> getProperties() {
		return properties;
	}

	public void setProperties(final List<DataUnitPropertyPersistentModel> properties) {
		this.properties = properties;
	}

	public static class DataUnitPropertyDocument implements DataUnitPropertyPersistentModel {

		private Long schemaId;

		private Object value;

		@Override
		public Long getSchemaId() {
			return schemaId;
		}

		public void setSchemaId(final Long schemaId) {
			this.schemaId = schemaId;
		}

		@Override
		public Object getValue() {
			return value;
		}

		public void setValue(final Object value) {
			this.value = value;
		}
	}
}
