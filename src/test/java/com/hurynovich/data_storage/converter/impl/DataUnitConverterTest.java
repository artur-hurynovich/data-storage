package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument_;
import com.hurynovich.data_storage.test_object_generator.TestIdentifiedObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDTOGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDocumentGenerator;
import com.hurynovich.data_storage.test_objects_asserter.TestIdentifiedObjectsAsserter;
import com.hurynovich.data_storage.test_objects_asserter.impl.DataUnitAsserter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DataUnitConverterTest {

	private final Converter<DataUnitDTO, DataUnitDocument, String> converter =
			new DataUnitConverter(new ConverterConfig().modelMapper());

	private final TestIdentifiedObjectGenerator<DataUnitDTO> dtoGenerator =
			new TestDataUnitDTOGenerator();

	private final TestIdentifiedObjectGenerator<DataUnitDocument> documentGenerator =
			new TestDataUnitDocumentGenerator();

	private final TestIdentifiedObjectsAsserter<DataUnitDTO, DataUnitDocument> asserter =
			new DataUnitAsserter();

	@Test
	void convertDTONullTest() {
		Assertions.assertNull(converter.convert(null));
	}

	@Test
	void convertNotNullTest() {
		final DataUnitDTO dto = dtoGenerator.generateObject();
		final DataUnitDocument document = converter.convert(dto);
		asserter.assertEquals(dto, document);
	}

	@Test
	void convertDocumentNullTest() {
		Assertions.assertNull(converter.convert((DataUnitDocument) null));
	}

	@Test
	void convertDocumentNotNullTest() {
		final DataUnitDocument document = documentGenerator.generateObject();
		final DataUnitDTO dto = converter.convert(document);
		asserter.assertEquals(document, dto);
	}

	@Test
	void convertDocumentNotNullIgnorePropertiesTest() {
		final DataUnitDocument document = documentGenerator.generateObject();
		final DataUnitDTO dto = converter.convert(document, DataUnitDocument_.PROPERTIES);
		asserter.assertEquals(document, dto, DataUnitDocument_.PROPERTIES);
	}

}
