package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.ApiConverter;
import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelImpl_;
import com.hurynovich.data_storage.model_asserter.ModelAsserter;
import com.hurynovich.data_storage.model_asserter.impl.DataUnitSchemaAsserter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataUnitSchemaApiConverterTest {

	private final ApiConverter<DataUnitSchemaApiModel, DataUnitSchemaServiceModel> converter =
			new DataUnitSchemaApiConverter();

	private final ModelGenerator<DataUnitSchemaApiModel> apiModelGenerator =
			new DataUnitSchemaApiModelGenerator();

	private final ModelGenerator<DataUnitSchemaServiceModel> serviceModelGenerator =
			new DataUnitSchemaServiceModelGenerator();

	private final ModelAsserter<DataUnitSchemaApiModel, DataUnitSchemaServiceModel, DataUnitSchemaPersistentModel> asserter =
			new DataUnitSchemaAsserter();

	@Test
	void convertServiceModelNullTest() {
		Assertions.assertNull(converter.convert((DataUnitSchemaServiceModel) null));
	}

	@Test
	void convertServiceModelNotNullTest() {
		final DataUnitSchemaServiceModel serviceModel = serviceModelGenerator.generate();
		final DataUnitSchemaApiModel apiModel = converter.convert(serviceModel);
		asserter.assertEquals(serviceModel, apiModel);
	}

	@Test
	void convertServiceModelNotNullIgnorePropertySchemasTest() {
		final DataUnitSchemaServiceModel serviceModel = serviceModelGenerator.generate();
		final DataUnitSchemaApiModel apiModel = converter.
				convert(serviceModel, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS);
		asserter.assertEquals(serviceModel, apiModel, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS);

		final List<DataUnitPropertySchemaApiModel> propertySchemas = apiModel.getPropertySchemas();
		Assertions.assertNotNull(propertySchemas);
		Assertions.assertTrue(propertySchemas.isEmpty());
	}

	@Test
	void convertApiModelNullTest() {
		Assertions.assertNull(converter.convert((DataUnitSchemaApiModel) null));
	}

	@Test
	void convertApiModelNotNullTest() {
		final DataUnitSchemaApiModel apiModel = apiModelGenerator.generate();
		final DataUnitSchemaServiceModel serviceModel = converter.convert(apiModel);
		asserter.assertEquals(apiModel, serviceModel);
	}

	@Test
	void convertApiModelNotNullIgnoreNameTest() {
		final DataUnitSchemaApiModel apiModel = apiModelGenerator.generate();
		final DataUnitSchemaServiceModel serviceModel = converter.
				convert(apiModel, DataUnitSchemaServiceModelImpl_.NAME);
		asserter.assertEquals(apiModel, serviceModel, DataUnitSchemaServiceModelImpl_.NAME);

		Assertions.assertNull(serviceModel.getName());
	}
}
