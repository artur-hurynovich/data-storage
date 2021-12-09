package com.hurynovich.data_storage.model;

import com.hurynovich.GenerateMetamodel;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@GenerateMetamodel
public class AbstractDocument<T extends Serializable> implements PersistentModel<T> {

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
