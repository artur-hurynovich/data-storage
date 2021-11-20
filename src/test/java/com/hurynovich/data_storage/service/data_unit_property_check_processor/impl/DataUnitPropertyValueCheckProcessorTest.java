package com.hurynovich.data_storage.service.data_unit_property_check_processor.impl;

import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import com.hurynovich.data_storage.service.data_unit_property_check_processor.DataUnitPropertyValueCheckProcessor;
import com.hurynovich.data_storage.service.data_unit_property_value_checker.DataUnitPropertyValueTypeChecker;
import com.hurynovich.data_storage.service.data_unit_property_value_checker.impl.DataUnitPropertyValueCheckerConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_BOOLEAN_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_DATE_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_FLOAT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_INTEGER_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TIME_PROPERTY_VALUE;

@ExtendWith(MockitoExtension.class)
class DataUnitPropertyValueCheckProcessorTest {

	private final Map<DataUnitPropertyType, DataUnitPropertyValueTypeChecker> checkersByType =
			new DataUnitPropertyValueCheckerConfig().dataUnitPropertyValueTypeCheckersByType();

	private DataUnitPropertyValueCheckProcessor processor;

	@BeforeEach
	public void initProcessor() {
		processor = new DataUnitPropertyValueCheckProcessorImpl(checkersByType);
	}

	@Test
	void textTypeNullTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.TEXT);

		Assertions.assertTrue(processor.processCheck(propertySchema, null));
	}

	private DataUnitPropertySchemaDTO buildPropertySchema(final DataUnitPropertyType type) {
		return new DataUnitPropertySchemaDTO(null, null, type);
	}

	@Test
	void textTypeNotNullTrueTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.TEXT);

		Assertions.assertTrue(processor.processCheck(propertySchema, DATA_UNIT_TEXT_PROPERTY_VALUE));
	}

	@Test
	void textTypeNotNullFalseTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.TEXT);

		Assertions.assertFalse(processor.processCheck(propertySchema, DATA_UNIT_INTEGER_PROPERTY_VALUE));
	}

	@Test
	void integerTypeNullTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.INTEGER);

		Assertions.assertTrue(processor.processCheck(propertySchema, null));
	}

	@Test
	void integerTypeNotNullTrueTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.INTEGER);

		Assertions.assertTrue(processor.processCheck(propertySchema, DATA_UNIT_INTEGER_PROPERTY_VALUE));
	}

	@Test
	void integerTypeNotNullFalseTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.INTEGER);

		Assertions.assertFalse(processor.processCheck(propertySchema, DATA_UNIT_FLOAT_PROPERTY_VALUE));
	}

	@Test
	void floatTypeNullTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.FLOAT);

		Assertions.assertTrue(processor.processCheck(propertySchema, null));
	}

	@Test
	void floatTypeNotNullTrueTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.FLOAT);

		Assertions.assertTrue(processor.processCheck(propertySchema, DATA_UNIT_FLOAT_PROPERTY_VALUE));
	}

	@Test
	void floatTypeNotNullFalseTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.FLOAT);

		Assertions.assertFalse(processor.processCheck(propertySchema, DATA_UNIT_BOOLEAN_PROPERTY_VALUE));
	}

	@Test
	void booleanTypeNullTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.BOOLEAN);

		Assertions.assertTrue(processor.processCheck(propertySchema, null));
	}

	@Test
	void booleanTypeNotNullTrueTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.BOOLEAN);

		Assertions.assertTrue(processor.processCheck(propertySchema, DATA_UNIT_BOOLEAN_PROPERTY_VALUE));
	}

	@Test
	void booleanTypeNotNullFalseTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.BOOLEAN);

		Assertions.assertFalse(processor.processCheck(propertySchema, DATA_UNIT_DATE_PROPERTY_VALUE));
	}

	@Test
	void dateTypeNullTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.DATE);

		Assertions.assertTrue(processor.processCheck(propertySchema, null));
	}

	@Test
	void dateTypeNotNullTrueTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.DATE);

		Assertions.assertTrue(processor.processCheck(propertySchema, DATA_UNIT_DATE_PROPERTY_VALUE));
	}

	@Test
	void dateTypeNotNullFalseTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.DATE);

		Assertions.assertFalse(processor.processCheck(propertySchema, DATA_UNIT_TIME_PROPERTY_VALUE));
	}

	@Test
	void timeTypeNullTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.TIME);

		Assertions.assertTrue(processor.processCheck(propertySchema, null));
	}

	@Test
	void timeTypeNotNullTrueTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.TIME);

		Assertions.assertTrue(processor.processCheck(propertySchema, DATA_UNIT_TIME_PROPERTY_VALUE));
	}

	@Test
	void timeTypeNotNullFalseTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.TIME);

		Assertions.assertFalse(processor.processCheck(propertySchema, DATA_UNIT_TEXT_PROPERTY_VALUE));
	}

}
