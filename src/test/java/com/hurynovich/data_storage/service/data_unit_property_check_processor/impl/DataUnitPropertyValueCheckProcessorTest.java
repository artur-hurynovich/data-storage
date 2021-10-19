package com.hurynovich.data_storage.service.data_unit_property_check_processor.impl;

import com.hurynovich.data_storage.model.DataUnitPropertyType;
import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.service.data_unit_property_check_processor.DataUnitPropertyValueCheckProcessor;
import com.hurynovich.data_storage.service.data_unit_property_check_processor.exception.DataUnitPropertyValueCheckProcessorException;
import com.hurynovich.data_storage.service.data_unit_property_value_checker.DataUnitPropertyValueTypeChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_INTEGER_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_FLOAT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_BOOLEAN_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_DATE_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TIME_PROPERTY_VALUE;

@ExtendWith(MockitoExtension.class)
class DataUnitPropertyValueCheckProcessorTest {

	@Mock
	private Map<DataUnitPropertyType, DataUnitPropertyValueTypeChecker> dataUnitPropertyTypeCheckerByType;

	@Mock
	private DataUnitPropertyValueTypeChecker checker;

	private DataUnitPropertyValueCheckProcessor processor;

	@BeforeEach
	public void initProcessor() {
		processor = new DataUnitPropertyValueCheckProcessorImpl(dataUnitPropertyTypeCheckerByType);
	}

	@Test
	void textTypeNullTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.TEXT);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final boolean checkResult = processor.processCheck(propertySchema, null);
		Assertions.assertTrue(checkResult);
	}

	private DataUnitPropertySchemaDTO buildPropertySchema(final DataUnitPropertyType type) {
		final DataUnitPropertySchemaDTO propertySchema = new DataUnitPropertySchemaDTO();

		propertySchema.setType(type);

		return propertySchema;
	}

	@Test
	void textTypeNotNullTrueTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.TEXT);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final Object value = DATA_UNIT_TEXT_PROPERTY_VALUE;
		Mockito.when(checker.check(value)).thenReturn(true);

		final boolean checkResult = processor.processCheck(propertySchema, value);
		Assertions.assertTrue(checkResult);
	}

	@Test
	void textTypeNotNullFalseTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.TEXT);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final Object value = DATA_UNIT_INTEGER_PROPERTY_VALUE;
		Mockito.when(checker.check(value)).thenReturn(false);

		final boolean checkResult = processor.processCheck(propertySchema, value);
		Assertions.assertFalse(checkResult);
	}

	@Test
	void integerTypeNullTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.INTEGER);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final boolean checkResult = processor.processCheck(propertySchema, null);
		Assertions.assertTrue(checkResult);
	}

	@Test
	void integerTypeNotNullTrueTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.INTEGER);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final Object value = DATA_UNIT_INTEGER_PROPERTY_VALUE;
		Mockito.when(checker.check(value)).thenReturn(true);

		final boolean checkResult = processor.processCheck(propertySchema, value);
		Assertions.assertTrue(checkResult);
	}

	@Test
	void integerTypeNotNullFalseTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.INTEGER);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final Object value = DATA_UNIT_FLOAT_PROPERTY_VALUE;
		Mockito.when(checker.check(value)).thenReturn(false);

		final boolean checkResult = processor.processCheck(propertySchema, value);
		Assertions.assertFalse(checkResult);
	}

	@Test
	void floatTypeNullTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.FLOAT);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final boolean checkResult = processor.processCheck(propertySchema, null);
		Assertions.assertTrue(checkResult);
	}

	@Test
	void floatTypeNotNullTrueTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.FLOAT);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final Object value = DATA_UNIT_FLOAT_PROPERTY_VALUE;
		Mockito.when(checker.check(value)).thenReturn(true);

		final boolean checkResult = processor.processCheck(propertySchema, value);
		Assertions.assertTrue(checkResult);
	}

	@Test
	void floatTypeNotNullFalseTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.FLOAT);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final Object value = DATA_UNIT_BOOLEAN_PROPERTY_VALUE;
		Mockito.when(checker.check(value)).thenReturn(false);

		final boolean checkResult = processor.processCheck(propertySchema, value);
		Assertions.assertFalse(checkResult);
	}

	@Test
	void booleanTypeNullTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.BOOLEAN);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final boolean checkResult = processor.processCheck(propertySchema, null);
		Assertions.assertTrue(checkResult);
	}

	@Test
	void booleanTypeNotNullTrueTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.BOOLEAN);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final Object value = DATA_UNIT_BOOLEAN_PROPERTY_VALUE;
		Mockito.when(checker.check(value)).thenReturn(true);

		final boolean checkResult = processor.processCheck(propertySchema, value);
		Assertions.assertTrue(checkResult);
	}

	@Test
	void booleanTypeNotNullFalseTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.BOOLEAN);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final Object value = DATA_UNIT_DATE_PROPERTY_VALUE;
		Mockito.when(checker.check(value)).thenReturn(false);

		final boolean checkResult = processor.processCheck(propertySchema, value);
		Assertions.assertFalse(checkResult);
	}

	@Test
	void dateTypeNullTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.DATE);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final boolean checkResult = processor.processCheck(propertySchema, null);
		Assertions.assertTrue(checkResult);
	}

	@Test
	void dateTypeNotNullTrueTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.DATE);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final Object value = DATA_UNIT_DATE_PROPERTY_VALUE;
		Mockito.when(checker.check(value)).thenReturn(true);

		final boolean checkResult = processor.processCheck(propertySchema, value);
		Assertions.assertTrue(checkResult);
	}

	@Test
	void dateTypeNotNullFalseTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.DATE);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final Object value = DATA_UNIT_TIME_PROPERTY_VALUE;
		Mockito.when(checker.check(value)).thenReturn(false);

		final boolean checkResult = processor.processCheck(propertySchema, value);
		Assertions.assertFalse(checkResult);
	}

	@Test
	void timeTypeNullTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.TIME);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final boolean checkResult = processor.processCheck(propertySchema, null);
		Assertions.assertTrue(checkResult);
	}

	@Test
	void timeTypeNotNullTrueTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.TIME);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final Object value = DATA_UNIT_TIME_PROPERTY_VALUE;
		Mockito.when(checker.check(value)).thenReturn(true);

		final boolean checkResult = processor.processCheck(propertySchema, value);
		Assertions.assertTrue(checkResult);
	}

	@Test
	void timeTypeNotNullFalseTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.TIME);
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(propertySchema.getType())).thenReturn(checker);

		final Object value = DATA_UNIT_TEXT_PROPERTY_VALUE;
		Mockito.when(checker.check(value)).thenReturn(false);

		final boolean checkResult = processor.processCheck(propertySchema, value);
		Assertions.assertFalse(checkResult);
	}

	@Test
	void checkerNotFoundExceptionTest() {
		final DataUnitPropertySchemaDTO propertySchema = buildPropertySchema(DataUnitPropertyType.TIME);
		final DataUnitPropertyType type = propertySchema.getType();
		Mockito.when(dataUnitPropertyTypeCheckerByType.get(type)).thenReturn(null);

		Assertions.assertThrows(DataUnitPropertyValueCheckProcessorException.class,
				() -> processor.processCheck(propertySchema, null), "No dataUnitPropertyValueTypeChecker for" +
						"DataUnitPropertyType=" + type);
	}

}
