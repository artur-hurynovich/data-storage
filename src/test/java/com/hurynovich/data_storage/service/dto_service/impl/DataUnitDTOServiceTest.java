package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.dao.BaseDAO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.service.dto_service.BaseDTOService;
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

import java.util.Objects;
import java.util.Optional;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.INCORRECT_STRING_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitDTOServiceTest {

	@Mock
	private BaseDAO<DataUnitDocument, String> dao;

	@Mock
	private DTOConverter<DataUnitDTO, DataUnitDocument, String> converter;

	private BaseDTOService<DataUnitDTO, String> service;

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
	void deleteByIdTest() {
		final DataUnitDTO dto = dtoGenerator.generateSingleObject();
		final String id = dto.getId();
		service.deleteById(id);

		Mockito.verify(dao).deleteById(id);
	}

}
