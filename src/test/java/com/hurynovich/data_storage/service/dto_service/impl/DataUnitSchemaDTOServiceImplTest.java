package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.dao.DataUnitSchemaDAO;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import com.hurynovich.data_storage.service.dto_service.DTOService;
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

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaDTOServiceImplTest {

	private final Long incorrectId = 1L;

	@Mock
	private DataUnitSchemaDAO dao;

	@Mock
	private DTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity> converter;

	private DTOService<DataUnitSchemaDTO, Long> service;

	private final TestObjectGenerator<DataUnitSchemaDTO> dtoGenerator =
			new TestDataUnitSchemaDTOGenerator();

	private final TestObjectGenerator<DataUnitSchemaEntity> entityGenerator =
			new TestDataUnitSchemaEntityGenerator();

	@BeforeEach
	public void initService() {
		service = new DataUnitSchemaDTOServiceImpl(dao, converter);
	}

	@Test
	void saveTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final DataUnitSchemaEntity schemaEntity = entityGenerator.generateSingleObject();
		Mockito.when(converter.convertFromDTO(schemaDTO)).thenReturn(schemaEntity);
		Mockito.when(dao.save(schemaEntity)).thenReturn(schemaEntity);
		Mockito.when(converter.convertToDTO(schemaEntity)).thenReturn(schemaDTO);

		final DataUnitSchemaDTO savedSchemaDTO = service.save(schemaDTO);
		Assertions.assertTrue(Objects.deepEquals(schemaDTO, savedSchemaDTO));
	}

	@Test
	void findByIdTest() {
		final DataUnitSchemaEntity schemaEntity = entityGenerator.generateSingleObject();

		final Long id = schemaEntity.getId();
		Mockito.when(dao.findById(id)).thenReturn(Optional.of(schemaEntity));

		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		Mockito.when(converter.convertToDTO(schemaEntity)).thenReturn(schemaDTO);

		final Optional<DataUnitSchemaDTO> savedSchemaDTOOptional = service.findById(id);
		Assertions.assertTrue(savedSchemaDTOOptional.isPresent());

		Assertions.assertTrue(Objects.deepEquals(schemaDTO, savedSchemaDTOOptional.get()));
	}

	@Test
	void findByIdEmptyTest() {
		Mockito.when(dao.findById(incorrectId)).thenReturn(Optional.empty());

		final Optional<DataUnitSchemaDTO> savedSchemaDTOOptional = service.findById(incorrectId);
		Assertions.assertFalse(savedSchemaDTOOptional.isPresent());
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
	void deleteByIdTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final Long id = schemaDTO.getId();
		service.deleteById(id);

		Mockito.verify(dao).deleteById(id);
	}

	@Test
	void existsByIdTrueTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final Long id = schemaDTO.getId();
		Mockito.when(dao.existsById(id)).thenReturn(true);
		Assertions.assertTrue(dao.existsById(id));
	}

	@Test
	void existsByIdTrueFalse() {
		Mockito.when(dao.existsById(incorrectId)).thenReturn(false);
		Assertions.assertFalse(dao.existsById(incorrectId));
	}

}
