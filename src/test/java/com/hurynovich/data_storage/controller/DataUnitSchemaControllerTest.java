package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.controller.exception.ControllerValidationException;
import com.hurynovich.data_storage.model.AbstractEntity_;
import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelGenerator;
import com.hurynovich.data_storage.model_asserter.ModelAsserter;
import com.hurynovich.data_storage.model_asserter.impl.DataUnitSchemaAsserter;
import com.hurynovich.data_storage.service.dto_service.MassReadService;
import com.hurynovich.data_storage.service.paginator.Paginator;
import com.hurynovich.data_storage.service.paginator.model.GenericPage;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.hurynovich.data_storage.model.ModelConstants.INCORRECT_LONG_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaControllerTest {

	private static final int PAGE_NUMBER = 1;

	private static final int ELEMENTS_PER_PAGE = 20;

	private static final long TOTAL_ELEMENTS_COUNT = 10;

	@Mock
	private Validator<DataUnitSchemaServiceModel> validator;

	@Mock
	private MassReadService<DataUnitSchemaServiceModel, Long> service;

	@Mock
	private Paginator paginator;

	@Mock
	private PaginationParams params;

	private DataUnitSchemaController controller;

	private final ModelGenerator<DataUnitSchemaServiceModel> schemaGenerator =
			new DataUnitSchemaServiceModelGenerator();

	private final ModelAsserter<DataUnitSchemaServiceModel, DataUnitSchemaPersistentModel> dataUnitSchemaAsserter =
			new DataUnitSchemaAsserter();

	@BeforeEach
	public void initController() {
		controller = new DataUnitSchemaController(validator, service, paginator);
	}

	@Test
	void postValidSchemaTest() {
		final DataUnitSchemaServiceModel newSchema = schemaGenerator.generateNullId();
		Mockito.when(validator.validate(newSchema)).thenReturn(new ValidationResult());
		Mockito.when(service.save(newSchema)).thenReturn(schemaGenerator.generate());

		final ResponseEntity<DataUnitSchemaServiceModel> response = controller.postSchema(newSchema);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		final DataUnitSchemaServiceModel responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		dataUnitSchemaAsserter.assertEquals(newSchema, responseBody, AbstractEntity_.ID);

		Assertions.assertNotNull(responseBody.getId());
	}

	@Test
	void postValidSchemaIdIsNotNullTest() {
		final DataUnitSchemaServiceModel schema = schemaGenerator.generate();
		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.postSchema(schema));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.id' should be null"));
	}

	@Test
	void postValidSchemaNameIsNullTest() {
		final DataUnitSchemaServiceModel schema = schemaGenerator.generateNullId();
		TestReflectionUtils.setField(schema, DataUnitSchemaEntity_.NAME, null);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		final String error = "'dataUnitSchema.name' can't be null, empty or blank";
		validationResult.addError(error);
		Mockito.when(validator.validate(schema)).thenReturn(validationResult);

		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.postSchema(schema));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains(error));
	}

	@Test
	void postNotValidSchemaTest() {
		final DataUnitSchemaServiceModel schema = schemaGenerator.generateNullId();
		TestReflectionUtils.setField(schema, DataUnitSchemaEntity_.PROPERTY_SCHEMAS, new ArrayList<>());

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		final String error = "'dataUnitSchema.propertySchemas' can't be null or empty";
		validationResult.addError(error);
		Mockito.when(validator.validate(schema)).thenReturn(validationResult);

		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.postSchema(schema));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains(error));
	}

	@Test
	void getSchemaByIdTest() {
		final DataUnitSchemaServiceModel schema = schemaGenerator.generate();
		final Long id = schema.getId();
		Mockito.when(service.findById(id)).thenReturn(Optional.of(schema));

		final ResponseEntity<DataUnitSchemaServiceModel> response = controller.getSchemaById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final DataUnitSchemaServiceModel responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		dataUnitSchemaAsserter.assertEquals(schema, responseBody);
	}

	@Test
	void getSchemaByIdNotFoundTest() {
		Mockito.when(service.findById(INCORRECT_LONG_ID)).thenReturn(Optional.empty());

		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.getSchemaById(INCORRECT_LONG_ID));
		Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema' with id = '" + INCORRECT_LONG_ID + "' not found"));
	}

	@Test
	void getSchemasTest() {
		final List<DataUnitSchemaServiceModel> schemas = schemaGenerator.generateList();
		Mockito.when(paginator.buildParams(PAGE_NUMBER, ELEMENTS_PER_PAGE)).thenReturn(params);
		Mockito.when(service.findAll(params)).thenReturn(schemas);
		Mockito.when(service.count()).thenReturn(TOTAL_ELEMENTS_COUNT);
		Mockito.when(paginator.buildPage(schemas, TOTAL_ELEMENTS_COUNT, params)).
				thenReturn(GenericPage.builder(schemas).
						withTotalElementsCount(TOTAL_ELEMENTS_COUNT).
						withPreviousPageNumber(null).
						withCurrentPageNumber(1L).
						withNextPageNumber(null).
						withTotalPagesCount(1L).
						build());

		final ResponseEntity<GenericPage<DataUnitSchemaServiceModel>> response = controller.getSchemas(PAGE_NUMBER);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericPage<DataUnitSchemaServiceModel> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		Assertions.assertEquals(schemas.size(), responseBody.getElements().size());
		for (int i = 0; i < schemas.size(); i++) {
			dataUnitSchemaAsserter.assertEquals(schemas.get(i), responseBody.getElements().get(i));
		}

		Assertions.assertEquals(TOTAL_ELEMENTS_COUNT, responseBody.getTotalElementsCount());
		Assertions.assertNull(responseBody.getPreviousPageNumber());
		Assertions.assertEquals(1L, responseBody.getCurrentPageNumber());
		Assertions.assertNull(responseBody.getNextPageNumber());
		Assertions.assertEquals(1L, responseBody.getTotalPagesCount());
	}

	@Test
	void getSchemasEmptyTest() {
		Mockito.when(paginator.buildParams(PAGE_NUMBER, ELEMENTS_PER_PAGE)).thenReturn(params);
		final List<DataUnitSchemaServiceModel> schemas = new ArrayList<>();
		Mockito.when(service.findAll(params)).thenReturn(schemas);
		Mockito.when(service.count()).thenReturn(0L);
		Mockito.when(paginator.buildPage(schemas, 0L, params)).
				thenReturn(GenericPage.builder(schemas).
						withTotalElementsCount(0L).
						withPreviousPageNumber(null).
						withCurrentPageNumber(null).
						withNextPageNumber(null).
						withTotalPagesCount(0L).
						build());

		final ResponseEntity<GenericPage<DataUnitSchemaServiceModel>> response = controller.
				getSchemas(PAGE_NUMBER);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericPage<DataUnitSchemaServiceModel> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		Assertions.assertTrue(responseBody.getElements().isEmpty());
		Assertions.assertEquals(0L, responseBody.getTotalElementsCount());
		Assertions.assertNull(responseBody.getPreviousPageNumber());
		Assertions.assertNull(responseBody.getCurrentPageNumber());
		Assertions.assertNull(responseBody.getNextPageNumber());
		Assertions.assertEquals(0L, responseBody.getTotalPagesCount());
	}

	@Test
	void putValidSchemaTest() {
		final DataUnitSchemaServiceModel schema = schemaGenerator.generate();
		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(validator.validate(schema)).thenReturn(validationResult);
		Mockito.when(service.save(schema)).thenReturn(schema);

		final Long id = schema.getId();
		final ResponseEntity<DataUnitSchemaServiceModel> response = controller.putSchema(id, schema);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final DataUnitSchemaServiceModel responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		dataUnitSchemaAsserter.assertEquals(schema, responseBody);
	}

	@Test
	void putValidSchemaIdIsNullTest() {
		final DataUnitSchemaServiceModel schema = schemaGenerator.generate();
		final Long id = schema.getId();
		final DataUnitSchemaServiceModel schemaWithNullId = schemaGenerator.generateNullId();
		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.putSchema(id, schemaWithNullId));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.id' should be equal to path variable 'id'"));
	}

	@Test
	void putValidSchemaIncorrectIdTest() {
		final DataUnitSchemaServiceModel schema = schemaGenerator.generate();
		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class,
						() -> controller.putSchema(INCORRECT_LONG_ID, schema));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.id' should be equal to path variable 'id'"));
	}

	@Test
	void putNotValidSchemaTest() {
		final DataUnitSchemaServiceModel schema = schemaGenerator.generate();
		TestReflectionUtils.setField(schema, DataUnitSchemaEntity_.PROPERTY_SCHEMAS, new ArrayList<>());
		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		final String error = "'dataUnitSchema.propertySchemas' can't be null or empty";
		validationResult.addError(error);
		Mockito.when(validator.validate(schema)).thenReturn(validationResult);

		final Long id = schema.getId();
		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class,
						() -> controller.putSchema(id, schema));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains(error));
	}

	@Test
	void deleteSchemaByIdTest() {
		final DataUnitSchemaServiceModel schema = schemaGenerator.generate();
		final Long id = schema.getId();

		final ResponseEntity<DataUnitSchemaServiceModel> response = controller.deleteSchemaById(id);
		Mockito.verify(service).deleteById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

		final DataUnitSchemaServiceModel responseBody = response.getBody();
		Assertions.assertNull(responseBody);
	}

	@Test
	void deleteSchemaByIdNotFoundTest() {
		final String error = "'dataUnitSchema' with id = '" + INCORRECT_LONG_ID + "' not found";
		Mockito.doThrow(new EntityNotFoundException(error)).when(service).deleteById(INCORRECT_LONG_ID);
		final EntityNotFoundException exception = Assertions.
				assertThrows(EntityNotFoundException.class,
						() -> controller.deleteSchemaById(INCORRECT_LONG_ID));
		Assertions.assertEquals(error, exception.getMessage());
	}
}
