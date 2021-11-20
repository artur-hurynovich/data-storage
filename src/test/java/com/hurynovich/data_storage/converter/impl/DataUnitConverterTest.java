package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO.DataUnitPropertyDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument.DataUnitPropertyDocument;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDTOGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDocumentGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataUnitConverterTest {

	private final Converter<DataUnitDTO, DataUnitDocument, String> converter =
			new DataUnitConverter(new ConverterConfig().modelMapper());

	private final TestObjectGenerator<DataUnitDTO> dtoGenerator =
			new TestDataUnitDTOGenerator();

	private final TestObjectGenerator<DataUnitDocument> documentGenerator =
			new TestDataUnitDocumentGenerator();

	@Test
	void convertDTONullTest() {
		Assertions.assertNull(converter.convert((DataUnitDTO) null));
	}

	@Test
	void convertNotNullTest() {
		final DataUnitDTO dto = dtoGenerator.generateSingleObject();
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

	@Test
	void convertDocumentNullTest() {
		Assertions.assertNull(converter.convert((DataUnitDocument) null));
	}

	@Test
	void convertDocumentNotNullTest() {
		final DataUnitDocument document = documentGenerator.generateSingleObject();
		final DataUnitDTO dto = converter.convert(document);
		checkConversion(document, dto, false);
	}

	@Test
	void convertDocumentNotNullIgnorePropertiesTest() {
		final DataUnitDocument document = documentGenerator.generateSingleObject();
		final DataUnitDTO dto = converter.convert(document, "properties");
		checkConversion(document, dto, true);
	}

	private void checkConversion(final DataUnitDocument document, final DataUnitDTO dto,
								 final boolean ignoreProperties) {
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(document.getId(), dto.getId());
		Assertions.assertEquals(document.getSchemaId(), dto.getSchemaId());

		final List<DataUnitPropertyDocument> propertyDocuments = document.getProperties();
		final List<DataUnitPropertyDTO> propertyDTOs = dto.getProperties();
		Assertions.assertNotNull(propertyDTOs);

		if (ignoreProperties) {
			Assertions.assertTrue(propertyDTOs.isEmpty());
		} else {
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

}
