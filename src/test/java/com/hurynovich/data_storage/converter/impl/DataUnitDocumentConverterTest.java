package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO.DataUnitPropertyDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument.DataUnitPropertyDocument;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDocumentGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataUnitDocumentConverterTest {

	private final Converter<DataUnitDocument, DataUnitDTO> converter =
			new DataUnitDocumentConverter();

	private final TestObjectGenerator<DataUnitDocument> dataUnitGenerator =
			new TestDataUnitDocumentGenerator();

	@Test
	void convertNullTest() {
		final DataUnitDTO dto = converter.convert(null);
		Assertions.assertNull(dto);
	}

	@Test
	void convertNotNullTest() {
		final DataUnitDocument document = dataUnitGenerator.generateSingleObject();
		final DataUnitDTO dto = converter.convert(document);
		checkConversion(document, dto);
	}

	private void checkConversion(final DataUnitDocument document, final DataUnitDTO dto) {
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(document.getId(), dto.getId());
		Assertions.assertEquals(document.getSchemaId(), dto.getSchemaId());

		final List<DataUnitPropertyDocument> propertyDocuments = document.getProperties();
		final List<DataUnitPropertyDTO> propertyDTOs = dto.getProperties();
		Assertions.assertNotNull(propertyDTOs);
		Assertions.assertNotNull(propertyDTOs);
		Assertions.assertEquals(propertyDocuments.size(), propertyDTOs.size());

		for (int i = 0; i < propertyDocuments.size(); i++) {
			final DataUnitPropertyDocument propertyDocument = propertyDocuments.get(i);
			final DataUnitPropertyDTO propertyDTO = propertyDTOs.get(i);

			Assertions.assertNotNull(propertyDTO);
			Assertions.assertEquals(propertyDocument.getSchemaId(), propertyDTO.getSchemaId());
			Assertions.assertEquals(propertyDocument.getValue(), propertyDTO.getValue());
		}
	}

}
