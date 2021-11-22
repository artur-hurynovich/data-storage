package com.hurynovich.data_storage.test_objects_asserter;

public interface Asserter<T> {

	void assertEquals(T expected, T actual, String... ignoreProperties);

}
