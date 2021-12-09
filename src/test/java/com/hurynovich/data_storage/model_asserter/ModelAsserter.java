package com.hurynovich.data_storage.model_asserter;

import com.hurynovich.data_storage.model.PersistentModel;
import com.hurynovich.data_storage.model.ServiceModel;

public interface ModelAsserter<T extends ServiceModel<?>, U extends PersistentModel<?>> {

	void assertEquals(T expected, T actual, String... ignoreProperties);

	void assertEquals(T expected, U actual, String... ignoreProperties);

	void assertEquals(U expected, T actual, String... ignoreProperties);

	void assertEquals(U expected, U actual, String... ignoreProperties);
}
