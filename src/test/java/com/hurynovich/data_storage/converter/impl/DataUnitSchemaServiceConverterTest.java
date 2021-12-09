package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.ServiceConverter;
import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelImpl_;
import com.hurynovich.data_storage.model_asserter.ModelAsserter;
import com.hurynovich.data_storage.model_asserter.impl.DataUnitSchemaAsserter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataUnitSchemaServiceConverterTest {

	private final ServiceConverter<DataUnitSchemaServiceModel, DataUnitSchemaPersistentModel> converter =
			new DataUnitSchemaServiceConverter(new DataUnitPropertySchemaServiceConverter());

	private final ModelGenerator<DataUnitSchemaServiceModel> serviceModelGenerator =
			new DataUnitSchemaServiceModelGenerator();

	private final ModelGenerator<DataUnitSchemaPersistentModel> persistentModelGenerator =
			new DataUnitSchemaPersistentModelGenerator();

	private final ModelAsserter<DataUnitSchemaServiceModel, DataUnitSchemaPersistentModel> asserter =
			new DataUnitSchemaAsserter();

	@Test
	void convertPersistentModelNullTest() {
		Assertions.assertNull(converter.convert((DataUnitSchemaPersistentModel) null));
	}

	@Test
	void convertPersistentModelNotNullTest() {
		final DataUnitSchemaPersistentModel persistentModel = persistentModelGenerator.generate();
		final DataUnitSchemaServiceModel serviceModel = converter.convert(persistentModel);
		asserter.assertEquals(persistentModel, serviceModel);
	}

	@Test
	void convertPersistentModelNotNullIgnorePropertySchemasTest() {
		final DataUnitSchemaPersistentModel persistentModel = persistentModelGenerator.generate();
		final DataUnitSchemaServiceModel serviceModel = converter.
				convert(persistentModel, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS);
		asserter.assertEquals(persistentModel, serviceModel, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS);

		final List<DataUnitPropertySchemaServiceModel> propertySchemas = serviceModel.getPropertySchemas();
		Assertions.assertNotNull(propertySchemas);
		Assertions.assertTrue(propertySchemas.isEmpty());
	}

	@Test
	void convertServiceModelNullTest() {
		Assertions.assertNull(converter.convert((DataUnitSchemaServiceModel) null));
	}

	@Test
	void convertServiceModelNotNullTest() {
		final DataUnitSchemaServiceModel serviceModel = serviceModelGenerator.generate();
		final DataUnitSchemaPersistentModel persistentModel = converter.convert(serviceModel);
		asserter.assertEquals(serviceModel, persistentModel);
	}

	@Test
	void convertServiceModelNotNullIgnoreNameTest() {
		final DataUnitSchemaServiceModel serviceModel = serviceModelGenerator.generate();
		final DataUnitSchemaPersistentModel persistentModel = converter.
				convert(serviceModel, DataUnitSchemaServiceModelImpl_.NAME);
		asserter.assertEquals(serviceModel, persistentModel, DataUnitSchemaServiceModelImpl_.NAME);

		Assertions.assertNull(persistentModel.getName());
	}
}
