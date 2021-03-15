package com.hurynovich.data_storage.model.document;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class DataUnitDocument extends AbstractDocument {

	private List<DataUnitPropertyDocument> properties;

	public List<DataUnitPropertyDocument> getProperties() {
		return properties;
	}

	public void setProperties(final List<DataUnitPropertyDocument> properties) {
		this.properties = properties;
	}

}
