package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.cache.Cache;
import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.dao.DataUnitSchemaDAO;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaDTOService;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaEntityGenerator;
import com.hurynovich.data_storage.utils.TestReflectionUtils;
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
	private DTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity, Long> converter;

	@Mock
	private Cache<Long, DataUnitSchemaDTO> cache;

	@Mock
	private PaginationParams params;

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
		final DataUnitSchemaDTO dto = dtoGenerator.generateSingleObject();
		final DataUnitSchemaEntity entity = entityGenerator.generateSingleObject();
		Mockito.when(converter.convert(dto)).thenReturn(entity);
		Mockito.when(dao.save(entity)).thenReturn(entity);
		Mockito.when(converter.convertFull(entity)).thenReturn(dto);

		final DataUnitSchemaDTO savedDTO = service.save(dto);
		Mockito.verify(cache).store(dto.getId(), dto);
		Assertions.assertTrue(Objects.deepEquals(dto, savedDTO));
	}

	@Test
	void findByIdNotInCacheTest() {
		final DataUnitSchemaEntity entity = entityGenerator.generateSingleObject();
		final Long id = entity.getId();
		Mockito.when(cache.contains(id)).thenReturn(false);
		Mockito.when(dao.findById(id)).thenReturn(Optional.of(entity));

		final DataUnitSchemaDTO dto = dtoGenerator.generateSingleObject();
		Mockito.when(converter.convertFull(entity)).thenReturn(dto);
		Mockito.when(cache.get(id)).thenReturn(Optional.of(dto));

		final Optional<DataUnitSchemaDTO> savedDTOOptional = service.findById(id);
		Mockito.verify(cache).store(id, dto);
		Assertions.assertTrue(savedDTOOptional.isPresent());
		Assertions.assertTrue(Objects.deepEquals(dto, savedDTOOptional.get()));
	}

	@Test
	void findByIdInCacheTest() {
		final DataUnitSchemaDTO dto = dtoGenerator.generateSingleObject();
		final Long id = dto.getId();
		Mockito.when(cache.contains(id)).thenReturn(true);
		Mockito.when(cache.get(id)).thenReturn(Optional.of(dto));

		final Optional<DataUnitSchemaDTO> savedDTOOptional = service.findById(id);
		Assertions.assertTrue(savedDTOOptional.isPresent());
		Assertions.assertTrue(Objects.deepEquals(dto, savedDTOOptional.get()));
	}

	@Test
	void findByIdEmptyTest() {
		Mockito.when(cache.contains(INCORRECT_LONG_ID)).thenReturn(false);
		Mockito.when(cache.get(INCORRECT_LONG_ID)).thenReturn(Optional.empty());
		Mockito.when(cache.get(INCORRECT_LONG_ID)).thenReturn(Optional.empty());

		final Optional<DataUnitSchemaDTO> savedDTOOptional = service.findById(INCORRECT_LONG_ID);
		Assertions.assertTrue(savedDTOOptional.isEmpty());
	}

	@Test
	void findAllTest() {
		final List<DataUnitSchemaEntity> entities = entityGenerator.generateMultipleObjects();
		Mockito.when(dao.findAll(params)).thenReturn(entities);

		final List<DataUnitSchemaDTO> dtos = dtoGenerator.generateMultipleObjects();
		for (int i = 0; i < entities.size(); i++) {
			final DataUnitSchemaDTO dto = dtos.get(i);
			TestReflectionUtils.setField(dto, "propertySchemas", new ArrayList<>());
			Mockito.when(converter.convertBase(entities.get(i))).thenReturn(dto);
		}

		final List<DataUnitSchemaDTO> savedDTOs = service.findAll(params);
		Assertions.assertNotNull(savedDTOs);
		Assertions.assertFalse(savedDTOs.isEmpty());
		Assertions.assertTrue(Objects.deepEquals(dtos, savedDTOs));
	}

	@Test
	void findAllEmptyTest() {
		final List<DataUnitSchemaEntity> entities = new ArrayList<>();
		Mockito.when(dao.findAll(params)).thenReturn(entities);

		final List<DataUnitSchemaDTO> savedDTOs = service.findAll(params);
		Assertions.assertNotNull(savedDTOs);
		Assertions.assertTrue(savedDTOs.isEmpty());
	}

	@Test
	void deleteByIdNotInCacheTest() {
		final DataUnitSchemaDTO dto = dtoGenerator.generateSingleObject();
		final Long id = dto.getId();
		Mockito.when(cache.contains(id)).thenReturn(false);
		service.deleteById(id);

		Mockito.verify(dao).deleteById(id);
	}

	@Test
	void deleteByIdInCacheTest() {
		final DataUnitSchemaDTO dto = dtoGenerator.generateSingleObject();
		final Long id = dto.getId();
		Mockito.when(cache.contains(id)).thenReturn(true);
		service.deleteById(id);

		Mockito.verify(dao).deleteById(id);
		Mockito.verify(cache).invalidate(id);
	}

	@Test
	void existsByNameTrueTest() {
		final DataUnitSchemaDTO dto = dtoGenerator.generateSingleObject();
		final String name = dto.getName();
		Mockito.when(dao.existsByName(name)).thenReturn(true);

		Assertions.assertTrue(service.existsByName(name));
	}

	@Test
	void existsByNameFalseTest() {
		final DataUnitSchemaDTO dto = dtoGenerator.generateSingleObject();
		final String name = dto.getName();
		Mockito.when(dao.existsByName(name)).thenReturn(false);

		Assertions.assertFalse(service.existsByName(name));
	}

	@Test
	void existsByNameAndNotIdTrueTest() {
		final DataUnitSchemaDTO dto = dtoGenerator.generateSingleObject();
		final String name = dto.getName();
		final Long id = dto.getId();
		Mockito.when(dao.existsByNameAndNotId(name, id)).thenReturn(true);

		Assertions.assertTrue(service.existsByNameAndNotId(name, id));
	}

	@Test
	void existsByNameAndNotIdFalseTest() {
		final DataUnitSchemaDTO dto = dtoGenerator.generateSingleObject();
		final String name = dto.getName();
		final Long id = dto.getId();
		Mockito.when(dao.existsByNameAndNotId(name, id)).thenReturn(false);

		Assertions.assertFalse(service.existsByNameAndNotId(name, id));
	}

}
