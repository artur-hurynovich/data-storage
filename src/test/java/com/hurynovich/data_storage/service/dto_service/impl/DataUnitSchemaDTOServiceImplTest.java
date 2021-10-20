package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.cache.Cache;
import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.dao.DataUnitSchemaDAO;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaDTOService;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
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
class DataUnitSchemaDTOServiceImplTest {

	@Mock
	private DataUnitSchemaDAO dao;

	@Mock
	private DTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity> converter;

	@Mock
	private Cache<Long, DataUnitSchemaDTO> cache;

	private DataUnitSchemaDTOService service;

	private final TestObjectGenerator<DataUnitSchemaDTO> dtoGenerator =
			new TestDataUnitSchemaDTOGenerator();

	private final TestObjectGenerator<DataUnitSchemaEntity> entityGenerator =
			new TestDataUnitSchemaEntityGenerator();

	@BeforeEach
	public void initService() {
		service = new DataUnitSchemaDTOServiceImpl(dao, converter, cache);
	}

	@Test
	void saveTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final DataUnitSchemaEntity schemaEntity = entityGenerator.generateSingleObject();
		Mockito.when(converter.convertFromDTO(schemaDTO)).thenReturn(schemaEntity);
		Mockito.when(dao.save(schemaEntity)).thenReturn(schemaEntity);
		Mockito.when(converter.convertToDTO(schemaEntity)).thenReturn(schemaDTO);

		final DataUnitSchemaDTO savedSchemaDTO = service.save(schemaDTO);
		Mockito.verify(cache).store(schemaDTO.getId(), schemaDTO);
		Assertions.assertTrue(Objects.deepEquals(schemaDTO, savedSchemaDTO));
	}

	@Test
	void findByIdNotInCacheTest() {
		final DataUnitSchemaEntity schemaEntity = entityGenerator.generateSingleObject();
		final Long id = schemaEntity.getId();
		Mockito.when(cache.contains(id)).thenReturn(false);
		Mockito.when(dao.findById(id)).thenReturn(Optional.of(schemaEntity));

		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		Mockito.when(converter.convertToDTO(schemaEntity)).thenReturn(schemaDTO);
		Mockito.when(cache.get(id)).thenReturn(Optional.of(schemaDTO));

		final Optional<DataUnitSchemaDTO> savedSchemaDTOOptional = service.findById(id);
		Mockito.verify(cache).store(id, schemaDTO);
		Assertions.assertTrue(savedSchemaDTOOptional.isPresent());

		Assertions.assertTrue(Objects.deepEquals(schemaDTO, savedSchemaDTOOptional.get()));
	}

	@Test
	void findByIdInCacheTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final Long id = schemaDTO.getId();
		Mockito.when(cache.contains(id)).thenReturn(true);
		Mockito.when(cache.get(id)).thenReturn(Optional.of(schemaDTO));

		final Optional<DataUnitSchemaDTO> savedSchemaDTOOptional = service.findById(id);
		Assertions.assertTrue(savedSchemaDTOOptional.isPresent());
		Assertions.assertTrue(Objects.deepEquals(schemaDTO, savedSchemaDTOOptional.get()));
	}

	@Test
	void findByIdEmptyTest() {
		Mockito.when(cache.contains(INCORRECT_LONG_ID)).thenReturn(false);
		Mockito.when(cache.get(INCORRECT_LONG_ID)).thenReturn(Optional.empty());
		Mockito.when(cache.get(INCORRECT_LONG_ID)).thenReturn(Optional.empty());

		final Optional<DataUnitSchemaDTO> savedSchemaDTOOptional = service.findById(INCORRECT_LONG_ID);
		Assertions.assertTrue(savedSchemaDTOOptional.isEmpty());
	}

	@Test
	void findAllTest() {
		final List<DataUnitSchemaEntity> schemaEntities = entityGenerator.generateMultipleObjects();
		Mockito.when(dao.findAll()).thenReturn(schemaEntities);

		final List<DataUnitSchemaDTO> schemaDTOs = dtoGenerator.generateMultipleObjects();
		Mockito.when(converter.convertAllToDTOs(schemaEntities)).thenReturn(schemaDTOs);

		final List<DataUnitSchemaDTO> savedSchemaDTOs = service.findAll();
		Assertions.assertNotNull(savedSchemaDTOs);
		Assertions.assertFalse(savedSchemaDTOs.isEmpty());
		Assertions.assertTrue(Objects.deepEquals(schemaDTOs, savedSchemaDTOs));
	}

	@Test
	void findAllEmptyTest() {
		final List<DataUnitSchemaEntity> schemaEntities = new ArrayList<>();
		Mockito.when(dao.findAll()).thenReturn(schemaEntities);
		Mockito.when(converter.convertAllToDTOs(schemaEntities)).thenReturn(new ArrayList<>());

		final List<DataUnitSchemaDTO> savedSchemaDTOs = service.findAll();
		Assertions.assertNotNull(savedSchemaDTOs);
		Assertions.assertTrue(savedSchemaDTOs.isEmpty());
	}

	@Test
	void deleteByIdNotInCacheTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final Long id = schemaDTO.getId();
		Mockito.when(cache.contains(id)).thenReturn(false);
		service.deleteById(id);

		Mockito.verify(dao).deleteById(id);
	}

	@Test
	void deleteByIdInCacheTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final Long id = schemaDTO.getId();
		Mockito.when(cache.contains(id)).thenReturn(true);
		service.deleteById(id);

		Mockito.verify(dao).deleteById(id);
		Mockito.verify(cache).invalidate(id);
	}

	@Test
	void existsByNameTrueTest() {
		final DataUnitSchemaDTO dataUnitSchema = dtoGenerator.generateSingleObject();
		final String name = dataUnitSchema.getName();
		Mockito.when(dao.existsByName(name)).thenReturn(true);

		Assertions.assertTrue(service.existsByName(name));
	}

	@Test
	void existsByNameFalseTest() {
		final DataUnitSchemaDTO dataUnitSchema = dtoGenerator.generateSingleObject();
		final String name = dataUnitSchema.getName();
		Mockito.when(dao.existsByName(name)).thenReturn(false);

		Assertions.assertFalse(service.existsByName(name));
	}

	@Test
	void existsByNameAndNotIdTrueTest() {
		final DataUnitSchemaDTO dataUnitSchema = dtoGenerator.generateSingleObject();
		final String name = dataUnitSchema.getName();
		final Long id = dataUnitSchema.getId();
		Mockito.when(dao.existsByNameAndNotId(name, id)).thenReturn(true);

		Assertions.assertTrue(service.existsByNameAndNotId(name, id));
	}

	@Test
	void existsByNameAndNotIdFalseTest() {
		final DataUnitSchemaDTO dataUnitSchema = dtoGenerator.generateSingleObject();
		final String name = dataUnitSchema.getName();
		final Long id = dataUnitSchema.getId();
		Mockito.when(dao.existsByNameAndNotId(name, id)).thenReturn(false);

		Assertions.assertFalse(service.existsByNameAndNotId(name, id));
	}

}
