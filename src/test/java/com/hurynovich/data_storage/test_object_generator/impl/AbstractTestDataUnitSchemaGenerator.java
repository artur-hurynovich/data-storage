package com.hurynovich.data_storage.test_object_generator.impl;

import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;

public abstract class AbstractTestDataUnitSchemaGenerator<T> implements TestObjectGenerator<T> {

	protected final Long id1 = 1111L;
	protected final String name1 = "Schema Name 1";

	protected final Long id2 = 2222L;
	protected final String name2 = "Schema Name 2";

	protected final Long id3 = 3333L;
	protected final String name3 = "Schema Name 3";

	protected final Long id4 = 4444L;
	protected final String name4 = "Schema Name 4";

}
