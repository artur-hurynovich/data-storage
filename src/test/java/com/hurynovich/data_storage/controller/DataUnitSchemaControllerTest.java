package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.controller.exception.ControllerValidationException;
import com.hurynovich.data_storage.converter.ApiConverter;
import com.hurynovich.data_storage.model.AbstractServiceModel_;
import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelImpl_;
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
	private Validator<DataUnitSchemaApiModel> validator;

	@Mock
	private MassReadService<DataUnitSchemaServiceModel, Long> service;

	@Mock
	private Paginator paginator;

	@Mock
	private ApiConverter<DataUnitSchemaApiModel, DataUnitSchemaServiceModel> converter;

	@Mock
	private PaginationParams params;

	private DataUnitSchemaController controller;

	private final ModelGenerator<DataUnitSchemaApiModel> apiModelGenerator =
			new DataUnitSchemaApiModelGenerator();

	private final ModelGenerator<DataUnitSchemaServiceModel> serviceModelGenerator =
			new DataUnitSchemaServiceModelGenerator();

	private final ModelAsserter<DataUnitSchemaApiModel, DataUnitSchemaServiceModel, DataUnitSchemaPersistentModel> dataUnitSchemaAsserter =
			new DataUnitSchemaAsserter();

	@BeforeEach
	public void initController() {
		controller = new DataUnitSchemaController(validator, service, paginator, converter);
	}

	@Test
	void postValidSchemaTest() {
		final DataUnitSchemaApiModel apiModel = apiModelGenerator.generateNullId();
		Mockito.when(validator.validate(apiModel)).thenReturn(new ValidationResult());
		final DataUnitSchemaServiceModel serviceModelWithNullId = serviceModelGenerator.generateNullId();
		Mockito.when(converter.convert(apiModel)).thenReturn(serviceModelWithNullId);
		final DataUnitSchemaServiceModel serviceModel = serviceModelGenerator.generate();
		Mockito.when(service.save(serviceModelWithNullId)).thenReturn(serviceModel);
		Mockito.when(converter.convert(serviceModel)).thenReturn(apiModelGenerator.generate());

		final ResponseEntity<DataUnitSchemaApiModel> response = controller.postSchema(apiModel);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		final DataUnitSchemaApiModel responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		dataUnitSchemaAsserter.assertEquals(apiModel, responseBody, AbstractServiceModel_.ID);

		Assertions.assertNotNull(responseBody.getId());
	}

	@Test
	void postValidSchemaIdIsNotNullTest() {
		final DataUnitSchemaApiModel apiModel = apiModelGenerator.generate();
		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.postSchema(apiModel));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.id' should be null"));
	}

	@Test
	void postValidSchemaNameIsNullTest() {
		final DataUnitSchemaApiModel apiModel = apiModelGenerator.generateNullId();
		TestReflectionUtils.setField(apiModel, DataUnitSchemaServiceModelImpl_.NAME, null);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		final String error = "'dataUnitSchema.name' can't be null, empty or blank";
		validationResult.addError(error);
		Mockito.when(validator.validate(apiModel)).thenReturn(validationResult);

		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.postSchema(apiModel));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains(error));
	}

	@Test
	void postNotValidSchemaTest() {
		final DataUnitSchemaApiModel apiModel = apiModelGenerator.generateNullId();
		TestReflectionUtils.setField(apiModel, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS, new ArrayList<>());

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		final String error = "'dataUnitSchema.propertySchemas' can't be null or empty";
		validationResult.addError(error);
		Mockito.when(validator.validate(apiModel)).thenReturn(validationResult);

		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.postSchema(apiModel));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains(error));
	}

	@Test
	void getSchemaByIdTest() {
		final DataUnitSchemaApiModel apiModel = apiModelGenerator.generate();
		final Long id = apiModel.getId();
		final DataUnitSchemaServiceModel serviceModel = serviceModelGenerator.generate();
		Mockito.when(service.findById(id)).thenReturn(Optional.of(serviceModel));
		Mockito.when(converter.convert(serviceModel)).thenReturn(apiModel);

		final ResponseEntity<DataUnitSchemaApiModel> response = controller.getSchemaById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final DataUnitSchemaApiModel responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		dataUnitSchemaAsserter.assertEquals(apiModel, responseBody);
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
		Mockito.when(paginator.buildParams(PAGE_NUMBER, ELEMENTS_PER_PAGE)).thenReturn(params);
		final List<DataUnitSchemaServiceModel> serviceModels = serviceModelGenerator.generateList();
		Mockito.when(service.findAll(params)).thenReturn(serviceModels);
		final List<DataUnitSchemaApiModel> apiModels = apiModelGenerator.generateList();
		for (int i = 0; i < serviceModels.size(); i++) {
			Mockito.when(converter.convert(serviceModels.get(i))).thenReturn(apiModels.get(i));
		}
		Mockito.when(service.count()).thenReturn(TOTAL_ELEMENTS_COUNT);
		Mockito.when(paginator.buildPage(apiModels, TOTAL_ELEMENTS_COUNT, params)).
				thenReturn(GenericPage.builder(apiModels).
						withTotalElementsCount(TOTAL_ELEMENTS_COUNT).
						withPreviousPageNumber(null).
						withCurrentPageNumber(1L).
						withNextPageNumber(null).
						withTotalPagesCount(1L).
						build());

		final ResponseEntity<GenericPage<DataUnitSchemaApiModel>> response = controller.getSchemas(PAGE_NUMBER);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericPage<DataUnitSchemaApiModel> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		Assertions.assertEquals(serviceModels.size(), responseBody.getElements().size());
		for (int i = 0; i < serviceModels.size(); i++) {
			dataUnitSchemaAsserter.assertEquals(serviceModels.get(i), responseBody.getElements().get(i));
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

		final ResponseEntity<GenericPage<DataUnitSchemaApiModel>> response = controller.
				getSchemas(PAGE_NUMBER);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericPage<DataUnitSchemaApiModel> responseBody = response.getBody();
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
		final DataUnitSchemaApiModel apiModel = apiModelGenerator.generate();
		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(validator.validate(apiModel)).thenReturn(validationResult);
		final DataUnitSchemaServiceModel serviceModel = serviceModelGenerator.generate();
		Mockito.when(converter.convert(apiModel)).thenReturn(serviceModel);
		Mockito.when(service.save(serviceModel)).thenReturn(serviceModel);
		Mockito.when(converter.convert(serviceModel)).thenReturn(apiModel);

		final Long id = apiModel.getId();
		final ResponseEntity<DataUnitSchemaApiModel> response = controller.putSchema(id, apiModel);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final DataUnitSchemaApiModel responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		dataUnitSchemaAsserter.assertEquals(apiModel, responseBody);
	}

	@Test
	void putValidSchemaIdIsNullTest() {
		final DataUnitSchemaApiModel apiModel = apiModelGenerator.generate();
		final Long id = apiModel.getId();
		final DataUnitSchemaApiModel apiModelWithNullId = apiModelGenerator.generateNullId();
		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.putSchema(id, apiModelWithNullId));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.id' should be equal to path variable 'id'"));
	}

	@Test
	void putValidSchemaIncorrectIdTest() {
		final DataUnitSchemaApiModel apiModel = apiModelGenerator.generate();
		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class,
						() -> controller.putSchema(INCORRECT_LONG_ID, apiModel));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.id' should be equal to path variable 'id'"));
	}

	@Test
	void putNotValidSchemaTest() {
		final DataUnitSchemaApiModel apiModel = apiModelGenerator.generate();
		TestReflectionUtils.setField(apiModel, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS, new ArrayList<>());
		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		final String error = "'dataUnitSchema.propertySchemas' can't be null or empty";
		validationResult.addError(error);
		Mockito.when(validator.validate(apiModel)).thenReturn(validationResult);

		final Long id = apiModel.getId();
		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class,
						() -> controller.putSchema(id, apiModel));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains(error));
	}

	@Test
	void deleteSchemaByIdTest() {
		final DataUnitSchemaApiModel apiModel = apiModelGenerator.generate();
		final Long id = apiModel.getId();

		final ResponseEntity<DataUnitSchemaApiModel> response = controller.deleteSchemaById(id);
		Mockito.verify(service).deleteById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

		final DataUnitSchemaApiModel responseBody = response.getBody();
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
