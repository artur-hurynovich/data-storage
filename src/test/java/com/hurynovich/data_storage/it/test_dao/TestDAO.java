package com.hurynovich.data_storage.it.test_dao;

import com.hurynovich.data_storage.model.Identified;

import java.io.Serializable;

public interface TestDAO<T extends Identified<I>, I extends Serializable> {

	T save(T t);

	T findById(I id);

	void deleteById(I id);
}
