package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.controller.exception.ControllerValidationException;
import com.hurynovich.data_storage.converter.ApiConverter;
import com.hurynovich.data_storage.filter.model.DataUnitFilter;
import com.hurynovich.data_storage.model.AbstractServiceModel_;
import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit.DataUnitApiModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitApiModelGenerator;
import com.hurynovich.data_storage.model.data_unit.DataUnitPersistentModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModelGenerator;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModelImpl_;
import com.hurynovich.data_storage.model_asserter.ModelAsserter;
import com.hurynovich.data_storage.model_asserter.impl.DataUnitAsserter;
import com.hurynovich.data_storage.service.dto_service.DataUnitService;
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

import static com.hurynovich.data_storage.model.ModelConstants.INCORRECT_STRING_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitControllerTest {

	private static final int PAGE_NUMBER = 1;

	private static final int ELEMENTS_PER_PAGE = 20;

	private static final long TOTAL_ELEMENTS_COUNT = 10;

	@Mock
	private Validator<DataUnitApiModel> dataUnitValidator;

	@Mock
	private Validator<DataUnitFilter> filterValidator;

	@Mock
	private DataUnitService service;

	@Mock
	private Paginator paginator;

	@Mock
	private ApiConverter<DataUnitApiModel, DataUnitServiceModel> converter;

	@Mock
	private PaginationParams params;

	@Mock
	private DataUnitFilter filter;

	private DataUnitController controller;

	private final ModelGenerator<DataUnitApiModel> apiModelGenerator = new DataUnitApiModelGenerator();

	private final ModelGenerator<DataUnitServiceModel> serviceModelGenerator = new DataUnitServiceModelGenerator();

	private final ModelAsserter<DataUnitApiModel, DataUnitServiceModel, DataUnitPersistentModel> dataUnitAsserter =
			new DataUnitAsserter();

	@BeforeEach
	public void initController() {
		controller = new DataUnitController(dataUnitValidator, filterValidator, service, paginator, converter);
	}

	@Test
	void postValidDataUnitTest() {
		final DataUnitApiModel apiModel = apiModelGenerator.generateNullId();
		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(dataUnitValidator.validate(apiModel)).thenReturn(validationResult);
		final DataUnitServiceModel serviceModelWithNullId = serviceModelGenerator.generateNullId();
		Mockito.when(converter.convert(apiModel)).thenReturn(serviceModelWithNullId);
		final DataUnitServiceModel serviceModel = serviceModelGenerator.generate();
		Mockito.when(service.save(serviceModelWithNullId)).thenReturn(serviceModel);
		Mockito.when(converter.convert(serviceModel)).thenReturn(apiModelGenerator.generate());

		final ResponseEntity<DataUnitApiModel> response = controller.postDataUnit(apiModel);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		final DataUnitApiModel responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		dataUnitAsserter.assertEquals(apiModel, responseBody, AbstractServiceModel_.ID);

		Assertions.assertNotNull(responseBody.getId());
	}

	@Test
	void postValidDataUnitIdIsNotNullTest() {
		final DataUnitApiModel apiModel = apiModelGenerator.generate();
		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.postDataUnit(apiModel));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnit.id' should be null"));
	}

	@Test
	void postValidDataUnitSchemaIdIsNullTest() {
		final DataUnitApiModel apiModel = apiModelGenerator.generateNullId();
		TestReflectionUtils.setField(apiModel, DataUnitServiceModelImpl_.SCHEMA_ID, null);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		final String error = "'dataUnit.schemaId' can't be null";
		validationResult.addError(error);
		Mockito.when(dataUnitValidator.validate(apiModel)).thenReturn(validationResult);

		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.postDataUnit(apiModel));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains(error));
	}

	@Test
	void postNotValidDataUnitTest() {
		final DataUnitApiModel apiModel = apiModelGenerator.generateNullId();
		TestReflectionUtils.setField(apiModel, DataUnitServiceModelImpl_.PROPERTIES, new ArrayList<>());

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		final String error = "'dataUnit.properties' can't be null or empty";
		validationResult.addError(error);
		Mockito.when(dataUnitValidator.validate(apiModel)).thenReturn(validationResult);

		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.postDataUnit(apiModel));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains(error));
	}

	@Test
	void getDataUnitByIdTest() {
		final DataUnitApiModel apiModel = apiModelGenerator.generate();
		final String id = apiModel.getId();
		final DataUnitServiceModel serviceModel = serviceModelGenerator.generate();
		Mockito.when(service.findById(id)).thenReturn(Optional.of(serviceModel));
		Mockito.when(converter.convert(serviceModel)).thenReturn(apiModel);

		final ResponseEntity<DataUnitApiModel> response = controller.getDataUnitById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final DataUnitApiModel responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		dataUnitAsserter.assertEquals(apiModel, responseBody);
	}

	@Test
	void getDataUnitByIdNotFoundTest() {
		Mockito.when(service.findById(INCORRECT_STRING_ID)).thenReturn(Optional.empty());

		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.getDataUnitById(INCORRECT_STRING_ID));
		Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnit' with id = '" + INCORRECT_STRING_ID + "' not found"));
	}

	@Test
	void getSchemasTest() {
		Mockito.when(filterValidator.validate(filter)).thenReturn(new ValidationResult());
		Mockito.when(paginator.buildParams(PAGE_NUMBER, ELEMENTS_PER_PAGE)).thenReturn(params);
		final List<DataUnitServiceModel> serviceModels = serviceModelGenerator.generateList();
		final List<DataUnitApiModel> apiModels = apiModelGenerator.generateList();
		for (int i = 0; i < serviceModels.size(); i++) {
			Mockito.when(converter.convert(serviceModels.get(i))).thenReturn(apiModels.get(i));
		}
		Mockito.when(service.findAll(params, filter)).thenReturn(serviceModels);
		Mockito.when(service.count(filter)).thenReturn(TOTAL_ELEMENTS_COUNT);
		Mockito.when(paginator.buildPage(apiModels, TOTAL_ELEMENTS_COUNT, params)).
				thenReturn(GenericPage.builder(apiModels).
						withTotalElementsCount(TOTAL_ELEMENTS_COUNT).
						withPreviousPageNumber(null).
						withCurrentPageNumber(1L).
						withNextPageNumber(null).
						withTotalPagesCount(1L).
						build());

		final ResponseEntity<GenericPage<DataUnitApiModel>> response = controller.
				getDataUnits(PAGE_NUMBER, filter);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericPage<DataUnitApiModel> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		Assertions.assertEquals(serviceModels.size(), responseBody.getElements().size());
		for (int i = 0; i < serviceModels.size(); i++) {
			dataUnitAsserter.assertEquals(serviceModels.get(i), responseBody.getElements().get(i));
		}

		Assertions.assertEquals(TOTAL_ELEMENTS_COUNT, responseBody.getTotalElementsCount());
		Assertions.assertNull(responseBody.getPreviousPageNumber());
		Assertions.assertEquals(1L, responseBody.getCurrentPageNumber());
		Assertions.assertNull(responseBody.getNextPageNumber());
		Assertions.assertEquals(1L, responseBody.getTotalPagesCount());
	}

	@Test
	void getSchemasEmptyTest() {
		Mockito.when(filterValidator.validate(filter)).thenReturn(new ValidationResult());
		Mockito.when(paginator.buildParams(PAGE_NUMBER, ELEMENTS_PER_PAGE)).thenReturn(params);
		final List<DataUnitServiceModel> emptyDataUnits = new ArrayList<>();
		Mockito.when(service.findAll(params, filter)).thenReturn(emptyDataUnits);
		Mockito.when(service.count(filter)).thenReturn(0L);
		Mockito.when(paginator.buildPage(emptyDataUnits, 0L, params)).
				thenReturn(GenericPage.builder(emptyDataUnits).
						withTotalElementsCount(0L).
						withPreviousPageNumber(null).
						withCurrentPageNumber(null).
						withNextPageNumber(null).
						withTotalPagesCount(0L).
						build());

		final ResponseEntity<GenericPage<DataUnitApiModel>> response = controller.
				getDataUnits(PAGE_NUMBER, filter);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericPage<DataUnitApiModel> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		Assertions.assertTrue(responseBody.getElements().isEmpty());
		Assertions.assertEquals(0L, responseBody.getTotalElementsCount());
		Assertions.assertNull(responseBody.getPreviousPageNumber());
		Assertions.assertNull(responseBody.getCurrentPageNumber());
		Assertions.assertNull(responseBody.getNextPageNumber());
		Assertions.assertEquals(0L, responseBody.getTotalPagesCount());
	}

	@Test
	void getSchemasDataUnitSchemaNotFoundTest() {
		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		final String error = "'dataUnitSchema' with id = '" + INCORRECT_STRING_ID + "' not found";
		validationResult.addError(error);
		Mockito.when(filterValidator.validate(filter)).thenReturn(validationResult);

		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.getDataUnits(PAGE_NUMBER, filter));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains(error));
	}

	@Test
	void putValidDataUnitTest() {
		final DataUnitApiModel apiModel = apiModelGenerator.generate();
		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(dataUnitValidator.validate(apiModel)).thenReturn(validationResult);
		final DataUnitServiceModel serviceModel = serviceModelGenerator.generate();
		Mockito.when(converter.convert(apiModel)).thenReturn(serviceModel);
		Mockito.when(service.save(serviceModel)).thenReturn(serviceModel);
		Mockito.when(converter.convert(serviceModel)).thenReturn(apiModel);

		final ResponseEntity<DataUnitApiModel> response = controller.putDataUnit(apiModel.getId(), apiModel);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final DataUnitApiModel responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		dataUnitAsserter.assertEquals(apiModel, responseBody);
	}

	@Test
	void putValidDataUnitIdIsNullTest() {
		final DataUnitApiModel apiModel = apiModelGenerator.generate();
		final String id = apiModel.getId();
		final DataUnitApiModel apiModelWithNullId = apiModelGenerator.generateNullId();
		final ControllerValidationException exception = Assertions.assertThrows(ControllerValidationException.class,
				() -> controller.putDataUnit(id, apiModelWithNullId));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnit.id' should be equal to path variable 'id'"));
	}

	@Test
	void putValidDataUnitIncorrectIdTest() {
		final DataUnitApiModel apiModel = apiModelGenerator.generate();
		final ControllerValidationException exception = Assertions.assertThrows(ControllerValidationException.class,
				() -> controller.putDataUnit(INCORRECT_STRING_ID, apiModel));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnit.id' should be equal to path variable 'id'"));
	}

	@Test
	void putNotValidDataUnitTest() {
		final DataUnitApiModel apiModel = apiModelGenerator.generate();
		TestReflectionUtils.setField(apiModel, DataUnitServiceModelImpl_.PROPERTIES, null);
		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		final String error = "'dataUnit.properties' can't be null or empty";
		validationResult.addError(error);
		Mockito.when(dataUnitValidator.validate(apiModel)).thenReturn(validationResult);

		final String id = apiModel.getId();
		final ControllerValidationException exception = Assertions.assertThrows(ControllerValidationException.class,
				() -> controller.putDataUnit(id, apiModel));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains(error));
	}

	@Test
	void deleteDataUnitByIdTest() {
		final DataUnitApiModel apiModel = apiModelGenerator.generate();
		final String id = apiModel.getId();

		final ResponseEntity<DataUnitApiModel> response = controller.deleteDataUnitById(id);
		Mockito.verify(service).deleteById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

		final DataUnitApiModel responseBody = response.getBody();
		Assertions.assertNull(responseBody);
	}

	@Test
	void deleteDataUnitByIdNotFoundTest() {
		final String error = "'dataUnit' with id = '" + INCORRECT_STRING_ID + "' not found";
		Mockito.doThrow(new EntityNotFoundException(error)).when(service).deleteById(INCORRECT_STRING_ID);
		final EntityNotFoundException exception = Assertions.
				assertThrows(EntityNotFoundException.class,
						() -> controller.deleteDataUnitById(INCORRECT_STRING_ID));
		Assertions.assertEquals(error, exception.getMessage());
	}
}
