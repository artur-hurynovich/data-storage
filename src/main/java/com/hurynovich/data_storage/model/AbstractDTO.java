package com.hurynovich.data_storage.model;

import java.io.Serializable;

public abstract class AbstractDTO<T extends Serializable> implements Identified<T> {

	private final T id;

	protected AbstractDTO(final T id) {
		this.id = id;
	}

	@Override
	public T getId() {
		return id;
	}

}
