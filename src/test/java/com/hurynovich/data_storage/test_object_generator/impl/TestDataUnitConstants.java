package com.hurynovich.data_storage.test_object_generator.impl;

import com.hurynovich.data_storage.model.DataUnitPropertyType;

import java.time.LocalDate;
import java.time.LocalTime;

public class TestDataUnitConstants {

	public static final Long INCORRECT_LONG_ID = 1L;
	public static final String INCORRECT_STRING_ID = "1L";

	public static final Long DATA_UNIT_SCHEMA_ID_1 = 1111L;
	public static final String DATA_UNIT_SCHEMA_NAME_1 = "Schema Name 1";

	public static final Long DATA_UNIT_SCHEMA_ID_2 = 2222L;
	public static final String DATA_UNIT_SCHEMA_NAME_2 = "Schema Name 2";

	public static final Long DATA_UNIT_SCHEMA_ID_3 = 3333L;
	public static final String DATA_UNIT_SCHEMA_NAME_3 = "Schema Name 3";

	public static final Long DATA_UNIT_SCHEMA_ID_4 = 4444L;
	public static final String DATA_UNIT_SCHEMA_NAME_4 = "Schema Name 4";

	public static final Long DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID = 1000L;
	public static final String DATA_UNIT_TEXT_PROPERTY_SCHEMA_NAME = "Text Property Schema";
	public static final DataUnitPropertyType DATA_UNIT_TEXT_PROPERTY_SCHEMA_TYPE = DataUnitPropertyType.TEXT;

	public static final Long DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID = 2000L;
	public static final String DATA_UNIT_INTEGER_PROPERTY_SCHEMA_NAME = "Integer Property Schema";
	public static final DataUnitPropertyType DATA_UNIT_INTEGER_PROPERTY_SCHEMA_TYPE = DataUnitPropertyType.INTEGER;

	public static final Long DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID = 3000L;
	public static final String DATA_UNIT_FLOAT_PROPERTY_SCHEMA_NAME = "Float Property Schema";
	public static final DataUnitPropertyType DATA_UNIT_FLOAT_PROPERTY_SCHEMA_TYPE = DataUnitPropertyType.FLOAT;

	public static final Long DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID = 4000L;
	public static final String DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_NAME = "Boolean Property Schema";
	public static final DataUnitPropertyType DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_TYPE = DataUnitPropertyType.BOOLEAN;

	public static final Long DATA_UNIT_DATE_PROPERTY_SCHEMA_ID = 5000L;
	public static final String DATA_UNIT_DATE_PROPERTY_SCHEMA_NAME = "Date Property Schema";
	public static final DataUnitPropertyType DATA_UNIT_DATE_PROPERTY_SCHEMA_TYPE = DataUnitPropertyType.DATE;

	public static final Long DATA_UNIT_TIME_PROPERTY_SCHEMA_ID = 6000L;
	public static final String DATA_UNIT_TIME_PROPERTY_SCHEMA_NAME = "Time Property Schema";
	public static final DataUnitPropertyType DATA_UNIT_TIME_PROPERTY_SCHEMA_TYPE = DataUnitPropertyType.TIME;

	public static final String DATA_UNIT_ID_1 = "1111";

	public static final String DATA_UNIT_ID_2 = "2222";

	public static final String DATA_UNIT_ID_3 = "3333";

	public static final String DATA_UNIT_ID_4 = "4444";

	public static final String DATA_UNIT_TEXT_PROPERTY_VALUE = "text";

	public static final int DATA_UNIT_INTEGER_PROPERTY_VALUE = 100;

	public static final double DATA_UNIT_FLOAT_PROPERTY_VALUE = 100.5d;

	public static final boolean DATA_UNIT_BOOLEAN_PROPERTY_VALUE = false;

	public static final LocalDate DATA_UNIT_DATE_PROPERTY_VALUE = LocalDate.of(2021, 10, 19);

	public static final LocalTime DATA_UNIT_TIME_PROPERTY_VALUE = LocalTime.of(17, 27);

}
