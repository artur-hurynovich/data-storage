package com.hurynovich.data_storage.test_objects_asserter;

import com.hurynovich.data_storage.model.AbstractDTO;
import com.hurynovich.data_storage.model.Identified;

public interface TestIdentifiedObjectsAsserter<T extends AbstractDTO<?>, U extends Identified<?>> {

	void assertEquals(T expected, T actual, String... ignoreProperties);

	void assertEquals(T expected, U actual, String... ignoreProperties);

	void assertEquals(U expected, U actual, String... ignoreProperties);

	void assertEquals(U expected, T actual, String... ignoreProperties);

}
