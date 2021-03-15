package com.hurynovich.data_storage.model.document;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class DataUnitPropertyDocument extends AbstractDocument {

	private Serializable value;

	public Serializable getValue() {
		return value;
	}

	public void setValue(final Serializable value) {
		this.value = value;
	}

}
