package com.hurynovich.data_storage.dao;

import com.hurynovich.data_storage.model.PersistentModel;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Optional;

public interface BaseDAO<T extends PersistentModel<I>, I extends Serializable> {

	T save(@NonNull T t);

	Optional<T> findById(@NonNull I id);

	void delete(@NonNull T t);
}
