package com.hurynovich.data_storage.it.api.initializer;

import com.hurynovich.data_storage.model.Identified;

public interface ITInitializer<T extends Identified<?>> {

	T init(T t);

	void clear();

}
