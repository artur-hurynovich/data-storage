package com.hurynovich.data_storage.test_object_generator;

import com.hurynovich.data_storage.model.Identified;

import java.util.List;

public interface TestIdentifiedObjectGenerator<T extends Identified<?>> {

	T generateObject();

	T generateObjectNullId();

	List<T> generateObjects();

}
