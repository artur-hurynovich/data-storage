package com.hurynovich.data_storage.model;

import java.io.Serializable;

public abstract class AbstractApiModel<T extends Serializable> implements ApiModel<T> {

	private final T id;

	protected AbstractApiModel(final T id) {
		this.id = id;
	}

	@Override
	public T getId() {
		return id;
	}
}
