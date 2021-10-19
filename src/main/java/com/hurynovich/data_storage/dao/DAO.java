package com.hurynovich.data_storage.dao;

import com.hurynovich.data_storage.model.Identified;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface DAO<T extends Identified<I>, I extends Serializable> {

	T save(@NonNull T t);

	Optional<T> findById(@NonNull I id);

	List<T> findAll();

	void deleteById(@NonNull I id);

}
