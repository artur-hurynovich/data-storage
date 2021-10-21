package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO.DataUnitPropertyDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument.DataUnitPropertyDocument;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDTOGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataUnitDTOConverterTest {

	private final Converter<DataUnitDTO, DataUnitDocument> converter =
			new DataUnitDTOConverter();

	private final TestObjectGenerator<DataUnitDTO> dataUnitGenerator =
			new TestDataUnitDTOGenerator();

	@Test
	void convertNullTest() {
		Assertions.assertNull(converter.convert(null));
	}

	@Test
	void convertNotNullTest() {
		final DataUnitDTO dto = dataUnitGenerator.generateSingleObject();
		final DataUnitDocument document = converter.convert(dto);
		checkConversion(dto, document);
	}

	private void checkConversion(final DataUnitDTO dto, final DataUnitDocument document) {
		Assertions.assertNotNull(document);
		Assertions.assertEquals(dto.getId(), document.getId());
		Assertions.assertEquals(dto.getSchemaId(), document.getSchemaId());

		final List<DataUnitPropertyDTO> propertyDTOs = dto.getProperties();
		final List<DataUnitPropertyDocument> propertyDocuments = document.getProperties();
		Assertions.assertNotNull(propertyDTOs);
		Assertions.assertNotNull(propertyDocuments);
		Assertions.assertEquals(propertyDTOs.size(), propertyDocuments.size());

		for (int i = 0; i < propertyDTOs.size(); i++) {
			final DataUnitPropertyDTO propertyDTO = propertyDTOs.get(i);
			final DataUnitPropertyDocument propertyDocument = propertyDocuments.get(i);
			Assertions.assertNotNull(propertyDocument);
			Assertions.assertEquals(propertyDTO.getSchemaId(), propertyDocument.getSchemaId());
			Assertions.assertEquals(propertyDTO.getValue(), propertyDocument.getValue());
		}
	}

}
