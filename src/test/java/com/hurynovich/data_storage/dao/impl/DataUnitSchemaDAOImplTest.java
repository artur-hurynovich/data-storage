package com.hurynovich.data_storage.dao.impl;

import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import com.hurynovich.data_storage.repository.DataUnitSchemaRepository;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaEntityGenerator;
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

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.INCORRECT_LONG_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaDAOImplTest {

	@Mock
	private DataUnitSchemaRepository repository;

	private DataUnitSchemaDAOImpl dao;

	private final TestObjectGenerator<DataUnitSchemaEntity> entityGenerator =
			new TestDataUnitSchemaEntityGenerator();

	@BeforeEach
	public void initDAO() {
		dao = new DataUnitSchemaDAOImpl(repository);
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
		Mockito.when(dao.findById(INCORRECT_LONG_ID)).thenReturn(Optional.empty());

		final Optional<DataUnitSchemaEntity> savedSchemaEntityOptional = dao.findById(INCORRECT_LONG_ID);
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

	@Test
	void existsByNameTrueTest() {
		final DataUnitSchemaEntity schemaEntity = entityGenerator.generateSingleObject();
		final String name = schemaEntity.getName();
		Mockito.when(repository.existsByName(name)).thenReturn(true);

		Assertions.assertTrue(dao.existsByName(name));
	}

	@Test
	void existsByNameFalseTest() {
		final DataUnitSchemaEntity schemaEntity = entityGenerator.generateSingleObject();
		final String name = schemaEntity.getName();
		Mockito.when(repository.existsByName(name)).thenReturn(false);

		Assertions.assertFalse(dao.existsByName(name));
	}

	@Test
	void existsByNameAndNotIdTrueTest() {
		final DataUnitSchemaEntity schemaEntity = entityGenerator.generateSingleObject();
		final String name = schemaEntity.getName();
		final Long id = schemaEntity.getId();
		Mockito.when(repository.existsByNameAndIdNot(name, id)).thenReturn(true);

		Assertions.assertTrue(dao.existsByNameAndNotId(name, id));
	}

	@Test
	void existsByNameAndNotIdFalseTest() {
		final DataUnitSchemaEntity schemaEntity = entityGenerator.generateSingleObject();
		final String name = schemaEntity.getName();
		final Long id = schemaEntity.getId();
		Mockito.when(repository.existsByNameAndIdNot(name, id)).thenReturn(false);

		Assertions.assertFalse(dao.existsByNameAndNotId(name, id));
	}

}
