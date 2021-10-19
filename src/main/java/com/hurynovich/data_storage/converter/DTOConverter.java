package com.hurynovich.data_storage.converter;

import com.hurynovich.data_storage.model.Identified;
import com.hurynovich.data_storage.model.dto.AbstractDTO;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;

public interface DTOConverter<T extends AbstractDTO<? extends Serializable>, U extends Identified<? extends Serializable>> {

	U convertFromDTO(@Nullable T source);

	T convertToDTO(@Nullable U source);

	List<U> convertAllFromDTOs(@Nullable Iterable<T> sources);

	List<T> convertAllToDTOs(@Nullable Iterable<U> sources);

}
