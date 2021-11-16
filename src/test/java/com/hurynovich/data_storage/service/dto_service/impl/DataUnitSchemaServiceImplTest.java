package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.cache.Cache;
import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.dao.DataUnitSchemaDAO;
import com.hurynovich.data_storage.event.EventListener;
import com.hurynovich.data_storage.event.model.Event;
import com.hurynovich.data_storage.event.model.EventType;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaService;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaEntityGenerator;
import com.hurynovich.data_storage.utils.TestReflectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.INCORRECT_LONG_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaServiceImplTest {

	@Mock
	private DataUnitSchemaDAO dao;

	@Mock
	private Converter<DataUnitSchemaDTO, DataUnitSchemaEntity, Long> converter;

	@Mock
	private Cache<Long, DataUnitSchemaDTO> cache;

	@Mock
	private PaginationParams params;

	@Mock
	private EventListener<DataUnitSchemaDTO> eventListener;

	private DataUnitSchemaService service;

	private final TestObjectGenerator<DataUnitSchemaDTO> dtoGenerator =
			new TestDataUnitSchemaDTOGenerator();

	private final TestObjectGenerator<DataUnitSchemaEntity> entityGenerator =
			new TestDataUnitSchemaEntityGenerator();

	@BeforeEach
	public void initService() {
		service = new DataUnitSchemaServiceImpl(dao, converter, cache, eventListener);
	}

	@Test
	void saveTest() {
		final DataUnitSchemaDTO dto = dtoGenerator.generateSingleObject();
		final DataUnitSchemaEntity entity = entityGenerator.generateSingleObject();
		Mockito.when(converter.convert(dto)).thenReturn(entity);
		Mockito.when(dao.save(entity)).thenReturn(entity);
		Mockito.when(converter.convert(entity)).thenReturn(dto);

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
		Mockito.when(converter.convert(entity)).thenReturn(dto);
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
			TestReflectionUtils.setField(dto, DataUnitSchemaEntity_.PROPERTY_SCHEMAS, new ArrayList<>());
			Mockito.when(converter.convert(entities.get(i), DataUnitSchemaEntity_.PROPERTY_SCHEMAS)).thenReturn(dto);
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
	void deleteByIdNotInCacheSuccessTest() {
		final DataUnitSchemaEntity entity = entityGenerator.generateSingleObject();
		final Long id = entity.getId();
		Mockito.when(dao.findById(id)).thenReturn(Optional.of(entity));

		final DataUnitSchemaDTO dto = dtoGenerator.generateSingleObject();
		Mockito.when(converter.convert(entity)).thenReturn(dto);
		Mockito.when(cache.contains(id)).thenReturn(false);
		service.deleteById(id);

		Mockito.verify(dao).delete(entity);
		Mockito.verify(eventListener).onEvent(
				Mockito.argThat(new EventArgumentMatcher(new Event<>(dto, EventType.DELETE))));
	}

	@Test
	void deleteByIdInCacheSuccessTest() {
		final DataUnitSchemaEntity entity = entityGenerator.generateSingleObject();
		final Long id = entity.getId();
		Mockito.when(dao.findById(id)).thenReturn(Optional.of(entity));

		final DataUnitSchemaDTO dto = dtoGenerator.generateSingleObject();
		Mockito.when(converter.convert(entity)).thenReturn(dto);
		Mockito.when(cache.contains(id)).thenReturn(true);
		service.deleteById(id);

		Mockito.verify(dao).delete(entity);
		Mockito.verify(cache).invalidate(id);
		Mockito.verify(eventListener).onEvent(
				Mockito.argThat(new EventArgumentMatcher(new Event<>(dto, EventType.DELETE))));
	}

	@Test
	void deleteByIdNotFoundTest() {
		Mockito.when(dao.findById(INCORRECT_LONG_ID)).thenReturn(Optional.empty());

		Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteById(INCORRECT_LONG_ID),
				"'DataUnitSchemaEntity' with id = '" + INCORRECT_LONG_ID + "' not found");
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

	private static class EventArgumentMatcher implements ArgumentMatcher<Event<DataUnitSchemaDTO>> {

		private final Event<DataUnitSchemaDTO> wanted;

		private EventArgumentMatcher(final Event<DataUnitSchemaDTO> wanted) {
			this.wanted = wanted;
		}

		@Override
		public boolean matches(final Event<DataUnitSchemaDTO> actual) {
			return EqualsBuilder.reflectionEquals(wanted, actual);
		}

	}

}
