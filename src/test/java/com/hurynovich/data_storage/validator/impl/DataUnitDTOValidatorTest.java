package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.dto.DataUnitDTO;
import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.data_unit_property_check_processor.DataUnitPropertyValueCheckProcessor;
import com.hurynovich.data_storage.service.dto_service.DTOService;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDTOGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import com.hurynovich.data_storage.validator.DTOValidator;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.INCORRECT_LONG_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitDTOValidatorTest {

	@Mock
	private DTOService<DataUnitSchemaDTO, Long> dataUnitSchemaService;

	@Mock
	private DataUnitPropertyValueCheckProcessor checkProcessor;

	private DTOValidator<DataUnitDTO> validator;

	private final TestObjectGenerator<DataUnitDTO> dataUnitGenerator =
			new TestDataUnitDTOGenerator();

	private final TestObjectGenerator<DataUnitSchemaDTO> dataUnitSchemaGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@BeforeEach
	public void initValidator() {
		validator = new DataUnitDTOValidator(dataUnitSchemaService, checkProcessor);
	}

	@Test
	void validateTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateSingleObject();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenReturn(true);

		final ValidationResult result = validator.validate(dataUnit);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.SUCCESS, result.getType());
		Assertions.assertTrue(result.getErrors().isEmpty());
	}

	@Test
	void validateDataUnitIsNullTest() {
		final ValidationResult result = validator.validate(null);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnit' can't be null"));
	}

	@Test
	void validateSchemaIdIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		dataUnit.setSchemaId(null);

		final ValidationResult result = validator.validate(dataUnit);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnit.schemaId' can't be null"));
	}

	@Test
	void validateSchemaEmptyTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		dataUnit.setSchemaId(INCORRECT_LONG_ID);
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.empty());

		final ValidationResult result = validator.validate(dataUnit);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema' with id=" + INCORRECT_LONG_ID + " not found"));
	}

	@Test
	void validatePropertiesIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		dataUnit.setProperties(null);

		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateSingleObject();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));

		final ValidationResult result = validator.validate(dataUnit);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnit.properties' can't be null or empty"));
	}

	@Test
	void validatePropertiesIsEmptyTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		dataUnit.setProperties(new ArrayList<>());

		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateSingleObject();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));

		final ValidationResult result = validator.validate(dataUnit);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnit.properties' can't be null or empty"));
	}

	@Test
	void validatePropertyIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		dataUnit.getProperties().set(0, null);

		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateSingleObject();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenReturn(true);

		final ValidationResult result = validator.validate(dataUnit);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnit.property' can't be null"));
	}

	@Test
	void validatePropertySchemaIdIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		dataUnit.getProperties().get(0).setSchemaId(null);

		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateSingleObject();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenReturn(true);

		final ValidationResult result = validator.validate(dataUnit);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnit.property.schemaId' can't be null"));
	}

	@Test
	void validatePropertySchemaIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateSingleObject();
		dataUnitSchema.setPropertySchemas(dataUnitSchema.getPropertySchemas().stream().
				filter(propertySchema -> !propertySchema.getId().equals(DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID)).
				collect(Collectors.toList()));
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenReturn(true);

		final ValidationResult result = validator.validate(dataUnit);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitPropertySchema' with id=" + DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID + " not found"));
	}

	@Test
	void validateIncorrectPropertyValueTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateSingleObject();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenAnswer(invocation -> {
					final boolean result;

					final DataUnitPropertySchemaDTO propertySchema = invocation.
							getArgument(0, DataUnitPropertySchemaDTO.class);
					if (propertySchema != null) {
						result = !propertySchema.getId().equals(DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID);
					} else {
						result = false;
					}

					return result;
				});

		final ValidationResult result = validator.validate(dataUnit);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnit.property.value' is incorrect for dataUnitProperty with id=" +
				DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID));
	}

}
