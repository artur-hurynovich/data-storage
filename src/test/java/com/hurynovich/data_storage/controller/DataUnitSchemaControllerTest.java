package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.controller.model.GenericValidatedResponse;
import com.hurynovich.data_storage.model.AbstractEntity_;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.dto_service.MassReadService;
import com.hurynovich.data_storage.service.paginator.Paginator;
import com.hurynovich.data_storage.service.paginator.model.GenericPage;
import com.hurynovich.data_storage.test_object_generator.TestIdentifiedObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import com.hurynovich.data_storage.test_objects_asserter.Asserter;
import com.hurynovich.data_storage.test_objects_asserter.impl.DataUnitSchemaAsserter;
import com.hurynovich.data_storage.test_objects_asserter.impl.ValidationResultAsserter;
import com.hurynovich.data_storage.test_objects_asserter.model.DataUnitSchemaWrapper;
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
import java.util.List;
import java.util.Optional;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.INCORRECT_LONG_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaControllerTest {

	private static final int PAGE_NUMBER = 1;

	private static final int ELEMENTS_PER_PAGE = 20;

	private static final long TOTAL_ELEMENTS_COUNT = 10;

	@Mock
	private Validator<DataUnitSchemaDTO> validator;

	@Mock
	private ValidationHelper helper;

	@Mock
	private MassReadService<DataUnitSchemaDTO, Long> service;

	@Mock
	private Paginator paginator;

	@Mock
	private PaginationParams params;

	private DataUnitSchemaController controller;

	private final TestIdentifiedObjectGenerator<DataUnitSchemaDTO> schemaGenerator =
			new TestDataUnitSchemaDTOGenerator();

	private final Asserter<DataUnitSchemaWrapper> schemaComparator =
			new DataUnitSchemaAsserter();

	private final Asserter<ValidationResult> validationResultComparator =
			new ValidationResultAsserter();

	@BeforeEach
	public void initController() {
		controller = new DataUnitSchemaController(validator, helper, service, paginator);
	}

	@Test
	void postValidSchemaTest() {
		final DataUnitSchemaDTO newSchema = schemaGenerator.generateObjectNullId();
		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(validator.validate(newSchema)).thenReturn(validationResult);
		Mockito.when(service.save(newSchema)).thenReturn(schemaGenerator.generateObject());

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.postSchema(newSchema);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultComparator.assertEquals(validationResult, responseBody.getValidationResult());

		schemaComparator.assertEquals(DataUnitSchemaWrapper.of(newSchema),
				DataUnitSchemaWrapper.of(responseBody.getBody()), AbstractEntity_.ID);

		Assertions.assertNotNull(responseBody.getBody().getId());
	}

	@Test
	void postValidSchemaIdIsNotNullTest() {
		Mockito.doAnswer(invocationOnMock -> {
			final ValidationResult validationResult = invocationOnMock.getArgument(1);
			validationResult.setType(ValidationResultType.FAILURE);
			validationResult.addError("'dataUnitSchema.id' should be null");

			return null;
		}).when(helper).applyIsNotNullError(Mockito.eq("dataUnitSchema.id"), Mockito.any(ValidationResult.class));
		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.postSchema(schema);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema.id' should be null");
		validationResultComparator.assertEquals(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void postValidSchemaNameIsNullTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateObjectNullId();
		TestReflectionUtils.setField(schema, "name", null);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema.name' can't be null, empty or blank");
		Mockito.when(validator.validate(schema)).thenReturn(validationResult);

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.postSchema(schema);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultComparator.assertEquals(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void postNotValidSchemaTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateObjectNullId();
		TestReflectionUtils.setField(schema, "propertySchemas", new ArrayList<>());

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

		validationResultComparator.assertEquals(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void getSchemaByIdTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		final Long id = schema.getId();
		Mockito.when(service.findById(id)).thenReturn(Optional.of(schema));

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.getSchemaById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultComparator.assertEquals(new ValidationResult(), responseBody.getValidationResult());

		schemaComparator.assertEquals(DataUnitSchemaWrapper.of(schema),
				DataUnitSchemaWrapper.of(responseBody.getBody()), AbstractEntity_.ID);
	}

	@Test
	void getSchemaByIdNotFoundTest() {
		Mockito.when(service.findById(INCORRECT_LONG_ID)).thenReturn(Optional.empty());
		Mockito.doAnswer(invocationOnMock -> {
			final ValidationResult validationResult = invocationOnMock.getArgument(2);
			validationResult.setType(ValidationResultType.FAILURE);
			validationResult.addError("'dataUnitSchema' with id = '" + INCORRECT_LONG_ID + "' not found");

			return null;
		}).when(helper).applyNotFoundByIdError(Mockito.eq("dataUnitSchema"), Mockito.eq(INCORRECT_LONG_ID),
				Mockito.any(ValidationResult.class));

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.
				getSchemaById(INCORRECT_LONG_ID);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema' with id = '" + INCORRECT_LONG_ID + "' not found");
		validationResultComparator.assertEquals(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void getSchemasTest() {
		final List<DataUnitSchemaDTO> schemas = schemaGenerator.generateObjects();
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

		final ResponseEntity<GenericValidatedResponse<GenericPage<DataUnitSchemaDTO>>> response = controller.
				getSchemas(PAGE_NUMBER);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<GenericPage<DataUnitSchemaDTO>> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultComparator.assertEquals(new ValidationResult(), responseBody.getValidationResult());

		final GenericPage<DataUnitSchemaDTO> page = responseBody.getBody();
		Assertions.assertEquals(schemas.size(), page.getElements().size());
		for (int i = 0; i < schemas.size(); i++) {
			schemaComparator.assertEquals(
					DataUnitSchemaWrapper.of(schemas.get(i)), DataUnitSchemaWrapper.of(page.getElements().get(i)));
		}

		Assertions.assertEquals(TOTAL_ELEMENTS_COUNT, page.getTotalElementsCount());
		Assertions.assertNull(page.getPreviousPageNumber());
		Assertions.assertEquals(1L, page.getCurrentPageNumber());
		Assertions.assertNull(page.getNextPageNumber());
		Assertions.assertEquals(1L, page.getTotalPagesCount());
	}

	@Test
	void getSchemasEmptyTest() {
		Mockito.when(paginator.buildParams(PAGE_NUMBER, ELEMENTS_PER_PAGE)).thenReturn(params);
		final List<DataUnitSchemaDTO> schemas = new ArrayList<>();
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

		final ResponseEntity<GenericValidatedResponse<GenericPage<DataUnitSchemaDTO>>> response = controller.
				getSchemas(PAGE_NUMBER);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<GenericPage<DataUnitSchemaDTO>> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultComparator.assertEquals(new ValidationResult(), responseBody.getValidationResult());

		final GenericPage<DataUnitSchemaDTO> page = responseBody.getBody();
		Assertions.assertTrue(page.getElements().isEmpty());
		Assertions.assertEquals(0L, page.getTotalElementsCount());
		Assertions.assertNull(page.getPreviousPageNumber());
		Assertions.assertNull(page.getCurrentPageNumber());
		Assertions.assertNull(page.getNextPageNumber());
		Assertions.assertEquals(0L, page.getTotalPagesCount());
	}

	@Test
	void putValidSchemaTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(validator.validate(schema)).thenReturn(validationResult);
		Mockito.when(service.save(schema)).thenReturn(schema);

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.
				putSchema(schema.getId(), schema);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultComparator.assertEquals(validationResult, responseBody.getValidationResult());

		schemaComparator.assertEquals(DataUnitSchemaWrapper.of(schema), DataUnitSchemaWrapper.of(responseBody.getBody()));
	}

	@Test
	void putValidSchemaIdIsNullTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		final Long id = schema.getId();
		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.
				putSchema(id, schemaGenerator.generateObjectNullId());
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema.id' should be equal to path variable 'id'");
		validationResultComparator.assertEquals(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void putValidSchemaIncorrectIdTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.
				putSchema(INCORRECT_LONG_ID, schema);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema.id' should be equal to path variable 'id'");
		validationResultComparator.assertEquals(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void putNotValidSchemaTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		TestReflectionUtils.setField(schema, "propertySchemas", new ArrayList<>());

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

		validationResultComparator.assertEquals(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void deleteSchemaByIdTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		final Long id = schema.getId();

		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> response = controller.deleteSchemaById(id);
		Mockito.verify(service).deleteById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultComparator.assertEquals(new ValidationResult(), responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

}
