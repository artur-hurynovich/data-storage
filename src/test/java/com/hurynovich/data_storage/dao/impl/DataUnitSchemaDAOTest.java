package com.hurynovich.data_storage.dao.impl;

import com.hurynovich.data_storage.dao.DAO;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaEntityGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaDAOTest {

	private final Long incorrectId = 1L;

	@Mock
	private JpaRepository<DataUnitSchemaEntity, Long> repository;

	private DAO<DataUnitSchemaEntity, Long> dao;

	private final TestObjectGenerator<DataUnitSchemaEntity> entityGenerator =
			new TestDataUnitSchemaEntityGenerator();

	@BeforeEach
	public void initDAO() {
		dao = new DataUnitSchemaDAO(repository);
	}

	@Test
	void saveTest() {
		final DataUnitSchemaEntity schemaEntity = entityGenerator.generateSingleObject();
		Mockito.when(repository.save(schemaEntity)).thenReturn(schemaEntity);

		final DataUnitSchemaEntity savedSchemaEntity = dao.save(schemaEntity);
		Assertions.assertTrue(Objects.deepEquals(schemaEntity, savedSchemaEntity));
	}

	@Test
	void findByIdTest() {
		final DataUnitSchemaEntity schemaEntity = entityGenerator.generateSingleObject();

		final Long id = schemaEntity.getId();
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(schemaEntity));

		final Optional<DataUnitSchemaEntity> savedSchemaEntityOptional = dao.findById(id);
		Assertions.assertTrue(savedSchemaEntityOptional.isPresent());

		Assertions.assertTrue(Objects.deepEquals(schemaEntity, savedSchemaEntityOptional.get()));
	}

	@Test
	void findByIdEmptyTest() {
		Mockito.when(dao.findById(incorrectId)).thenReturn(Optional.empty());

		final Optional<DataUnitSchemaEntity> savedSchemaEntityOptional = dao.findById(incorrectId);
		Assertions.assertFalse(savedSchemaEntityOptional.isPresent());
	}

	@Test
	void findAllTest() {
		final List<DataUnitSchemaEntity> schemaEntities = entityGenerator.generateMultipleObjects();
		Mockito.when(dao.findAll()).thenReturn(schemaEntities);

		final List<DataUnitSchemaEntity> savedSchemaEntities = dao.findAll();
		Assertions.assertNotNull(savedSchemaEntities);
		Assertions.assertFalse(savedSchemaEntities.isEmpty());
		Assertions.assertTrue(Objects.deepEquals(schemaEntities, savedSchemaEntities));
	}

	@Test
	void findAllEmptyTest() {
		final List<DataUnitSchemaEntity> schemaEntities = new ArrayList<>();
		Mockito.when(dao.findAll()).thenReturn(schemaEntities);

		final List<DataUnitSchemaEntity> savedSchemaEntities = dao.findAll();
		Assertions.assertNotNull(savedSchemaEntities);
		Assertions.assertTrue(savedSchemaEntities.isEmpty());
	}

	@Test
	void deleteByIdTest() {
		final DataUnitSchemaEntity schemaEntity = entityGenerator.generateSingleObject();
		final Long id = schemaEntity.getId();
		dao.deleteById(id);

		Mockito.verify(repository).deleteById(id);
	}

}
