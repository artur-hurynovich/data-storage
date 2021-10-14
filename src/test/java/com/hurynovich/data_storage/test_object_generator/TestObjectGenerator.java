package com.hurynovich.data_storage.test_object_generator;

import java.util.List;

public interface TestObjectGenerator<T> {

	T generateSingleObject();

	List<T> generateMultipleObjects();

}
