package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.cache.Cache;
import com.hurynovich.data_storage.converter.ServiceConverter;
import com.hurynovich.data_storage.dao.DataUnitSchemaDAO;
import com.hurynovich.data_storage.event.EventListener;
import com.hurynovich.data_storage.event.model.Event;
import com.hurynovich.data_storage.event.model.EventType;
import com.hurynovich.data_storage.model.AbstractServiceModel_;
import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelImpl_;
import com.hurynovich.data_storage.model_asserter.ModelAsserter;
import com.hurynovich.data_storage.model_asserter.impl.DataUnitSchemaAsserter;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaService;
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

import static com.hurynovich.data_storage.model.ModelConstants.INCORRECT_LONG_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaServiceImplTest {

	@Mock
	private DataUnitSchemaDAO dao;

	@Mock
	private ServiceConverter<DataUnitSchemaServiceModel, DataUnitSchemaPersistentModel> converter;

	@Mock
	private Cache<Long, DataUnitSchemaServiceModel> cache;

	@Mock
	private PaginationParams params;

	@Mock
	private EventListener<DataUnitSchemaServiceModel> eventListener;

	private DataUnitSchemaService service;

	private final ModelGenerator<DataUnitSchemaServiceModel> serviceModelGenerator =
			new DataUnitSchemaServiceModelGenerator();

	private final ModelGenerator<DataUnitSchemaPersistentModel> persistentModelGenerator =
			new DataUnitSchemaPersistentModelGenerator();

	private final ModelAsserter<DataUnitSchemaApiModel, DataUnitSchemaServiceModel, DataUnitSchemaPersistentModel> asserter =
			new DataUnitSchemaAsserter();

	@BeforeEach
	public void initService() {
		service = new DataUnitSchemaServiceImpl(dao, converter, cache, eventListener);
	}

	@Test
	void saveTest() {
		final DataUnitSchemaServiceModel serviceModel = serviceModelGenerator.generateNullId();
		final DataUnitSchemaPersistentModel persistentModel = persistentModelGenerator.generate();
		Mockito.when(converter.convert(serviceModel)).thenReturn(persistentModel);
		Mockito.when(dao.save(persistentModel)).thenReturn(persistentModel);
		Mockito.when(converter.convert(persistentModel)).thenReturn(serviceModelGenerator.generate());

		final DataUnitSchemaServiceModel savedServiceModel = service.save(serviceModel);
		Mockito.verify(cache).store(savedServiceModel.getId(), savedServiceModel);

		asserter.assertEquals(serviceModel, savedServiceModel, AbstractServiceModel_.ID);
		Assertions.assertNotNull(savedServiceModel.getId());
	}

	@Test
	void findByIdNotInCacheTest() {
		final DataUnitSchemaPersistentModel persistentModel = persistentModelGenerator.generate();
		final Long id = persistentModel.getId();
		Mockito.when(cache.contains(id)).thenReturn(false);
		Mockito.when(dao.findById(id)).thenReturn(Optional.of(persistentModel));

		final DataUnitSchemaServiceModel serviceModel = serviceModelGenerator.generate();
		Mockito.when(converter.convert(persistentModel)).thenReturn(serviceModel);
		Mockito.when(cache.get(id)).thenReturn(Optional.of(serviceModel));

		final Optional<DataUnitSchemaServiceModel> savedServiceModelOptional = service.findById(id);
		Mockito.verify(cache).store(id, serviceModel);
		Assertions.assertTrue(savedServiceModelOptional.isPresent());
		asserter.assertEquals(serviceModel, savedServiceModelOptional.get());
	}

	@Test
	void findByIdInCacheTest() {
		final DataUnitSchemaServiceModel serviceModel = serviceModelGenerator.generate();
		final Long id = serviceModel.getId();
		Mockito.when(cache.contains(id)).thenReturn(true);
		Mockito.when(cache.get(id)).thenReturn(Optional.of(serviceModel));

		final Optional<DataUnitSchemaServiceModel> savedServiceModelOptional = service.findById(id);
		Assertions.assertTrue(savedServiceModelOptional.isPresent());
		asserter.assertEquals(serviceModel, savedServiceModelOptional.get());
	}

	@Test
	void findByIdEmptyTest() {
		Mockito.when(cache.contains(INCORRECT_LONG_ID)).thenReturn(false);
		Mockito.when(cache.get(INCORRECT_LONG_ID)).thenReturn(Optional.empty());

		final Optional<DataUnitSchemaServiceModel> savedServiceModelOptional = service.findById(INCORRECT_LONG_ID);
		Assertions.assertTrue(savedServiceModelOptional.isEmpty());
	}

	@Test
	void findAllTest() {
		final List<DataUnitSchemaPersistentModel> persistentModels = persistentModelGenerator.generateList();
		Mockito.when(dao.findAll(params)).thenReturn(persistentModels);

		final List<DataUnitSchemaServiceModel> serviceModels = serviceModelGenerator.generateList();
		for (int i = 0; i < persistentModels.size(); i++) {
			final DataUnitSchemaServiceModel serviceModel = serviceModels.get(i);
			TestReflectionUtils.setField(serviceModel, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS, new ArrayList<>());
			Mockito.when(converter.convert(persistentModels.get(i), DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS)).
					thenReturn(serviceModel);
		}

		final List<DataUnitSchemaServiceModel> savedServiceModels = service.findAll(params);
		Assertions.assertNotNull(savedServiceModels);
		Assertions.assertFalse(savedServiceModels.isEmpty());
		Assertions.assertTrue(Objects.deepEquals(serviceModels, savedServiceModels));
	}

	@Test
	void findAllEmptyTest() {
		final List<DataUnitSchemaPersistentModel> persistentModels = new ArrayList<>();
		Mockito.when(dao.findAll(params)).thenReturn(persistentModels);

		final List<DataUnitSchemaServiceModel> savedServiceModels = service.findAll(params);
		Assertions.assertNotNull(savedServiceModels);
		Assertions.assertTrue(savedServiceModels.isEmpty());
	}

	@Test
	void deleteByIdNotInCacheSuccessTest() {
		final DataUnitSchemaPersistentModel persistentModel = persistentModelGenerator.generate();
		final Long id = persistentModel.getId();
		Mockito.when(dao.findById(id)).thenReturn(Optional.of(persistentModel));

		final DataUnitSchemaServiceModel serviceModel = serviceModelGenerator.generate();
		Mockito.when(converter.convert(persistentModel)).thenReturn(serviceModel);
		Mockito.when(cache.contains(id)).thenReturn(false);
		service.deleteById(id);

		Mockito.verify(dao).delete(persistentModel);
		Mockito.verify(eventListener).onEvent(
				Mockito.argThat(new EventArgumentMatcher(new Event<>(serviceModel, EventType.DELETE))));
	}

	@Test
	void deleteByIdInCacheSuccessTest() {
		final DataUnitSchemaPersistentModel persistentModel = persistentModelGenerator.generate();
		final Long id = persistentModel.getId();
		Mockito.when(dao.findById(id)).thenReturn(Optional.of(persistentModel));

		final DataUnitSchemaServiceModel serviceModel = serviceModelGenerator.generate();
		Mockito.when(converter.convert(persistentModel)).thenReturn(serviceModel);
		Mockito.when(cache.contains(id)).thenReturn(true);
		service.deleteById(id);

		Mockito.verify(dao).delete(persistentModel);
		Mockito.verify(cache).invalidate(id);
		Mockito.verify(eventListener).onEvent(
				Mockito.argThat(new EventArgumentMatcher(new Event<>(serviceModel, EventType.DELETE))));
	}

	@Test
	void deleteByIdNotFoundTest() {
		Mockito.when(dao.findById(INCORRECT_LONG_ID)).thenReturn(Optional.empty());

		Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteById(INCORRECT_LONG_ID),
				"'DataUnitSchemaPersistentModel' with id = '" + INCORRECT_LONG_ID + "' not found");
	}

	@Test
	void existsByNameTrueTest() {
		final DataUnitSchemaServiceModel dto = serviceModelGenerator.generate();
		final String name = dto.getName();
		Mockito.when(dao.existsByName(name)).thenReturn(true);

		Assertions.assertTrue(service.existsByName(name));
	}

	@Test
	void existsByNameFalseTest() {
		final DataUnitSchemaServiceModel dto = serviceModelGenerator.generate();
		final String name = dto.getName();
		Mockito.when(dao.existsByName(name)).thenReturn(false);

		Assertions.assertFalse(service.existsByName(name));
	}

	@Test
	void existsByNameAndNotIdTrueTest() {
		final DataUnitSchemaServiceModel dto = serviceModelGenerator.generate();
		final String name = dto.getName();
		final Long id = dto.getId();
		Mockito.when(dao.existsByNameAndNotId(name, id)).thenReturn(true);

		Assertions.assertTrue(service.existsByNameAndNotId(name, id));
	}

	@Test
	void existsByNameAndNotIdFalseTest() {
		final DataUnitSchemaServiceModel dto = serviceModelGenerator.generate();
		final String name = dto.getName();
		final Long id = dto.getId();
		Mockito.when(dao.existsByNameAndNotId(name, id)).thenReturn(false);

		Assertions.assertFalse(service.existsByNameAndNotId(name, id));
	}

	private static class EventArgumentMatcher implements ArgumentMatcher<Event<DataUnitSchemaServiceModel>> {

		private final Event<DataUnitSchemaServiceModel> wanted;

		private EventArgumentMatcher(final Event<DataUnitSchemaServiceModel> wanted) {
			this.wanted = wanted;
		}

		@Override
		public boolean matches(final Event<DataUnitSchemaServiceModel> actual) {
			return EqualsBuilder.reflectionEquals(wanted, actual);
		}
	}
}
