package com.hurynovich.data_storage.test_object_generator.impl;

import com.hurynovich.data_storage.model.DataUnitPropertyType;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;

public abstract class AbstractTestDataUnitPropertySchemaGenerator<T> implements TestObjectGenerator<T> {

	protected final Long id1 = 1000L;
	protected final String name1 = "Property Schema Name 1";
	protected final DataUnitPropertyType type1 = DataUnitPropertyType.INTEGER;

	protected final Long id2 = 2000L;
	protected final String name2 = "Property Schema Name 2";
	protected final DataUnitPropertyType type2 = DataUnitPropertyType.FLOAT;

	protected final Long id3 = 3000L;
	protected final String name3 = "Property Schema Name 3";
	protected final DataUnitPropertyType type3 = DataUnitPropertyType.BOOLEAN;

	protected final Long id4 = 4000L;
	protected final String name4 = "Property Schema Name 4";
	protected final DataUnitPropertyType type4 = DataUnitPropertyType.LABEL;

}
