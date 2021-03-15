package com.hurynovich.data_storage.converter;

import com.hurynovich.data_storage.model.Identified;

import java.io.Serializable;

public interface PersistenceConverter<T extends Identified<I>, U extends Identified<I>, I extends Serializable>
		extends Converter<T, U> {

}
