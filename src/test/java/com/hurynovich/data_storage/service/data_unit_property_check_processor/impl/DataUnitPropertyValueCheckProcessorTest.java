package com.hurynovich.data_storage.service.data_unit_property_check_processor.impl;

import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModelImpl;
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

import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_BOOLEAN_PROPERTY_VALUE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_DATE_PROPERTY_VALUE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_FLOAT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_INTEGER_PROPERTY_VALUE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TEXT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TIME_PROPERTY_VALUE;

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
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.TEXT);

		Assertions.assertTrue(processor.processCheck(propertySchema, null));
	}

	private DataUnitPropertySchemaServiceModel buildPropertySchema(final DataUnitPropertyType type) {
		return new DataUnitPropertySchemaServiceModelImpl(null, null, type);
	}

	@Test
	void textTypeNotNullTrueTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.TEXT);

		Assertions.assertTrue(processor.processCheck(propertySchema, DATA_UNIT_TEXT_PROPERTY_VALUE));
	}

	@Test
	void textTypeNotNullFalseTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.TEXT);

		Assertions.assertFalse(processor.processCheck(propertySchema, DATA_UNIT_INTEGER_PROPERTY_VALUE));
	}

	@Test
	void integerTypeNullTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.INTEGER);

		Assertions.assertTrue(processor.processCheck(propertySchema, null));
	}

	@Test
	void integerTypeNotNullTrueTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.INTEGER);

		Assertions.assertTrue(processor.processCheck(propertySchema, DATA_UNIT_INTEGER_PROPERTY_VALUE));
	}

	@Test
	void integerTypeNotNullFalseTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.INTEGER);

		Assertions.assertFalse(processor.processCheck(propertySchema, DATA_UNIT_FLOAT_PROPERTY_VALUE));
	}

	@Test
	void floatTypeNullTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.FLOAT);

		Assertions.assertTrue(processor.processCheck(propertySchema, null));
	}

	@Test
	void floatTypeNotNullTrueTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.FLOAT);

		Assertions.assertTrue(processor.processCheck(propertySchema, DATA_UNIT_FLOAT_PROPERTY_VALUE));
	}

	@Test
	void floatTypeNotNullFalseTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.FLOAT);

		Assertions.assertFalse(processor.processCheck(propertySchema, DATA_UNIT_BOOLEAN_PROPERTY_VALUE));
	}

	@Test
	void booleanTypeNullTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.BOOLEAN);

		Assertions.assertTrue(processor.processCheck(propertySchema, null));
	}

	@Test
	void booleanTypeNotNullTrueTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.BOOLEAN);

		Assertions.assertTrue(processor.processCheck(propertySchema, DATA_UNIT_BOOLEAN_PROPERTY_VALUE));
	}

	@Test
	void booleanTypeNotNullFalseTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.BOOLEAN);

		Assertions.assertFalse(processor.processCheck(propertySchema, DATA_UNIT_DATE_PROPERTY_VALUE));
	}

	@Test
	void dateTypeNullTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.DATE);

		Assertions.assertTrue(processor.processCheck(propertySchema, null));
	}

	@Test
	void dateTypeNotNullTrueTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.DATE);

		Assertions.assertTrue(processor.processCheck(propertySchema, DATA_UNIT_DATE_PROPERTY_VALUE));
	}

	@Test
	void dateTypeNotNullFalseTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.DATE);

		Assertions.assertFalse(processor.processCheck(propertySchema, DATA_UNIT_TIME_PROPERTY_VALUE));
	}

	@Test
	void timeTypeNullTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.TIME);

		Assertions.assertTrue(processor.processCheck(propertySchema, null));
	}

	@Test
	void timeTypeNotNullTrueTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.TIME);

		Assertions.assertTrue(processor.processCheck(propertySchema, DATA_UNIT_TIME_PROPERTY_VALUE));
	}

	@Test
	void timeTypeNotNullFalseTest() {
		final DataUnitPropertySchemaServiceModel propertySchema = buildPropertySchema(DataUnitPropertyType.TIME);

		Assertions.assertFalse(processor.processCheck(propertySchema, DATA_UNIT_TEXT_PROPERTY_VALUE));
	}
}
