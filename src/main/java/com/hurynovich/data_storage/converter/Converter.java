package com.hurynovich.data_storage.converter;

import org.springframework.lang.Nullable;

public interface Converter<T, U> {

	U convert(@Nullable T source, @Nullable String... ignoreProperties);

}
