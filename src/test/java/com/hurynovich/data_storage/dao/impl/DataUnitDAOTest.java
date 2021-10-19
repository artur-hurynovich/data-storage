package com.hurynovich.data_storage.dao.impl;

import com.hurynovich.data_storage.dao.DAO;
import com.hurynovich.data_storage.model.document.DataUnitDocument;
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

	private final TestObjectGenerator<DataUnitDocument> documentGenerator =
			new TestDataUnitDocumentGenerator();

	@BeforeEach
	public void initDAO() {
		dao = new DataUnitDAO(repository);
	}

	@Test
	void saveTest() {
		final DataUnitDocument schemaDocument = documentGenerator.generateSingleObject();
		Mockito.when(repository.save(schemaDocument)).thenReturn(schemaDocument);

		final DataUnitDocument savedSchemaDocument = dao.save(schemaDocument);
		Assertions.assertTrue(Objects.deepEquals(schemaDocument, savedSchemaDocument));
	}

	@Test
	void findByIdTest() {
		final DataUnitDocument schemaDocument = documentGenerator.generateSingleObject();

		final String id = schemaDocument.getId();
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(schemaDocument));

		final Optional<DataUnitDocument> savedSchemaDocumentOptional = dao.findById(id);
		Assertions.assertTrue(savedSchemaDocumentOptional.isPresent());

		Assertions.assertTrue(Objects.deepEquals(schemaDocument, savedSchemaDocumentOptional.get()));
	}

	@Test
	void findByIdEmptyTest() {
		Mockito.when(dao.findById(INCORRECT_STRING_ID)).thenReturn(Optional.empty());

		final Optional<DataUnitDocument> savedSchemaDocumentOptional = dao.findById(INCORRECT_STRING_ID);
		Assertions.assertFalse(savedSchemaDocumentOptional.isPresent());
	}

	@Test
	void findAllTest() {
		final List<DataUnitDocument> schemaDocuments = documentGenerator.generateMultipleObjects();
		Mockito.when(dao.findAll()).thenReturn(schemaDocuments);

		final List<DataUnitDocument> savedSchemaDocuments = dao.findAll();
		Assertions.assertNotNull(savedSchemaDocuments);
		Assertions.assertFalse(savedSchemaDocuments.isEmpty());
		Assertions.assertTrue(Objects.deepEquals(schemaDocuments, savedSchemaDocuments));
	}

	@Test
	void findAllEmptyTest() {
		final List<DataUnitDocument> schemaDocuments = new ArrayList<>();
		Mockito.when(dao.findAll()).thenReturn(schemaDocuments);

		final List<DataUnitDocument> savedSchemaDocuments = dao.findAll();
		Assertions.assertNotNull(savedSchemaDocuments);
		Assertions.assertTrue(savedSchemaDocuments.isEmpty());
	}

	@Test
	void deleteByIdTest() {
		final DataUnitDocument schemaDocument = documentGenerator.generateSingleObject();
		final String id = schemaDocument.getId();
		dao.deleteById(id);

		Mockito.verify(repository).deleteById(id);
	}

}
