package com.hurynovich.data_storage.converter;

import com.hurynovich.data_storage.model.AbstractDTO;
import com.hurynovich.data_storage.model.Identified;
import org.springframework.lang.Nullable;

import java.io.Serializable;

public interface DTOConverter<T extends AbstractDTO<I>, U extends Identified<I>, I extends Serializable> {

	U convert(@Nullable T source);

	T convert(@Nullable U source);

	T convert(@Nullable U source, String... ignoreProperties);

}
