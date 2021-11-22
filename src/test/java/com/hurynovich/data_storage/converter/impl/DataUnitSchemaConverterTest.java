package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.test_object_generator.TestIdentifiedObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaEntityGenerator;
import com.hurynovich.data_storage.test_objects_asserter.Asserter;
import com.hurynovich.data_storage.test_objects_asserter.impl.DataUnitSchemaAsserter;
import com.hurynovich.data_storage.test_objects_asserter.model.DataUnitSchemaWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DataUnitSchemaConverterTest {

	private final Converter<DataUnitSchemaDTO, DataUnitSchemaEntity, Long> converter =
			new DataUnitSchemaConverter(new ConverterConfig().modelMapper());

	private final TestIdentifiedObjectGenerator<DataUnitSchemaDTO> dtoGenerator =
			new TestDataUnitSchemaDTOGenerator();

	private final TestIdentifiedObjectGenerator<DataUnitSchemaEntity> entityGenerator =
			new TestDataUnitSchemaEntityGenerator();

	private final Asserter<DataUnitSchemaWrapper> asserter =
			new DataUnitSchemaAsserter();

	@Test
	void convertDTONullTest() {
		Assertions.assertNull(converter.convert(null));
	}

	@Test
	void convertDTONotNullTest() {
		final DataUnitSchemaDTO dto = dtoGenerator.generateObject();
		final DataUnitSchemaEntity entity = converter.convert(dto);
		asserter.assertEquals(DataUnitSchemaWrapper.of(dto), DataUnitSchemaWrapper.of(entity));
	}

	@Test
	void convertEntityNullTest() {
		Assertions.assertNull(converter.convert((DataUnitSchemaEntity) null));
	}

	@Test
	void convertEntityNotNullTest() {
		final DataUnitSchemaEntity entity = entityGenerator.generateObject();
		final DataUnitSchemaDTO dto = converter.convert(entity);
		asserter.assertEquals(DataUnitSchemaWrapper.of(entity), DataUnitSchemaWrapper.of(dto));
	}

	@Test
	void convertEntityNotNullIgnorePropertySchemasTest() {
		final DataUnitSchemaEntity entity = entityGenerator.generateObject();
		final DataUnitSchemaDTO dto = converter.convert(entity, DataUnitSchemaEntity_.PROPERTY_SCHEMAS);
		asserter.assertEquals(DataUnitSchemaWrapper.of(entity), DataUnitSchemaWrapper.of(dto),
				DataUnitSchemaEntity_.PROPERTY_SCHEMAS);
	}

}
