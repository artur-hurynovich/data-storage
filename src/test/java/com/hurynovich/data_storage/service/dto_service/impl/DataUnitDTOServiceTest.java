package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.dao.DAO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.service.dto_service.DTOService;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDTOGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDocumentGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.INCORRECT_STRING_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitDTOServiceTest {

	@Mock
	private DAO<DataUnitDocument, String> dao;

	@Mock
	private DTOConverter<DataUnitDTO, DataUnitDocument, String> converter;

	private DTOService<DataUnitDTO, String> service;

	private final TestObjectGenerator<DataUnitDTO> dtoGenerator =
			new TestDataUnitDTOGenerator();

	private final TestObjectGenerator<DataUnitDocument> entityGenerator =
			new TestDataUnitDocumentGenerator();

	@BeforeEach
	public void initService() {
		service = new DataUnitDTOService(dao, converter);
	}

	@Test
	void saveTest() {
		final DataUnitDTO dto = dtoGenerator.generateSingleObject();
		final DataUnitDocument document = entityGenerator.generateSingleObject();
		Mockito.when(converter.convert(dto)).thenReturn(document);
		Mockito.when(dao.save(document)).thenReturn(document);
		Mockito.when(converter.convertFull(document)).thenReturn(dto);

		final DataUnitDTO savedDTO = service.save(dto);
		Assertions.assertTrue(Objects.deepEquals(dto, savedDTO));
	}

	@Test
	void findByIdTest() {
		final DataUnitDocument document = entityGenerator.generateSingleObject();
		final String id = document.getId();
		Mockito.when(dao.findById(id)).thenReturn(Optional.of(document));

		final DataUnitDTO dto = dtoGenerator.generateSingleObject();
		Mockito.when(converter.convertFull(document)).thenReturn(dto);

		final Optional<DataUnitDTO> savedDTOOptional = service.findById(id);
		Assertions.assertTrue(savedDTOOptional.isPresent());
		Assertions.assertTrue(Objects.deepEquals(dto, savedDTOOptional.get()));
	}

	@Test
	void findByIdEmptyTest() {
		Mockito.when(dao.findById(INCORRECT_STRING_ID)).thenReturn(Optional.empty());

		final Optional<DataUnitDTO> savedDTOOptional = service.findById(INCORRECT_STRING_ID);
		Assertions.assertFalse(savedDTOOptional.isPresent());
	}

	@Test
	void findAllTest() {
		final List<DataUnitDocument> documents = entityGenerator.generateMultipleObjects();
		Mockito.when(dao.findAll()).thenReturn(documents);

		final List<DataUnitDTO> dtos = dtoGenerator.generateMultipleObjects();
		for (int i = 0; i < documents.size(); i++) {
			Mockito.when(converter.convertFull(documents.get(i))).thenReturn(dtos.get(i));
		}

		final List<DataUnitDTO> savedDTOs = service.findAll();
		Assertions.assertNotNull(savedDTOs);
		Assertions.assertFalse(savedDTOs.isEmpty());
		Assertions.assertTrue(Objects.deepEquals(dtos, savedDTOs));
	}

	@Test
	void findAllEmptyTest() {
		final List<DataUnitDocument> documents = new ArrayList<>();
		Mockito.when(dao.findAll()).thenReturn(documents);

		final List<DataUnitDTO> savedDTOs = service.findAll();
		Assertions.assertNotNull(savedDTOs);
		Assertions.assertTrue(savedDTOs.isEmpty());
	}

	@Test
	void deleteByIdTest() {
		final DataUnitDTO dto = dtoGenerator.generateSingleObject();
		final String id = dto.getId();
		service.deleteById(id);

		Mockito.verify(dao).deleteById(id);
	}

}
