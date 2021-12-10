package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.ServiceConverter;
import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.data_unit.DataUnitApiModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitPersistentModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitPersistentModelGenerator;
import com.hurynovich.data_storage.model.data_unit.DataUnitPropertyServiceModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModelGenerator;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModelImpl_;
import com.hurynovich.data_storage.model_asserter.ModelAsserter;
import com.hurynovich.data_storage.model_asserter.impl.DataUnitAsserter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataUnitServiceConverterTest {

	private final ServiceConverter<DataUnitServiceModel, DataUnitPersistentModel> converter =
			new DataUnitServiceConverter();

	private final ModelGenerator<DataUnitServiceModel> serviceModelGenerator =
			new DataUnitServiceModelGenerator();

	private final ModelGenerator<DataUnitPersistentModel> persistentModelGenerator =
			new DataUnitPersistentModelGenerator();

	private final ModelAsserter<DataUnitApiModel, DataUnitServiceModel, DataUnitPersistentModel> asserter = new DataUnitAsserter();

	@Test
	void convertPersistentModelNullTest() {
		Assertions.assertNull(converter.convert((DataUnitPersistentModel) null));
	}

	@Test
	void convertPersistentModelNotNullTest() {
		final DataUnitPersistentModel persistentModel = persistentModelGenerator.generate();
		final DataUnitServiceModel serviceModel = converter.convert(persistentModel);
		asserter.assertEquals(persistentModel, serviceModel);
	}

	@Test
	void convertPersistentModelNotNullIgnorePropertiesTest() {
		final DataUnitPersistentModel persistentModel = persistentModelGenerator.generate();
		final DataUnitServiceModel serviceModel = converter.
				convert(persistentModel, DataUnitServiceModelImpl_.PROPERTIES);
		asserter.assertEquals(persistentModel, serviceModel, DataUnitServiceModelImpl_.PROPERTIES);

		final List<DataUnitPropertyServiceModel> properties = serviceModel.getProperties();
		Assertions.assertNotNull(properties);
		Assertions.assertTrue(properties.isEmpty());
	}

	@Test
	void convertServiceModelNullTest() {
		Assertions.assertNull(converter.convert((DataUnitServiceModel) null));
	}

	@Test
	void convertServiceModelNotNullTest() {
		final DataUnitServiceModel serviceModel = serviceModelGenerator.generate();
		final DataUnitPersistentModel persistentModel = converter.convert(serviceModel);
		asserter.assertEquals(serviceModel, persistentModel);
	}

	@Test
	void convertServiceModelNotNullIgnoreSchemaIdTest() {
		final DataUnitServiceModel serviceModel = serviceModelGenerator.generate();
		final DataUnitPersistentModel persistentModel = converter.convert(serviceModel,
				DataUnitServiceModelImpl_.SCHEMA_ID);
		asserter.assertEquals(serviceModel, persistentModel, DataUnitServiceModelImpl_.SCHEMA_ID);

		Assertions.assertNull(persistentModel.getSchemaId());
	}
}
