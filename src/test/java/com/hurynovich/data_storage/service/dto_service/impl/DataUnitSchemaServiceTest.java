package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
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
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaServiceTest {

	private final Long incorrectId = 1L;

	@Mock
	private JpaRepository<DataUnitSchemaEntity, Long> repository;

	@Mock
	private DTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity> converter;

	private DataUnitSchemaService service;

	private final TestObjectGenerator<DataUnitSchemaDTO> dtoGenerator =
			new TestDataUnitSchemaDTOGenerator();

	private final TestObjectGenerator<DataUnitSchemaEntity> entityGenerator =
			new TestDataUnitSchemaEntityGenerator();

	@BeforeEach
	public void initService() {
		service = new DataUnitSchemaService(repository, converter);
	}

	@Test
	void saveTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final DataUnitSchemaEntity schemaEntity = entityGenerator.generateSingleObject();
		Mockito.when(converter.convertFromDTO(schemaDTO)).thenReturn(schemaEntity);
		Mockito.when(repository.save(schemaEntity)).thenReturn(schemaEntity);
		Mockito.when(converter.convertToDTO(schemaEntity)).thenReturn(schemaDTO);

		final DataUnitSchemaDTO savedSchemaDTO = service.save(schemaDTO);
		Assertions.assertTrue(Objects.deepEquals(schemaDTO, savedSchemaDTO));
	}

	@Test
	void findByIdSuccessTest() {
		final DataUnitSchemaEntity schemaEntity = entityGenerator.generateSingleObject();

		final Long id = schemaEntity.getId();
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(schemaEntity));

		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		Mockito.when(converter.convertToDTO(schemaEntity)).thenReturn(schemaDTO);

		final Optional<DataUnitSchemaDTO> savedSchemaDTOOptional = service.findById(id);
		Assertions.assertTrue(savedSchemaDTOOptional.isPresent());

		Assertions.assertTrue(Objects.deepEquals(schemaDTO, savedSchemaDTOOptional.get()));
	}

	@Test
	void findByIdFailureTest() {
		Mockito.when(repository.findById(incorrectId)).thenReturn(Optional.empty());

		final Optional<DataUnitSchemaDTO> savedSchemaDTOOptional = service.findById(incorrectId);
		Assertions.assertFalse(savedSchemaDTOOptional.isPresent());
	}

	@Test
	void findAllSuccessTest() {
		final List<DataUnitSchemaEntity> schemaEntities = entityGenerator.generateMultipleObjects();
		Mockito.when(repository.findAll()).thenReturn(schemaEntities);

		final List<DataUnitSchemaDTO> schemaDTOs = dtoGenerator.generateMultipleObjects();
		Mockito.when(converter.convertAllToDTOs(schemaEntities)).thenReturn(schemaDTOs);

		final List<DataUnitSchemaDTO> savedSchemaDTOs = service.findAll();
		Assertions.assertNotNull(savedSchemaDTOs);
		Assertions.assertFalse(savedSchemaDTOs.isEmpty());
		Assertions.assertTrue(Objects.deepEquals(schemaDTOs, savedSchemaDTOs));
	}

	@Test
	void findAllFailureTest() {
		final ArrayList<DataUnitSchemaEntity> schemaEntities = new ArrayList<>();
		Mockito.when(repository.findAll()).thenReturn(schemaEntities);
		Mockito.when(converter.convertAllToDTOs(schemaEntities)).thenReturn(new ArrayList<>());

		final List<DataUnitSchemaDTO> savedSchemaDTOs = service.findAll();
		Assertions.assertNotNull(savedSchemaDTOs);
		Assertions.assertTrue(savedSchemaDTOs.isEmpty());
	}

}
