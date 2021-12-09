package com.hurynovich.data_storage.model;

import java.util.List;

public interface ModelGenerator<T extends Identified<?>> {

	T generate();

	T generateNullId();

	List<T> generateList();

	List<T> generateListNullId();
}
