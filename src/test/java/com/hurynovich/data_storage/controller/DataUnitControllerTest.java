package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.model.GenericValidatedResponse;
import com.hurynovich.data_storage.model.dto.DataUnitDTO;
import com.hurynovich.data_storage.service.dto_service.DTOService;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDTOGenerator;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.INCORRECT_STRING_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitControllerTest {

	@Mock
	private DTOValidator<DataUnitDTO> validator;

	@Mock
	private DTOService<DataUnitDTO, String> service;

	private DataUnitController controller;

	private final TestObjectGenerator<DataUnitDTO> dtoGenerator =
			new TestDataUnitDTOGenerator();

	@BeforeEach
	public void initController() {
		controller = new DataUnitController(validator, service);
	}

	@Test
	void postValidDataUnitTest() {
		final DataUnitDTO dataUnitDTO = dtoGenerator.generateSingleObject();
		dataUnitDTO.setId(null);

		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(validator.validate(dataUnitDTO)).thenReturn(validationResult);
		Mockito.when(service.save(dataUnitDTO)).thenReturn(dataUnitDTO);

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.postDataUnit(dataUnitDTO);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertEquals(dataUnitDTO, responseBody.getBody());
	}

	private void checkValidationResultsEquality(final ValidationResult expected, final ValidationResult actual) {
		Assertions.assertEquals(expected.getType(), actual.getType());
		Assertions.assertEquals(expected.getErrors(), actual.getErrors());
	}

	@Test
	void postValidDataUnitNotNullIdTest() {
		final DataUnitDTO dataUnitDTO = dtoGenerator.generateSingleObject();
		Mockito.when(validator.validate(dataUnitDTO)).thenReturn(new ValidationResult());

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.postDataUnit(dataUnitDTO);
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
		final DataUnitDTO dataUnitDTO = dtoGenerator.generateSingleObject();
		dataUnitDTO.setId(null);
		dataUnitDTO.setSchemaId(null);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit.schemaId' can't be null");
		Mockito.when(validator.validate(dataUnitDTO)).thenReturn(validationResult);

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.postDataUnit(dataUnitDTO);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void getDataUnitByIdTest() {
		final DataUnitDTO dataUnitDTO = dtoGenerator.generateSingleObject();
		final String id = dataUnitDTO.getId();
		Mockito.when(service.findById(id)).thenReturn(Optional.of(dataUnitDTO));

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.getDataUnitById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(new ValidationResult(), responseBody.getValidationResult());

		Assertions.assertEquals(dataUnitDTO, responseBody.getBody());
	}

	@Test
	void getDataUnitByIdNotFoundTest() {
		Mockito.when(service.findById(INCORRECT_STRING_ID)).thenReturn(Optional.empty());

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.getDataUnitById(INCORRECT_STRING_ID);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit' with id = " + INCORRECT_STRING_ID + " not found");

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void getDataUnitsTest() {
		final List<DataUnitDTO> dataUnitDTOs = dtoGenerator.generateMultipleObjects();
		Mockito.when(service.findAll()).thenReturn(dataUnitDTOs);

		final ResponseEntity<GenericValidatedResponse<List<DataUnitDTO>>> response = controller.getDataUnits();
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<List<DataUnitDTO>> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(new ValidationResult(), responseBody.getValidationResult());

		Assertions.assertTrue(Objects.deepEquals(dataUnitDTOs, responseBody.getBody()));
	}

	@Test
	void getDataUnitEmptyTest() {
		Mockito.when(service.findAll()).thenReturn(new ArrayList<>());

		final ResponseEntity<GenericValidatedResponse<List<DataUnitDTO>>> response = controller.getDataUnits();
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<List<DataUnitDTO>> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(new ValidationResult(), responseBody.getValidationResult());

		final List<DataUnitDTO> dataUnitDTOs = responseBody.getBody();
		Assertions.assertNotNull(dataUnitDTOs);
		Assertions.assertTrue(dataUnitDTOs.isEmpty());
	}

	@Test
	void putValidDataUnitTest() {
		final DataUnitDTO dataUnitDTO = dtoGenerator.generateSingleObject();
		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(validator.validate(dataUnitDTO)).thenReturn(validationResult);
		Mockito.when(service.save(dataUnitDTO)).thenReturn(dataUnitDTO);

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.
				putDataUnit(dataUnitDTO.getId(), dataUnitDTO);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertEquals(dataUnitDTO, responseBody.getBody());
	}

	@Test
	void putValidDataUnitNullIdTest() {
		final DataUnitDTO dataUnitDTO = dtoGenerator.generateSingleObject();
		final String id = dataUnitDTO.getId();
		dataUnitDTO.setId(null);
		Mockito.when(validator.validate(dataUnitDTO)).thenReturn(new ValidationResult());

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.
				putDataUnit(id, dataUnitDTO);
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
		final DataUnitDTO dataUnitDTO = dtoGenerator.generateSingleObject();
		Mockito.when(validator.validate(dataUnitDTO)).thenReturn(new ValidationResult());

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.
				putDataUnit(INCORRECT_STRING_ID, dataUnitDTO);
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
		final DataUnitDTO dataUnitDTO = dtoGenerator.generateSingleObject();
		dataUnitDTO.setProperties(null);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit.properties' can't be null or empty");
		Mockito.when(validator.validate(dataUnitDTO)).thenReturn(validationResult);

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.
				putDataUnit(dataUnitDTO.getId(), dataUnitDTO);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void deleteDataUnitByIdTest() {
		final DataUnitDTO dataUnitDTO = dtoGenerator.generateSingleObject();
		final String id = dataUnitDTO.getId();

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.deleteDataUnitById(id);
		Mockito.verify(service).deleteById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(new ValidationResult(), responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

}
