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

import java.util.Objects;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaServiceTest {

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

}
