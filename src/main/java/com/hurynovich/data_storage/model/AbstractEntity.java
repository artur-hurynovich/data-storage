package com.hurynovich.data_storage.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class AbstractEntity<T extends Serializable> implements Identified<T> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private T id;

	@Override
	public T getId() {
		return id;
	}

	public void setId(final T id) {
		this.id = id;
	}

}
