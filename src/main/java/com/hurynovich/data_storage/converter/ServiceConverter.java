package com.hurynovich.data_storage.converter;

import com.hurynovich.data_storage.model.PersistentModel;
import com.hurynovich.data_storage.model.ServiceModel;
import org.springframework.lang.Nullable;

public interface ServiceConverter<T extends ServiceModel<?>, U extends PersistentModel<?>> {

	U convert(@Nullable T source, @Nullable String... ignoreProperties);

	T convert(@Nullable U source, @Nullable String... ignoreProperties);
}
