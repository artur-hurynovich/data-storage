package com.hurynovich.data_storage.dao.impl;

import com.hurynovich.data_storage.dao.DAO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDocumentGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.INCORRECT_STRING_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitDAOTest {

	@Mock
	private MongoRepository<DataUnitDocument, String> repository;

	private DAO<DataUnitDocument, String> dao;

	private final TestObjectGenerator<DataUnitDocument> dataUnitGenerator =
			new TestDataUnitDocumentGenerator();

	@BeforeEach
	public void initDAO() {
		dao = new DataUnitDAO(repository);
	}

	@Test
	void saveTest() {
		final DataUnitDocument dataUnit = dataUnitGenerator.generateSingleObject();
		Mockito.when(repository.save(dataUnit)).thenReturn(dataUnit);

		final DataUnitDocument savedDataUnit = dao.save(dataUnit);
		Assertions.assertTrue(Objects.deepEquals(dataUnit, savedDataUnit));
	}

	@Test
	void findByIdTest() {
		final DataUnitDocument dataUnit = dataUnitGenerator.generateSingleObject();
		final String id = dataUnit.getId();
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(dataUnit));

		final Optional<DataUnitDocument> savedDataUnitOptional = dao.findById(id);
		Assertions.assertTrue(savedDataUnitOptional.isPresent());
		Assertions.assertTrue(Objects.deepEquals(dataUnit, savedDataUnitOptional.get()));
	}

	@Test
	void findByIdEmptyTest() {
		Mockito.when(dao.findById(INCORRECT_STRING_ID)).thenReturn(Optional.empty());

		final Optional<DataUnitDocument> savedDataUnitOptional = dao.findById(INCORRECT_STRING_ID);
		Assertions.assertFalse(savedDataUnitOptional.isPresent());
	}

	@Test
	void findAllTest() {
		final List<DataUnitDocument> dataUnits = dataUnitGenerator.generateMultipleObjects();
		Mockito.when(dao.findAll()).thenReturn(dataUnits);

		final List<DataUnitDocument> savedDataUnits = dao.findAll();
		Assertions.assertNotNull(savedDataUnits);
		Assertions.assertFalse(savedDataUnits.isEmpty());
		Assertions.assertTrue(Objects.deepEquals(dataUnits, savedDataUnits));
	}

	@Test
	void findAllEmptyTest() {
		final List<DataUnitDocument> dataUnits = new ArrayList<>();
		Mockito.when(dao.findAll()).thenReturn(dataUnits);

		final List<DataUnitDocument> savedDataUnits = dao.findAll();
		Assertions.assertNotNull(savedDataUnits);
		Assertions.assertTrue(savedDataUnits.isEmpty());
	}

	@Test
	void deleteByIdTest() {
		final DataUnitDocument dataUnit = dataUnitGenerator.generateSingleObject();
		final String id = dataUnit.getId();
		dao.deleteById(id);

		Mockito.verify(repository).deleteById(id);
	}

}
