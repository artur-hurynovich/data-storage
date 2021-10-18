package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.model.GenericValidatedResponse;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.dto_service.DTOService;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaControllerTest {

	private final Long incorrectId = 1L;

	@Mock
	private DTOValidator<DataUnitSchemaDTO> validator;

	@Mock
	private DTOService<DataUnitSchemaDTO, Long> service;

	private DataUnitSchemaController controller;

	private final TestObjectGenerator<DataUnitSchemaDTO> dtoGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@BeforeEach
	public void initController() {
		controller = new DataUnitSchemaController(validator, service);
	}

	@Test
	void postValidSchemaTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		schemaDTO.setId(null);

		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(validator.validate(schemaDTO)).thenReturn(validationResult);
		Mockito.when(service.save(schemaDTO)).thenReturn(schemaDTO);

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.postSchema(schemaDTO);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertEquals(schemaDTO, responseBody.getBody());
	}

	private void checkValidationResultsEquality(final ValidationResult expected, final ValidationResult actual) {
		Assertions.assertEquals(expected.getType(), actual.getType());
		Assertions.assertEquals(expected.getErrors(), actual.getErrors());
	}

	@Test
	void postValidSchemaNotNullIdTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema.id' should be null");

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.postSchema(schemaDTO);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void postNotValidSchemaTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		schemaDTO.setId(null);
		schemaDTO.setName(null);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema.name' can't be null, empty or blank");
		Mockito.when(validator.validate(schemaDTO)).thenReturn(validationResult);

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.postSchema(schemaDTO);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void getSchemaByIdTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final Long id = schemaDTO.getId();
		Mockito.when(service.findById(id)).thenReturn(Optional.of(schemaDTO));

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.getSchemaById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(new ValidationResult(), responseBody.getValidationResult());

		Assertions.assertEquals(schemaDTO, responseBody.getBody());
	}

	@Test
	void getSchemaByIdNotFoundTest() {
		Mockito.when(service.findById(incorrectId)).thenReturn(Optional.empty());

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.getSchemaById(incorrectId);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema' with id = " + incorrectId + " not found");

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void getSchemasTest() {
		final List<DataUnitSchemaDTO> schemaDTOs = dtoGenerator.generateMultipleObjects();
		Mockito.when(service.findAll()).thenReturn(schemaDTOs);

		final ResponseEntity<GenericValidatedResponse<List<DataUnitSchemaDTO>>> response = controller.getSchemas();
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<List<DataUnitSchemaDTO>> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(new ValidationResult(), responseBody.getValidationResult());

		Assertions.assertTrue(Objects.deepEquals(schemaDTOs, responseBody.getBody()));
	}

	@Test
	void getSchemasEmptyTest() {
		Mockito.when(service.findAll()).thenReturn(new ArrayList<>());

		final ResponseEntity<GenericValidatedResponse<List<DataUnitSchemaDTO>>> response = controller.getSchemas();
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<List<DataUnitSchemaDTO>> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(new ValidationResult(), responseBody.getValidationResult());

		final List<DataUnitSchemaDTO> schemaDTOs = responseBody.getBody();
		Assertions.assertNotNull(schemaDTOs);
		Assertions.assertTrue(schemaDTOs.isEmpty());
	}

	@Test
	void putValidSchemaTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(validator.validate(schemaDTO)).thenReturn(validationResult);
		Mockito.when(service.save(schemaDTO)).thenReturn(schemaDTO);

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.
				putSchema(schemaDTO.getId(), schemaDTO);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertEquals(schemaDTO, responseBody.getBody());
	}

	@Test
	void putValidSchemaNullIdTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final Long id = schemaDTO.getId();
		schemaDTO.setId(null);

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.
				putSchema(id, schemaDTO);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema.id' should be equal to path variable 'id'");
		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void putValidSchemaIncorrectIdTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.
				putSchema(incorrectId, schemaDTO);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema.id' should be equal to path variable 'id'");
		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void putNotValidSchemaTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		schemaDTO.setPropertySchemas(new ArrayList<>());

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema.propertySchemas' can't be null or empty");
		Mockito.when(validator.validate(schemaDTO)).thenReturn(validationResult);

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.
				putSchema(schemaDTO.getId(), schemaDTO);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void deleteSchemaByIdTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final Long id = schemaDTO.getId();

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.deleteSchemaById(id);
		Mockito.verify(service).deleteById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(new ValidationResult(), responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

}
