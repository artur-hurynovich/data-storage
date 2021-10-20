package com.hurynovich.data_storage.converter;

import com.hurynovich.data_storage.model.Identified;
import com.hurynovich.data_storage.model.dto.AbstractDTO;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;

public interface DTOConverter<T extends AbstractDTO<? extends Serializable>, U extends Identified<? extends Serializable>> {

	U convert(@Nullable T source);

	T convert(@Nullable U source);

	List<T> convert(@Nullable Iterable<U> sources);

}
