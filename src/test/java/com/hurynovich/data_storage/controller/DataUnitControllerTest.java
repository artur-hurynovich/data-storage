package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.controller.model.GenericValidatedResponse;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.service.dto_service.BaseService;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDTOGenerator;
import com.hurynovich.data_storage.utils.TestReflectionUtils;
import com.hurynovich.data_storage.validator.ValidationHelper;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.INCORRECT_STRING_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitControllerTest extends AbstractControllerTest {

	@Mock
	private Validator<DataUnitDTO> validator;

	@Mock
	private ValidationHelper helper;

	@Mock
	private BaseService<DataUnitDTO, String> service;

	private DataUnitController controller;

	private final TestObjectGenerator<DataUnitDTO> dataUnitGenerator =
			new TestDataUnitDTOGenerator();

	@BeforeEach
	public void initController() {
		controller = new DataUnitController(validator, helper, service);
	}

	@Test
	void postValidDataUnitTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		TestReflectionUtils.setField(dataUnit, "id", null);

		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(validator.validate(dataUnit)).thenReturn(validationResult);
		Mockito.when(service.save(dataUnit)).thenReturn(dataUnit);

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.postDataUnit(dataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertEquals(dataUnit, responseBody.getBody());
	}

	@Test
	void postValidDataUnitIdIsNotNullTest() {
		Mockito.doAnswer(invocationOnMock -> {
			final ValidationResult validationResult = invocationOnMock.getArgument(1);
			validationResult.setType(ValidationResultType.FAILURE);
			validationResult.addError("'dataUnit.id' should be null");

			return null;
		}).when(helper).applyIsNotNullError(Mockito.eq("dataUnit.id"), Mockito.any(ValidationResult.class));
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.postDataUnit(dataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit.id' should be null");
		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void postValidDataUnitSchemaIdIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		TestReflectionUtils.setField(dataUnit, "id", null);
		TestReflectionUtils.setField(dataUnit, "schemaId", null);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit.schemaId' can't be null");
		Mockito.when(validator.validate(dataUnit)).thenReturn(validationResult);

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.postDataUnit(dataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void postNotValidDataUnitTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		TestReflectionUtils.setField(dataUnit, "properties", new ArrayList<>());

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit.properties' can't be null or empty");
		Mockito.when(validator.validate(dataUnit)).thenReturn(validationResult);

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.
				putDataUnit(dataUnit.getId(), dataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void getDataUnitByIdTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		final String id = dataUnit.getId();
		Mockito.when(service.findById(id)).thenReturn(Optional.of(dataUnit));

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.getDataUnitById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(new ValidationResult(), responseBody.getValidationResult());

		Assertions.assertEquals(dataUnit, responseBody.getBody());
	}

	@Test
	void getDataUnitByIdNotFoundTest() {
		Mockito.when(service.findById(INCORRECT_STRING_ID)).thenReturn(Optional.empty());
		Mockito.doAnswer(invocationOnMock -> {
			final ValidationResult validationResult = invocationOnMock.getArgument(2);
			validationResult.setType(ValidationResultType.FAILURE);
			validationResult.addError("'dataUnit' with id = '" + INCORRECT_STRING_ID + "' not found");

			return null;
		}).when(helper).applyNotFoundByIdError(Mockito.eq("dataUnit"), Mockito.eq(INCORRECT_STRING_ID),
				Mockito.any(ValidationResult.class));

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.
				getDataUnitById(INCORRECT_STRING_ID);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit' with id = '" + INCORRECT_STRING_ID + "' not found");

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void putValidDataUnitTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(validator.validate(dataUnit)).thenReturn(validationResult);
		Mockito.when(service.save(dataUnit)).thenReturn(dataUnit);

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.
				putDataUnit(dataUnit.getId(), dataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertEquals(dataUnit, responseBody.getBody());
	}

	@Test
	void putValidDataUnitIdIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		final String id = dataUnit.getId();
		TestReflectionUtils.setField(dataUnit, "id", null);

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.
				putDataUnit(id, dataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit.id' should be equal to path variable 'id'");
		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void putValidDataUnitIncorrectIdTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.
				putDataUnit(INCORRECT_STRING_ID, dataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit.id' should be equal to path variable 'id'");
		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void putNotValidDataUnitTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		TestReflectionUtils.setField(dataUnit, "properties", null);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit.properties' can't be null or empty");
		Mockito.when(validator.validate(dataUnit)).thenReturn(validationResult);

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.
				putDataUnit(dataUnit.getId(), dataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void deleteDataUnitByIdTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		final String id = dataUnit.getId();

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.deleteDataUnitById(id);
		Mockito.verify(service).deleteById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(new ValidationResult(), responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

}
