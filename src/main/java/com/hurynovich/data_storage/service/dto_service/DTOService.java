package com.hurynovich.data_storage.service.dto_service;

import com.hurynovich.data_storage.model.dto.AbstractDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface DTOService<T extends AbstractDTO<I>, I extends Serializable> {

	T save(T t);

	Optional<T> findById(I id);

	List<T> findAll();

	void deleteById(I id);

}
