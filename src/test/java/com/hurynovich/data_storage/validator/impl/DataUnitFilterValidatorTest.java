package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.filter.impl.DataUnitPropertyCriteriaConfig;
import com.hurynovich.data_storage.filter.model.CriteriaComparison;
import com.hurynovich.data_storage.filter.model.DataUnitFilter;
import com.hurynovich.data_storage.filter.model.DataUnitPropertyCriteria;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.data_unit_property_check_processor.DataUnitPropertyValueCheckProcessor;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaService;
import com.hurynovich.data_storage.test_object_generator.TestIdentifiedObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import com.hurynovich.data_storage.utils.TestReflectionUtils;
import com.hurynovich.data_storage.validator.Validator;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_BOOLEAN_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_DATE_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_DATE_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_FLOAT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_INTEGER_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TIME_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TIME_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.INCORRECT_LONG_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitFilterValidatorTest {

	private final TestIdentifiedObjectGenerator<DataUnitSchemaDTO> schemaGenerator = new TestDataUnitSchemaDTOGenerator();

	@Mock
	private DataUnitSchemaService schemaService;

	private final Map<DataUnitPropertyType, Set<CriteriaComparison>> criteriaComparisonsByPropertyType =
			new DataUnitPropertyCriteriaConfig().criteriaComparisonsByPropertyType();

	@Mock
	private DataUnitPropertyValueCheckProcessor valueCheckProcessor;

	private Validator<DataUnitFilter> validator;

	@BeforeEach
	public void initValidator() {
		validator = new DataUnitFilterValidator(schemaService, criteriaComparisonsByPropertyType,
				valueCheckProcessor);
	}

	@Test
	void validateEmptyCriteriaTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		final Long schemaId = schema.getId();
		final DataUnitFilter filter = new DataUnitFilter(schemaId, new ArrayList<>());
		Mockito.when(schemaService.findById(schemaId)).thenReturn(Optional.of(schema));

		final ValidationResult validationResult = validator.validate(filter);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.SUCCESS, validationResult.getType());
		Assertions.assertTrue(validationResult.getErrors().isEmpty());
	}

	@Test
	void validateNotEmptyCriteriaTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		final List<DataUnitPropertyCriteria> criteria = buildCriteria();
		final Long schemaId = schema.getId();
		final DataUnitFilter filter = new DataUnitFilter(schemaId, criteria);
		Mockito.when(schemaService.findById(schemaId)).thenReturn(Optional.of(schema));
		Mockito.when(valueCheckProcessor.processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenReturn(true);

		final ValidationResult validationResult = validator.validate(filter);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.SUCCESS, validationResult.getType());
		Assertions.assertTrue(validationResult.getErrors().isEmpty());
	}

	private List<DataUnitPropertyCriteria> buildCriteria() {
		return List.of(
				new DataUnitPropertyCriteria(DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID,
						CriteriaComparison.CONTAINS, DATA_UNIT_TEXT_PROPERTY_VALUE),
				new DataUnitPropertyCriteria(DATA_UNIT_INTEGER_PROPERTY_SCHEMA_ID,
						CriteriaComparison.GT, DATA_UNIT_INTEGER_PROPERTY_VALUE),
				new DataUnitPropertyCriteria(DATA_UNIT_FLOAT_PROPERTY_SCHEMA_ID,
						CriteriaComparison.LE, DATA_UNIT_FLOAT_PROPERTY_VALUE),
				new DataUnitPropertyCriteria(DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_ID,
						CriteriaComparison.EQ, DATA_UNIT_BOOLEAN_PROPERTY_VALUE),
				new DataUnitPropertyCriteria(DATA_UNIT_DATE_PROPERTY_SCHEMA_ID,
						CriteriaComparison.LT, DATA_UNIT_DATE_PROPERTY_VALUE),
				new DataUnitPropertyCriteria(DATA_UNIT_TIME_PROPERTY_SCHEMA_ID,
						CriteriaComparison.GE, DATA_UNIT_TIME_PROPERTY_VALUE));
	}

	@Test
	void validateSchemaIsNullTest() {
		final List<DataUnitPropertyCriteria> criteria = buildCriteria();
		final DataUnitFilter filter = new DataUnitFilter(INCORRECT_LONG_ID, criteria);
		Mockito.when(schemaService.findById(INCORRECT_LONG_ID)).thenReturn(Optional.empty());

		final ValidationResult validationResult = validator.validate(filter);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema' with id = '" + INCORRECT_LONG_ID + "' not found",
				errors.iterator().next());
	}

	@Test
	void validatePropertySchemaNotFoundTest() {
		final List<DataUnitPropertyCriteria> criteria = buildCriteria();
		TestReflectionUtils.setField(criteria.iterator().next(), "propertySchemaId", INCORRECT_LONG_ID);

		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		final Long schemaId = schema.getId();
		final DataUnitFilter filter = new DataUnitFilter(schemaId, criteria);
		Mockito.when(schemaService.findById(schemaId)).thenReturn(Optional.of(schema));
		Mockito.when(valueCheckProcessor.
						processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenReturn(true);

		final ValidationResult validationResult = validator.validate(filter);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitPropertySchema' with id = '" + INCORRECT_LONG_ID + "' not found",
				errors.iterator().next());
	}

	@Test
	void validatePropertySchemaIdDuplicateTest() {
		final List<DataUnitPropertyCriteria> criteria = buildCriteria();
		final Long propertySchemaId = criteria.get(2).getPropertySchemaId();
		TestReflectionUtils.setField(criteria.get(1), "propertySchemaId", propertySchemaId);

		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		final Long schemaId = schema.getId();
		final DataUnitFilter filter = new DataUnitFilter(schemaId, criteria);
		Mockito.when(schemaService.findById(schemaId)).thenReturn(Optional.of(schema));
		Mockito.when(valueCheckProcessor.
						processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenReturn(true);

		final ValidationResult validationResult = validator.validate(filter);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("Found duplicate '" + propertySchemaId + "' for 'filter.criteria.propertySchemaId'",
				errors.iterator().next());
	}

	@Test
	void validateIncorrectCriteriaComparisonTest() {
		final List<DataUnitPropertyCriteria> criteria = buildCriteria();
		final DataUnitPropertyCriteria incorrectCriteria = criteria.get(0);
		TestReflectionUtils.setField(incorrectCriteria, "comparison", CriteriaComparison.GT);

		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		final Long schemaId = schema.getId();
		final DataUnitFilter filter = new DataUnitFilter(schemaId, criteria);
		Mockito.when(schemaService.findById(schemaId)).thenReturn(Optional.of(schema));
		Mockito.when(valueCheckProcessor.
						processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenReturn(true);

		final ValidationResult validationResult = validator.validate(filter);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'filter.criteria.comparison' '" + CriteriaComparison.GT +
						"' is incorrect for dataUnitProperty with schemaId = '" + incorrectCriteria.getPropertySchemaId() + "'",
				errors.iterator().next());
	}

	@Test
	void validateIncorrectComparisonPatternTest() {
		final List<DataUnitPropertyCriteria> criteria = buildCriteria();
		final DataUnitPropertyCriteria incorrectCriteria = criteria.get(0);
		final Object incorrectComparisonPattern = 100;
		TestReflectionUtils.setField(incorrectCriteria, "comparisonPattern", incorrectComparisonPattern);

		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		final Long schemaId = schema.getId();
		final DataUnitFilter filter = new DataUnitFilter(schemaId, criteria);
		Mockito.when(schemaService.findById(schemaId)).thenReturn(Optional.of(schema));
		Mockito.when(valueCheckProcessor.
						processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenAnswer(invocation -> {
					final DataUnitPropertySchemaDTO propertySchema = invocation.getArgument(0);

					return !propertySchema.getId().equals(incorrectCriteria.getPropertySchemaId());
				});

		final ValidationResult validationResult = validator.validate(filter);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'filter.criteria.comparisonPattern' '" + incorrectComparisonPattern +
						"' is incorrect for dataUnitProperty with schemaId = '" + incorrectCriteria.getPropertySchemaId() + "'",
				errors.iterator().next());
	}
}
