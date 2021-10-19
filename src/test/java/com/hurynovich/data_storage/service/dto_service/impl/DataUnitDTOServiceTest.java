package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.dao.DAO;
import com.hurynovich.data_storage.model.document.DataUnitDocument;
import com.hurynovich.data_storage.model.dto.DataUnitDTO;
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
	private DTOConverter<DataUnitDTO, DataUnitDocument> converter;

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
		final DataUnitDTO dataUnitDTO = dtoGenerator.generateSingleObject();
		final DataUnitDocument dataUnitDocument = entityGenerator.generateSingleObject();
		Mockito.when(converter.convertFromDTO(dataUnitDTO)).thenReturn(dataUnitDocument);
		Mockito.when(dao.save(dataUnitDocument)).thenReturn(dataUnitDocument);
		Mockito.when(converter.convertToDTO(dataUnitDocument)).thenReturn(dataUnitDTO);

		final DataUnitDTO savedDataUnitDTO = service.save(dataUnitDTO);
		Assertions.assertTrue(Objects.deepEquals(dataUnitDTO, savedDataUnitDTO));
	}

	@Test
	void findByIdTest() {
		final DataUnitDocument dataUnitDocument = entityGenerator.generateSingleObject();

		final String id = dataUnitDocument.getId();
		Mockito.when(dao.findById(id)).thenReturn(Optional.of(dataUnitDocument));

		final DataUnitDTO dataUnitDTO = dtoGenerator.generateSingleObject();
		Mockito.when(converter.convertToDTO(dataUnitDocument)).thenReturn(dataUnitDTO);

		final Optional<DataUnitDTO> savedDataUnitDTOOptional = service.findById(id);
		Assertions.assertTrue(savedDataUnitDTOOptional.isPresent());

		Assertions.assertTrue(Objects.deepEquals(dataUnitDTO, savedDataUnitDTOOptional.get()));
	}

	@Test
	void findByIdEmptyTest() {
		Mockito.when(dao.findById(INCORRECT_STRING_ID)).thenReturn(Optional.empty());

		final Optional<DataUnitDTO> savedDataUnitDTOOptional = service.findById(INCORRECT_STRING_ID);
		Assertions.assertFalse(savedDataUnitDTOOptional.isPresent());
	}

	@Test
	void findAllTest() {
		final List<DataUnitDocument> dataUnitDocuments = entityGenerator.generateMultipleObjects();
		Mockito.when(dao.findAll()).thenReturn(dataUnitDocuments);

		final List<DataUnitDTO> dataUnitDTOs = dtoGenerator.generateMultipleObjects();
		Mockito.when(converter.convertAllToDTOs(dataUnitDocuments)).thenReturn(dataUnitDTOs);

		final List<DataUnitDTO> savedDataUnitDTOs = service.findAll();
		Assertions.assertNotNull(savedDataUnitDTOs);
		Assertions.assertFalse(savedDataUnitDTOs.isEmpty());
		Assertions.assertTrue(Objects.deepEquals(dataUnitDTOs, savedDataUnitDTOs));
	}

	@Test
	void findAllEmptyTest() {
		final List<DataUnitDocument> dataUnitDocuments = new ArrayList<>();
		Mockito.when(dao.findAll()).thenReturn(dataUnitDocuments);
		Mockito.when(converter.convertAllToDTOs(dataUnitDocuments)).thenReturn(new ArrayList<>());

		final List<DataUnitDTO> savedDataUnitDTOs = service.findAll();
		Assertions.assertNotNull(savedDataUnitDTOs);
		Assertions.assertTrue(savedDataUnitDTOs.isEmpty());
	}

	@Test
	void deleteByIdTest() {
		final DataUnitDTO dataUnitDTO = dtoGenerator.generateSingleObject();
		final String id = dataUnitDTO.getId();
		service.deleteById(id);

		Mockito.verify(dao).deleteById(id);
	}

}
