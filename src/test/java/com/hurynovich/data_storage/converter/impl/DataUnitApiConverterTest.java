package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.ApiConverter;
import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.data_unit.DataUnitApiModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitApiModelGenerator;
import com.hurynovich.data_storage.model.data_unit.DataUnitPersistentModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitPropertyServiceModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModelGenerator;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModelImpl_;
import com.hurynovich.data_storage.model_asserter.ModelAsserter;
import com.hurynovich.data_storage.model_asserter.impl.DataUnitAsserter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataUnitApiConverterTest {

	private final ApiConverter<DataUnitApiModel, DataUnitServiceModel> converter =
			new DataUnitApiConverter();

	private final ModelGenerator<DataUnitApiModel> apiModelGenerator =
			new DataUnitApiModelGenerator();

	private final ModelGenerator<DataUnitServiceModel> serviceModelGenerator =
			new DataUnitServiceModelGenerator();

	private final ModelAsserter<DataUnitApiModel, DataUnitServiceModel, DataUnitPersistentModel> asserter =
			new DataUnitAsserter();

	@Test
	void convertServiceModelNullTest() {
		Assertions.assertNull(converter.convert((DataUnitServiceModel) null));
	}

	@Test
	void convertServiceModelNotNullTest() {
		final DataUnitServiceModel serviceModel = serviceModelGenerator.generate();
		final DataUnitApiModel apiModel = converter.convert(serviceModel);
		asserter.assertEquals(serviceModel, apiModel);
	}

	@Test
	void convertServiceModelNotNullIgnoreSchemaIdTest() {
		final DataUnitServiceModel serviceModel = serviceModelGenerator.generate();
		final DataUnitApiModel apiModel = converter.convert(serviceModel,
				DataUnitServiceModelImpl_.SCHEMA_ID);
		asserter.assertEquals(serviceModel, apiModel, DataUnitServiceModelImpl_.SCHEMA_ID);

		Assertions.assertNull(apiModel.getSchemaId());
	}

	@Test
	void convertPersistentModelNullTest() {
		Assertions.assertNull(converter.convert((DataUnitApiModel) null));
	}

	@Test
	void convertPersistentModelNotNullTest() {
		final DataUnitApiModel apiModel = apiModelGenerator.generate();
		final DataUnitServiceModel serviceModel = converter.convert(apiModel);
		asserter.assertEquals(apiModel, serviceModel);
	}

	@Test
	void convertPersistentModelNotNullIgnorePropertiesTest() {
		final DataUnitApiModel apiModel = apiModelGenerator.generate();
		final DataUnitServiceModel serviceModel = converter.
				convert(apiModel, DataUnitServiceModelImpl_.PROPERTIES);
		asserter.assertEquals(apiModel, serviceModel, DataUnitServiceModelImpl_.PROPERTIES);

		final List<DataUnitPropertyServiceModel> properties = serviceModel.getProperties();
		Assertions.assertNotNull(properties);
		Assertions.assertTrue(properties.isEmpty());
	}
}
