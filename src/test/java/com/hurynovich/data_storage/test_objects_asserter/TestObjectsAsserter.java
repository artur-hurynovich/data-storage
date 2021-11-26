package com.hurynovich.data_storage.test_objects_asserter;

public interface TestObjectsAsserter<T> {

	void assertEquals(T expected, T actual);

}
