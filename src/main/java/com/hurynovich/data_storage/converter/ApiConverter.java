package com.hurynovich.data_storage.converter;

import com.hurynovich.data_storage.model.ApiModel;
import com.hurynovich.data_storage.model.ServiceModel;
import org.springframework.lang.Nullable;

public interface ApiConverter<T extends ApiModel<?>, U extends ServiceModel<?>> {

	U convert(@Nullable T source, @Nullable String... ignoreProperties);

	T convert(@Nullable U source, @Nullable String... ignoreProperties);
}
