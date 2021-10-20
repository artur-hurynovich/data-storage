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

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.INCORRECT_LONG_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaControllerTest extends AbstractControllerTest {

	@Mock
	private DTOValidator<DataUnitSchemaDTO> validator;

	@Mock
	private DTOService<DataUnitSchemaDTO, Long> service;

	private DataUnitSchemaController controller;

	private final TestObjectGenerator<DataUnitSchemaDTO> schemaGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@BeforeEach
	public void initController() {
		controller = new DataUnitSchemaController(validator, service);
	}

	@Test
	void postValidSchemaTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		schema.setId(null);

		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(validator.validate(schema)).thenReturn(validationResult);
		Mockito.when(service.save(schema)).thenReturn(schema);

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.postSchema(schema);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertEquals(schema, responseBody.getBody());
	}

	@Test
	void postValidSchemaIdIsNotNullTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		Mockito.when(validator.validate(schema)).thenReturn(new ValidationResult());

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.postSchema(schema);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema.id' should be null");
		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void postValidSchemaNameIsNullTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		schema.setId(null);
		schema.setName(null);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema.name' can't be null, empty or blank");
		Mockito.when(validator.validate(schema)).thenReturn(validationResult);

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.postSchema(schema);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void postNotValidSchemaTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		schema.setId(null);
		schema.setPropertySchemas(new ArrayList<>());

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema.propertySchemas' can't be null or empty");
		Mockito.when(validator.validate(schema)).thenReturn(validationResult);

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.
				postSchema(schema);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void getSchemaByIdTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		final Long id = schema.getId();
		Mockito.when(service.findById(id)).thenReturn(Optional.of(schema));

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.getSchemaById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(new ValidationResult(), responseBody.getValidationResult());

		Assertions.assertEquals(schema, responseBody.getBody());
	}

	@Test
	void getSchemaByIdNotFoundTest() {
		Mockito.when(service.findById(INCORRECT_LONG_ID)).thenReturn(Optional.empty());

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.
				getSchemaById(INCORRECT_LONG_ID);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema' with id = " + INCORRECT_LONG_ID + " not found");

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void getSchemasTest() {
		final List<DataUnitSchemaDTO> schemas = schemaGenerator.generateMultipleObjects();
		Mockito.when(service.findAll()).thenReturn(schemas);

		final ResponseEntity<GenericValidatedResponse<List<DataUnitSchemaDTO>>> response = controller.getSchemas();
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<List<DataUnitSchemaDTO>> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(new ValidationResult(), responseBody.getValidationResult());

		Assertions.assertTrue(Objects.deepEquals(schemas, responseBody.getBody()));
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

		final List<DataUnitSchemaDTO> schemas = responseBody.getBody();
		Assertions.assertNotNull(schemas);
		Assertions.assertTrue(schemas.isEmpty());
	}

	@Test
	void putValidSchemaTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(validator.validate(schema)).thenReturn(validationResult);
		Mockito.when(service.save(schema)).thenReturn(schema);

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.
				putSchema(schema.getId(), schema);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertEquals(schema, responseBody.getBody());
	}

	@Test
	void putValidSchemaIdIsNullTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		final Long id = schema.getId();
		schema.setId(null);
		Mockito.when(validator.validate(schema)).thenReturn(new ValidationResult());

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.
				putSchema(id, schema);
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
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		Mockito.when(validator.validate(schema)).thenReturn(new ValidationResult());

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.
				putSchema(INCORRECT_LONG_ID, schema);
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
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		schema.setPropertySchemas(new ArrayList<>());

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema.propertySchemas' can't be null or empty");
		Mockito.when(validator.validate(schema)).thenReturn(validationResult);

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.
				putSchema(schema.getId(), schema);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		checkValidationResultsEquality(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void deleteSchemaByIdTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		final Long id = schema.getId();

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
