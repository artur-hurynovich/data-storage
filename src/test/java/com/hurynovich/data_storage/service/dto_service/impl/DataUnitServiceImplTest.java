package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.converter.ServiceConverter;
import com.hurynovich.data_storage.dao.DataUnitDAO;
import com.hurynovich.data_storage.filter.model.DataUnitFilter;
import com.hurynovich.data_storage.model.AbstractDocument_;
import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit.DataUnitPersistentModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitPersistentModelGenerator;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModelGenerator;
import com.hurynovich.data_storage.model_asserter.ModelAsserter;
import com.hurynovich.data_storage.model_asserter.impl.DataUnitAsserter;
import com.hurynovich.data_storage.service.dto_service.DataUnitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hurynovich.data_storage.model.ModelConstants.INCORRECT_STRING_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitServiceImplTest {

	@Mock
	private DataUnitDAO dao;

	@Mock
	private ServiceConverter<DataUnitServiceModel, DataUnitPersistentModel> converter;

	@Mock
	private PaginationParams params;

	@Mock
	private DataUnitFilter filter;

	private DataUnitService service;

	private final ModelGenerator<DataUnitServiceModel> serviceModelGenerator =
			new DataUnitServiceModelGenerator();

	private final ModelGenerator<DataUnitPersistentModel> persistentModelGenerator =
			new DataUnitPersistentModelGenerator();

	private final ModelAsserter<DataUnitServiceModel, DataUnitPersistentModel> asserter = new DataUnitAsserter();

	@BeforeEach
	public void initService() {
		service = new DataUnitServiceImpl(dao, converter);
	}

	@Test
	void saveTest() {
		final DataUnitServiceModel serviceModel = serviceModelGenerator.generateNullId();
		final DataUnitPersistentModel persistentModel = persistentModelGenerator.generate();
		Mockito.when(converter.convert(serviceModel)).thenReturn(persistentModel);
		Mockito.when(dao.save(persistentModel)).thenReturn(persistentModel);
		Mockito.when(converter.convert(persistentModel)).thenReturn(serviceModelGenerator.generate());

		final DataUnitServiceModel savedServiceModel = service.save(serviceModel);
		asserter.assertEquals(serviceModel, savedServiceModel, AbstractDocument_.ID);
		Assertions.assertNotNull(savedServiceModel.getId());
	}

	@Test
	void findByIdTest() {
		final DataUnitPersistentModel persistentModel = persistentModelGenerator.generate();
		final String id = persistentModel.getId();
		Mockito.when(dao.findById(id)).thenReturn(Optional.of(persistentModel));

		final DataUnitServiceModel serviceModel = serviceModelGenerator.generate();
		Mockito.when(converter.convert(persistentModel)).thenReturn(serviceModel);

		final Optional<DataUnitServiceModel> savedDTOOptional = service.findById(id);
		Assertions.assertTrue(savedDTOOptional.isPresent());
		asserter.assertEquals(serviceModel, savedDTOOptional.get());
	}

	@Test
	void findByIdEmptyTest() {
		Mockito.when(dao.findById(INCORRECT_STRING_ID)).thenReturn(Optional.empty());

		final Optional<DataUnitServiceModel> savedDTOOptional = service.findById(INCORRECT_STRING_ID);
		Assertions.assertFalse(savedDTOOptional.isPresent());
	}

	@Test
	void deleteSuccessTest() {
		final DataUnitPersistentModel persistentModel = persistentModelGenerator.generate();
		final String id = persistentModel.getId();
		Mockito.when(dao.findById(id)).thenReturn(Optional.of(persistentModel));

		service.deleteById(id);

		Mockito.verify(dao).delete(persistentModel);
	}

	@Test
	void deleteNotFoundTest() {
		Mockito.when(dao.findById(INCORRECT_STRING_ID)).thenReturn(Optional.empty());

		Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteById(INCORRECT_STRING_ID),
				"'DataUnitDocument' with id = '" + INCORRECT_STRING_ID + "' not found");
	}

	@Test
	void findAllTest() {
		final List<DataUnitPersistentModel> persistentModels = persistentModelGenerator.generateList();
		Mockito.when(dao.findAll(params, filter)).thenReturn(persistentModels);

		final List<DataUnitServiceModel> serviceModels = serviceModelGenerator.generateList();
		for (int i = 0; i < persistentModels.size(); i++) {
			final DataUnitServiceModel serviceModel = serviceModels.get(i);
			Mockito.when(converter.convert(persistentModels.get(i))).thenReturn(serviceModel);
		}

		final List<DataUnitServiceModel> savedServiceModels = service.findAll(params, filter);
		Assertions.assertNotNull(savedServiceModels);
		Assertions.assertFalse(savedServiceModels.isEmpty());
		Assertions.assertEquals(serviceModels.size(), savedServiceModels.size());
		for (int i = 0; i < serviceModels.size(); i++) {
			asserter.assertEquals(serviceModels.get(i), savedServiceModels.get(i));
		}
	}

	@Test
	void findAllEmptyTest() {
		final List<DataUnitPersistentModel> persistentModels = new ArrayList<>();
		Mockito.when(dao.findAll(params, filter)).thenReturn(persistentModels);

		final List<DataUnitServiceModel> savedServiceModels = service.findAll(params, filter);
		Assertions.assertNotNull(savedServiceModels);
		Assertions.assertTrue(savedServiceModels.isEmpty());
	}

	@Test
	void countTest() {
		final List<DataUnitPersistentModel> persistentModels = persistentModelGenerator.generateList();
		final long count = persistentModels.size();
		Mockito.when(dao.count(filter)).thenReturn(count);

		Assertions.assertEquals(count, service.count(filter));
	}

	@Test
	void deleteAllBySchemaIdTest() {
		final DataUnitPersistentModel persistentModel = persistentModelGenerator.generate();
		final Long schemaId = persistentModel.getSchemaId();
		service.deleteAllBySchemaId(schemaId);

		Mockito.verify(dao).deleteAllBySchemaId(schemaId);
	}
}
