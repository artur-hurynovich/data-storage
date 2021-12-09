package com.hurynovich.data_storage.service.dto_service;

import com.hurynovich.data_storage.model.ServiceModel;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Optional;

public interface BaseService<T extends ServiceModel<I>, I extends Serializable> {

	T save(@NonNull T t);

	Optional<T> findById(@NonNull I id);

	void deleteById(@NonNull I id);
}
