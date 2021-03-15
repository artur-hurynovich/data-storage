package com.hurynovich.data_storage.converter;

import java.util.List;

public interface Converter<T, U> {

	U convert(T source);

	List<U> convertAll(Iterable<T> sources);

}
