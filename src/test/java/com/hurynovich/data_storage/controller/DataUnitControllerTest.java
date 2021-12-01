package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.controller.exception.ControllerValidationException;
import com.hurynovich.data_storage.filter.model.DataUnitFilter;
import com.hurynovich.data_storage.model.AbstractEntity_;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument_;
import com.hurynovich.data_storage.service.dto_service.DataUnitService;
import com.hurynovich.data_storage.service.paginator.Paginator;
import com.hurynovich.data_storage.service.paginator.model.GenericPage;
import com.hurynovich.data_storage.test_object_generator.TestIdentifiedObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDTOGenerator;
import com.hurynovich.data_storage.test_objects_asserter.TestIdentifiedObjectsAsserter;
import com.hurynovich.data_storage.test_objects_asserter.impl.DataUnitAsserter;
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

	private final TestIdentifiedObjectsAsserter<DataUnitDTO, DataUnitDocument> dataUnitAsserter =
			new DataUnitAsserter();

	@BeforeEach
	public void initController() {
		controller = new DataUnitController(dataUnitValidator, filterValidator, service, paginator);
	}

	@Test
	void postValidDataUnitTest() {
		final DataUnitDTO newDataUnit = dataUnitGenerator.generateObjectNullId();
		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(dataUnitValidator.validate(newDataUnit)).thenReturn(validationResult);
		Mockito.when(service.save(newDataUnit)).thenReturn(dataUnitGenerator.generateObject());

		final ResponseEntity<DataUnitDTO> response = controller.postDataUnit(newDataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		final DataUnitDTO responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		dataUnitAsserter.assertEquals(newDataUnit, responseBody, AbstractEntity_.ID);

		Assertions.assertNotNull(responseBody.getId());
	}

	@Test
	void postValidDataUnitIdIsNotNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.postDataUnit(dataUnit));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnit.id' should be null"));
	}

	@Test
	void postValidDataUnitSchemaIdIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObjectNullId();
		TestReflectionUtils.setField(dataUnit, DataUnitDocument_.SCHEMA_ID, null);

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		final String error = "'dataUnit.schemaId' can't be null";
		validationResult.addError(error);
		Mockito.when(dataUnitValidator.validate(dataUnit)).thenReturn(validationResult);

		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.postDataUnit(dataUnit));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains(error));
	}

	@Test
	void postNotValidDataUnitTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObjectNullId();
		TestReflectionUtils.setField(dataUnit, DataUnitDocument_.PROPERTIES, new ArrayList<>());

		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		final String error = "'dataUnit.properties' can't be null or empty";
		validationResult.addError(error);
		Mockito.when(dataUnitValidator.validate(dataUnit)).thenReturn(validationResult);

		final ControllerValidationException exception = Assertions.
				assertThrows(ControllerValidationException.class, () -> controller.postDataUnit(dataUnit));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains(error));
	}

	@Test
	void getDataUnitByIdTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final String id = dataUnit.getId();
		Mockito.when(service.findById(id)).thenReturn(Optional.of(dataUnit));

		final ResponseEntity<DataUnitDTO> response = controller.getDataUnitById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final DataUnitDTO responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		dataUnitAsserter.assertEquals(dataUnit, responseBody);
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

		final ResponseEntity<GenericPage<DataUnitDTO>> response = controller.
				getDataUnits(PAGE_NUMBER, filter);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericPage<DataUnitDTO> responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		Assertions.assertEquals(dataUnits.size(), responseBody.getElements().size());
		for (int i = 0; i < dataUnits.size(); i++) {
			dataUnitAsserter.assertEquals(dataUnits.get(i), responseBody.getElements().get(i));
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

		final ResponseEntity<GenericPage<DataUnitDTO>> response = controller.
				getDataUnits(PAGE_NUMBER, filter);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final GenericPage<DataUnitDTO> responseBody = response.getBody();
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
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final ValidationResult validationResult = new ValidationResult();
		Mockito.when(dataUnitValidator.validate(dataUnit)).thenReturn(validationResult);
		Mockito.when(service.save(dataUnit)).thenReturn(dataUnit);

		final ResponseEntity<DataUnitDTO> response = controller.putDataUnit(dataUnit.getId(), dataUnit);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final DataUnitDTO responseBody = response.getBody();
		Assertions.assertNotNull(responseBody);

		dataUnitAsserter.assertEquals(dataUnit, responseBody);
	}

	@Test
	void putValidDataUnitIdIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final String id = dataUnit.getId();
		final DataUnitDTO dataUnitWithNullId = dataUnitGenerator.generateObjectNullId();
		final ControllerValidationException exception = Assertions.assertThrows(ControllerValidationException.class,
				() -> controller.putDataUnit(id, dataUnitWithNullId));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnit.id' should be equal to path variable 'id'"));
	}

	@Test
	void putValidDataUnitIncorrectIdTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final ControllerValidationException exception = Assertions.assertThrows(ControllerValidationException.class,
				() -> controller.putDataUnit(INCORRECT_STRING_ID, dataUnit));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnit.id' should be equal to path variable 'id'"));
	}

	@Test
	void putNotValidDataUnitTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		TestReflectionUtils.setField(dataUnit, DataUnitDocument_.PROPERTIES, null);
		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.FAILURE);
		final String error = "'dataUnit.properties' can't be null or empty";
		validationResult.addError(error);
		Mockito.when(dataUnitValidator.validate(dataUnit)).thenReturn(validationResult);

		final String id = dataUnit.getId();
		final ControllerValidationException exception = Assertions.assertThrows(ControllerValidationException.class,
				() -> controller.putDataUnit(id, dataUnit));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		final Set<String> errors = exception.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains(error));
	}

	@Test
	void deleteDataUnitByIdTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final String id = dataUnit.getId();

		final ResponseEntity<DataUnitDTO> response = controller.deleteDataUnitById(id);
		Mockito.verify(service).deleteById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

		final DataUnitDTO responseBody = response.getBody();
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
