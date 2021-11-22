package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.dao.DataUnitDAO;
import com.hurynovich.data_storage.filter.model.DataUnitFilter;
import com.hurynovich.data_storage.model.AbstractDocument_;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.service.dto_service.DataUnitService;
import com.hurynovich.data_storage.test_object_generator.TestIdentifiedObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDTOGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDocumentGenerator;
import com.hurynovich.data_storage.test_objects_asserter.Asserter;
import com.hurynovich.data_storage.test_objects_asserter.impl.DataUnitAsserter;
import com.hurynovich.data_storage.test_objects_asserter.model.DataUnitWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.INCORRECT_STRING_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitServiceImplTest {

	@Mock
	private DataUnitDAO dao;

	@Mock
	private Converter<DataUnitDTO, DataUnitDocument, String> converter;

	@Mock
	private PaginationParams params;

	@Mock
	private DataUnitFilter filter;

	private DataUnitService service;

	private final TestIdentifiedObjectGenerator<DataUnitDTO> dtoGenerator =
			new TestDataUnitDTOGenerator();

	private final TestIdentifiedObjectGenerator<DataUnitDocument> entityGenerator =
			new TestDataUnitDocumentGenerator();

	private final Asserter<DataUnitWrapper> asserter = new DataUnitAsserter();

	@BeforeEach
	public void initService() {
		service = new DataUnitServiceImpl(dao, converter);
	}

	@Test
	void saveTest() {
		final DataUnitDTO dto = dtoGenerator.generateObjectNullId();
		final DataUnitDocument document = entityGenerator.generateObject();
		Mockito.when(converter.convert(dto)).thenReturn(document);
		Mockito.when(dao.save(document)).thenReturn(document);
		Mockito.when(converter.convert(document)).thenReturn(dtoGenerator.generateObject());

		final DataUnitDTO savedDTO = service.save(dto);
		asserter.assertEquals(DataUnitWrapper.of(dto), DataUnitWrapper.of(savedDTO),
				AbstractDocument_.ID);
		Assertions.assertNotNull(savedDTO.getId());
	}

	@Test
	void findByIdTest() {
		final DataUnitDocument document = entityGenerator.generateObject();
		final String id = document.getId();
		Mockito.when(dao.findById(id)).thenReturn(Optional.of(document));

		final DataUnitDTO dto = dtoGenerator.generateObject();
		Mockito.when(converter.convert(document)).thenReturn(dto);

		final Optional<DataUnitDTO> savedDTOOptional = service.findById(id);
		Assertions.assertTrue(savedDTOOptional.isPresent());
		asserter.assertEquals(DataUnitWrapper.of(dto), DataUnitWrapper.of(savedDTOOptional.get()));
	}

	@Test
	void findByIdEmptyTest() {
		Mockito.when(dao.findById(INCORRECT_STRING_ID)).thenReturn(Optional.empty());

		final Optional<DataUnitDTO> savedDTOOptional = service.findById(INCORRECT_STRING_ID);
		Assertions.assertFalse(savedDTOOptional.isPresent());
	}

	@Test
	void deleteSuccessTest() {
		final DataUnitDocument document = entityGenerator.generateObject();
		final String id = document.getId();
		Mockito.when(dao.findById(id)).thenReturn(Optional.of(document));

		service.deleteById(id);

		Mockito.verify(dao).delete(document);
	}

	@Test
	void deleteNotFoundTest() {
		Mockito.when(dao.findById(INCORRECT_STRING_ID)).thenReturn(Optional.empty());

		Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteById(INCORRECT_STRING_ID),
				"'DataUnitDocument' with id = '" + INCORRECT_STRING_ID + "' not found");
	}

	@Test
	void findAllTest() {
		final List<DataUnitDocument> documents = entityGenerator.generateObjects();
		Mockito.when(dao.findAll(params, filter)).thenReturn(documents);

		final List<DataUnitDTO> dtos = dtoGenerator.generateObjects();
		for (int i = 0; i < documents.size(); i++) {
			final DataUnitDTO dto = dtos.get(i);
			Mockito.when(converter.convert(documents.get(i))).thenReturn(dto);
		}

		final List<DataUnitDTO> savedDTOs = service.findAll(params, filter);
		Assertions.assertNotNull(savedDTOs);
		Assertions.assertFalse(savedDTOs.isEmpty());
		Assertions.assertEquals(dtos.size(), savedDTOs.size());
		for (int i = 0; i < dtos.size(); i++) {
			asserter.assertEquals(
					DataUnitWrapper.of(dtos.get(i)), DataUnitWrapper.of(savedDTOs.get(i)));
		}
	}

	@Test
	void findAllEmptyTest() {
		final List<DataUnitDocument> documents = new ArrayList<>();
		Mockito.when(dao.findAll(params, filter)).thenReturn(documents);

		final List<DataUnitDTO> savedDTOs = service.findAll(params, filter);
		Assertions.assertNotNull(savedDTOs);
		Assertions.assertTrue(savedDTOs.isEmpty());
	}

	@Test
	void countTest() {
		final List<DataUnitDocument> documents = entityGenerator.generateObjects();
		final long count = documents.size();
		Mockito.when(dao.count(filter)).thenReturn(count);

		Assertions.assertEquals(count, service.count(filter));
	}

	@Test
	void deleteAllBySchemaIdTest() {
		final DataUnitDocument document = entityGenerator.generateObject();
		final Long schemaId = document.getSchemaId();
		service.deleteAllBySchemaId(schemaId);

		Mockito.verify(dao).deleteAllBySchemaId(schemaId);
	}

}
