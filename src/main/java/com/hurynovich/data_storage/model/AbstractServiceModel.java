package com.hurynovich.data_storage.model;

import com.hurynovich.GenerateMetamodel;

import java.io.Serializable;

@GenerateMetamodel
public abstract class AbstractServiceModel<T extends Serializable> implements ServiceModel<T> {

	private final T id;

	protected AbstractServiceModel(final T id) {
		this.id = id;
	}

	@Override
	public T getId() {
		return id;
	}
}
