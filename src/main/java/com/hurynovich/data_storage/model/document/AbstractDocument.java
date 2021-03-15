package com.hurynovich.data_storage.model.document;

import com.hurynovich.data_storage.model.Identified;

import javax.persistence.Id;

public class AbstractDocument implements Identified<String> {

	@Id
	private String id;

	@Override
	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

}
