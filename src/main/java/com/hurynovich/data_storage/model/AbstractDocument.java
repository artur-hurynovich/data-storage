package com.hurynovich.data_storage.model;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class AbstractDocument<T extends Serializable> implements Identified<T> {

	@Id
	private T id;

	@Override
	public T getId() {
		return id;
	}

	public void setId(final T id) {
		this.id = id;
	}

}
