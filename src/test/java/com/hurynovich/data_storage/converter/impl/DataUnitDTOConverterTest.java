package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.model.document.DataUnitDocument;
import com.hurynovich.data_storage.model.document.DataUnitDocument.DataUnitPropertyDocument;
import com.hurynovich.data_storage.model.dto.DataUnitDTO;
import com.hurynovich.data_storage.model.dto.DataUnitDTO.DataUnitPropertyDTO;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDTOGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDocumentGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataUnitDTOConverterTest {

	private final DTOConverter<DataUnitDTO, DataUnitDocument> converter =
			new DataUnitDTOConverter();

	private final TestObjectGenerator<DataUnitDTO> dtoGenerator =
			new TestDataUnitDTOGenerator();

	private final TestObjectGenerator<DataUnitDocument> documentGenerator =
			new TestDataUnitDocumentGenerator();

	@Test
	void convertDTONullTest() {
		final DataUnitDocument document = converter.convert((DataUnitDTO) null);
		Assertions.assertNull(document);
	}

	@Test
	void convertDTONotNullTest() {
		final DataUnitDTO dto = dtoGenerator.generateSingleObject();
		final DataUnitDocument document = converter.convert(dto);
		checkDTOConversion(dto, document);
	}

	private void checkDTOConversion(final DataUnitDTO dto, final DataUnitDocument document) {
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
		final DataUnitDTO dto = converter.convert((DataUnitDocument) null);
		Assertions.assertNull(dto);
	}

	@Test
	void convertDocumentNotNullTest() {
		final DataUnitDocument document = documentGenerator.generateSingleObject();
		final DataUnitDTO dto = converter.convert(document);
		checkDocumentConversion(document, dto);
	}

	private void checkDocumentConversion(final DataUnitDocument document, final DataUnitDTO dto) {
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

	@Test
	void convertDocumentsNullTest() {
		final List<DataUnitDTO> dtos = converter.convert((Iterable<DataUnitDocument>) null);
		Assertions.assertNotNull(dtos);
		Assertions.assertTrue(dtos.isEmpty());
	}

	@Test
	void convertDocumentsNotNullTest() {
		final List<DataUnitDocument> documents = documentGenerator.generateMultipleObjects();
		final List<DataUnitDTO> dtos = converter.convert(documents);
		Assertions.assertNotNull(dtos);
		Assertions.assertEquals(documents.size(), dtos.size());

		for (int i = 0; i < documents.size(); i++) {
			final DataUnitDocument dataUnitDocument = documents.get(i);
			final DataUnitDTO dataUnitDTO = dtos.get(i);
			checkDocumentConversion(dataUnitDocument, dataUnitDTO);
		}
	}

}
