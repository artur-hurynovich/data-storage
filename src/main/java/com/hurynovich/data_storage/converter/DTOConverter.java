package com.hurynovich.data_storage.converter;

import com.hurynovich.data_storage.model.Identified;
import com.hurynovich.data_storage.model.dto.AbstractDTO;

import java.io.Serializable;
import java.util.List;

public interface DTOConverter<T extends AbstractDTO<? extends Serializable>, U extends Identified<? extends Serializable>> {

	U convertFromDTO(T source);

	T convertToDTO(U source);

	List<U> convertAllFromDTOs(Iterable<T> sources);

	List<T> convertAllToDTOs(Iterable<U> sources);

}
