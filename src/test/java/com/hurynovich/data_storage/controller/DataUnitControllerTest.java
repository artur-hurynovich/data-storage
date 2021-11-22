package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.controller.model.GenericValidatedResponse;
import com.hurynovich.data_storage.filter.model.DataUnitFilter;
import com.hurynovich.data_storage.model.AbstractEntity_;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument_;
import com.hurynovich.data_storage.service.dto_service.DataUnitService;
import com.hurynovich.data_storage.service.paginator.Paginator;
import com.hurynovich.data_storage.service.paginator.model.GenericPage;
import com.hurynovich.data_storage.test_object_generator.TestIdentifiedObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDTOGenerator;
import com.hurynovich.data_storage.test_objects_asserter.Asserter;
import com.hurynovich.data_storage.test_objects_asserter.impl.DataUnitAsserter;
import com.hurynovich.data_storage.test_objects_asserter.impl.ValidationResultComparator;
import com.hurynovich.data_storage.test_objects_asserter.model.DataUnitWrapper;
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
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.INCORRECT_STRING_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitControllerTest {

	private static final int PAGE_NUMBER = 1;

	private static final int ELEMENTS_PER_PAGE = 20;

	private static final long TOTAL_ELEMENTS_COUNT = 10;

	@Mock
	private Validator<DataUnitDTO> dataUnitValidator;

	@Mock
	private Validator<DataUnitFilter> filterValidator;

	@Mock
	private ValidationHelper helper;

	@Mock
	private DataUnitService service;

	@Mock
	private Paginator paginator;

	@Mock
	private PaginationParams params;

	@Mock
	private DataUnitFilter filter;

	private DataUnitController controller;

	private final TestIdentifiedObjectGenerator<DataUnitDTO> dataUnitGenerator =
			new TestDataUnitDTOGenerator();

	private final Asserter<DataUnitWrapper> dataUnitAsserter =
			new DataUnitAsserter();

	private final Asserter<ValidationResult> validationResultAsserter =
			new ValidationResultComparator();

	@BeforeEach
	public void initController() {
		controller = new DataUnitController(dataUnitValidator, filterValidator, helper, service, paginator);
	}

	@Test
	void postValidDataUnitTest() {
		final DataUnitDTO newDataUnit = dataUnitGenerator.generateObjectNullId();
		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(dataUnitValidator.validate(newDataUnit)).thenReturn(validationResult);
		Mockito.when(service.save(newDataUnit)).thenReturn(dataUnitGenerator.generateObject());

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.postDataUnit(newDataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultAsserter.assertEquals(validationResult, responseBody.getValidationResult());

		dataUnitAsserter.assertEquals(DataUnitWrapper.of(newDataUnit), DataUnitWrapper.of(responseBody.getBody()),
				AbstractEntity_.ID);

		Assertions.assertNotNull(responseBody.getBody().getId());
	}

	@Test
	void postValidDataUnitIdIsNotNullTest() {
		Mockito.doAnswer(invocationOnMock -> {
			final ValidationResult validationResult = invocationOnMock.getArgument(1);
			validationResult.setType(ValidationResultType.FAILURE);
			validationResult.addError("'dataUnit.id' should be null");

			return null;
		}).when(helper).applyIsNotNullError(Mockito.eq("dataUnit.id"), Mockito.any(ValidationResult.class));
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.postDataUnit(dataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit.id' should be null");
		validationResultAsserter.assertEquals(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void postValidDataUnitSchemaIdIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObjectNullId();
		TestReflectionUtils.setField(dataUnit, DataUnitDocument_.SCHEMA_ID, null);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit.schemaId' can't be null");
		Mockito.when(dataUnitValidator.validate(dataUnit)).thenReturn(validationResult);

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.postDataUnit(dataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultAsserter.assertEquals(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void postNotValidDataUnitTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObjectNullId();
		TestReflectionUtils.setField(dataUnit, DataUnitDocument_.PROPERTIES, new ArrayList<>());

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit.properties' can't be null or empty");
		Mockito.when(dataUnitValidator.validate(dataUnit)).thenReturn(validationResult);

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.
				postDataUnit(dataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultAsserter.assertEquals(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void getDataUnitByIdTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final String id = dataUnit.getId();
		Mockito.when(service.findById(id)).thenReturn(Optional.of(dataUnit));

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.getDataUnitById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultAsserter.assertEquals(new ValidationResult(), responseBody.getValidationResult());

		dataUnitAsserter.assertEquals(DataUnitWrapper.of(dataUnit), DataUnitWrapper.of(responseBody.getBody()));
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

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit' with id = '" + INCORRECT_STRING_ID + "' not found");
		validationResultAsserter.assertEquals(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void getSchemasTest() {
		Mockito.when(filterValidator.validate(filter)).thenReturn(new ValidationResult());
		Mockito.when(paginator.buildParams(PAGE_NUMBER, ELEMENTS_PER_PAGE)).thenReturn(params);
		final List<DataUnitDTO> dataUnits = dataUnitGenerator.generateObjects();
		Mockito.when(service.findAll(params, filter)).thenReturn(dataUnits);
		Mockito.when(service.count(filter)).thenReturn(TOTAL_ELEMENTS_COUNT);
		Mockito.when(paginator.buildPage(dataUnits, TOTAL_ELEMENTS_COUNT, params)).
				thenReturn(GenericPage.builder(dataUnits).
						withTotalElementsCount(TOTAL_ELEMENTS_COUNT).
						withPreviousPageNumber(null).
						withCurrentPageNumber(1L).
						withNextPageNumber(null).
						withTotalPagesCount(1L).
						build());

		final ResponseEntity<GenericValidatedResponse<GenericPage<DataUnitDTO>>> response = controller.
				getDataUnits(PAGE_NUMBER, filter);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<GenericPage<DataUnitDTO>> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultAsserter.assertEquals(new ValidationResult(), responseBody.getValidationResult());

		final GenericPage<DataUnitDTO> page = responseBody.getBody();
		Assertions.assertEquals(dataUnits.size(), page.getElements().size());
		for (int i = 0; i < dataUnits.size(); i++) {
			dataUnitAsserter.assertEquals(
					DataUnitWrapper.of(dataUnits.get(i)), DataUnitWrapper.of(page.getElements().get(i)));
		}

		Assertions.assertEquals(TOTAL_ELEMENTS_COUNT, page.getTotalElementsCount());
		Assertions.assertNull(page.getPreviousPageNumber());
		Assertions.assertEquals(1L, page.getCurrentPageNumber());
		Assertions.assertNull(page.getNextPageNumber());
		Assertions.assertEquals(1L, page.getTotalPagesCount());
	}

	@Test
	void getSchemasEmptyTest() {
		Mockito.when(filterValidator.validate(filter)).thenReturn(new ValidationResult());
		Mockito.when(paginator.buildParams(PAGE_NUMBER, ELEMENTS_PER_PAGE)).thenReturn(params);
		final List<DataUnitDTO> emptyDataUnits = new ArrayList<>();
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

		final ResponseEntity<GenericValidatedResponse<GenericPage<DataUnitDTO>>> response = controller.
				getDataUnits(PAGE_NUMBER, filter);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<GenericPage<DataUnitDTO>> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultAsserter.assertEquals(new ValidationResult(), responseBody.getValidationResult());

		final GenericPage<DataUnitDTO> page = responseBody.getBody();
		Assertions.assertTrue(page.getElements().isEmpty());
		Assertions.assertEquals(0L, page.getTotalElementsCount());
		Assertions.assertNull(page.getPreviousPageNumber());
		Assertions.assertNull(page.getCurrentPageNumber());
		Assertions.assertNull(page.getNextPageNumber());
		Assertions.assertEquals(0L, page.getTotalPagesCount());
	}

	@Test
	void getSchemasDataUnitSchemaNotFoundTest() {
		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnitSchema' with id = '" + INCORRECT_LONG_ID + "' not found");
		Mockito.when(filterValidator.validate(filter)).thenReturn(validationResult);

		final ResponseEntity<GenericValidatedResponse<GenericPage<DataUnitDTO>>> response = controller.
				getDataUnits(PAGE_NUMBER, filter);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<GenericPage<DataUnitDTO>> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultAsserter.assertEquals(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void putValidDataUnitTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(dataUnitValidator.validate(dataUnit)).thenReturn(validationResult);
		Mockito.when(service.save(dataUnit)).thenReturn(dataUnit);

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.
				putDataUnit(dataUnit.getId(), dataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultAsserter.assertEquals(validationResult, responseBody.getValidationResult());

		dataUnitAsserter.assertEquals(DataUnitWrapper.of(dataUnit), DataUnitWrapper.of(responseBody.getBody()));
	}

	@Test
	void putValidDataUnitIdIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final String id = dataUnit.getId();
		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.
				putDataUnit(id, dataUnitGenerator.generateObjectNullId());
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit.id' should be equal to path variable 'id'");
		validationResultAsserter.assertEquals(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void putValidDataUnitIncorrectIdTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.
				putDataUnit(INCORRECT_STRING_ID, dataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit.id' should be equal to path variable 'id'");
		validationResultAsserter.assertEquals(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void putNotValidDataUnitTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		TestReflectionUtils.setField(dataUnit, DataUnitDocument_.PROPERTIES, null);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		validationResult.addError("'dataUnit.properties' can't be null or empty");
		Mockito.when(dataUnitValidator.validate(dataUnit)).thenReturn(validationResult);

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.
				putDataUnit(dataUnit.getId(), dataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultAsserter.assertEquals(validationResult, responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

	@Test
	void deleteDataUnitByIdTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final String id = dataUnit.getId();

		final ResponseEntity<GenericValidatedResponse<DataUnitDTO>> response = controller.deleteDataUnitById(id);
		Mockito.verify(service).deleteById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

		final GenericValidatedResponse<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		validationResultAsserter.assertEquals(new ValidationResult(), responseBody.getValidationResult());

		Assertions.assertNull(responseBody.getBody());
	}

}
