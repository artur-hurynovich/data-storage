package com.hurynovich.data_storage.model;

import java.io.Serializable;

public abstract class AbstractDTO<T extends Serializable> implements Identified<T> {

	private T id;

	@Override
	public T getId() {
		return id;
	}

	public void setId(final T id) {
		this.id = id;
	}

}
