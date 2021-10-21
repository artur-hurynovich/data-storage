package com.hurynovich.data_storage.dao.impl;

import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
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

	private final TestObjectGenerator<DataUnitSchemaEntity> schemaGenerator =
			new TestDataUnitSchemaEntityGenerator();

	@BeforeEach
	public void initDAO() {
		dao = new DataUnitSchemaDAOImpl(repository);
	}

	@Test
	void saveTest() {
		final DataUnitSchemaEntity schema = schemaGenerator.generateSingleObject();
		Mockito.when(repository.save(schema)).thenReturn(schema);

		final DataUnitSchemaEntity savedSchema = dao.save(schema);
		Assertions.assertTrue(Objects.deepEquals(schema, savedSchema));
	}

	@Test
	void findByIdTest() {
		final DataUnitSchemaEntity schema = schemaGenerator.generateSingleObject();
		final Long id = schema.getId();
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(schema));

		final Optional<DataUnitSchemaEntity> savedSchemaOptional = dao.findById(id);
		Assertions.assertTrue(savedSchemaOptional.isPresent());
		Assertions.assertTrue(Objects.deepEquals(schema, savedSchemaOptional.get()));
	}

	@Test
	void findByIdEmptyTest() {
		Mockito.when(dao.findById(INCORRECT_LONG_ID)).thenReturn(Optional.empty());

		final Optional<DataUnitSchemaEntity> savedSchemaOptional = dao.findById(INCORRECT_LONG_ID);
		Assertions.assertFalse(savedSchemaOptional.isPresent());
	}

	@Test
	void findAllTest() {
		final List<DataUnitSchemaEntity> schemas = schemaGenerator.generateMultipleObjects();
		Mockito.when(dao.findAll()).thenReturn(schemas);

		final List<DataUnitSchemaEntity> savedSchemas = dao.findAll();
		Assertions.assertNotNull(savedSchemas);
		Assertions.assertFalse(savedSchemas.isEmpty());
		Assertions.assertTrue(Objects.deepEquals(schemas, savedSchemas));
	}

	@Test
	void findAllEmptyTest() {
		final List<DataUnitSchemaEntity> schemas = new ArrayList<>();
		Mockito.when(dao.findAll()).thenReturn(schemas);

		final List<DataUnitSchemaEntity> savedSchemas = dao.findAll();
		Assertions.assertNotNull(savedSchemas);
		Assertions.assertTrue(savedSchemas.isEmpty());
	}

	@Test
	void deleteByIdTest() {
		final DataUnitSchemaEntity schema = schemaGenerator.generateSingleObject();
		final Long id = schema.getId();
		dao.deleteById(id);

		Mockito.verify(repository).deleteById(id);
	}

	@Test
	void existsByNameTrueTest() {
		final DataUnitSchemaEntity schema = schemaGenerator.generateSingleObject();
		final String name = schema.getName();
		Mockito.when(repository.existsByName(name)).thenReturn(true);

		Assertions.assertTrue(dao.existsByName(name));
	}

	@Test
	void existsByNameFalseTest() {
		final DataUnitSchemaEntity schema = schemaGenerator.generateSingleObject();
		final String name = schema.getName();
		Mockito.when(repository.existsByName(name)).thenReturn(false);

		Assertions.assertFalse(dao.existsByName(name));
	}

	@Test
	void existsByNameAndNotIdTrueTest() {
		final DataUnitSchemaEntity schema = schemaGenerator.generateSingleObject();
		final String name = schema.getName();
		final Long id = schema.getId();
		Mockito.when(repository.existsByNameAndIdNot(name, id)).thenReturn(true);

		Assertions.assertTrue(dao.existsByNameAndNotId(name, id));
	}

	@Test
	void existsByNameAndNotIdFalseTest() {
		final DataUnitSchemaEntity schemaEntity = schemaGenerator.generateSingleObject();
		final String name = schemaEntity.getName();
		final Long id = schemaEntity.getId();
		Mockito.when(repository.existsByNameAndIdNot(name, id)).thenReturn(false);

		Assertions.assertFalse(dao.existsByNameAndNotId(name, id));
	}

}
