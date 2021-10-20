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
	void convertFromDTONullTest() {
		final DataUnitDocument dataUnitDocument = converter.convert((DataUnitDTO) null);
		Assertions.assertNull(dataUnitDocument);
	}

	@Test
	void convertFromDTONotNullTest() {
		final DataUnitDTO dataUnitDTO = dtoGenerator.generateSingleObject();
		final DataUnitDocument dataUnitDocument = converter.convert(dataUnitDTO);
		checkDTOToDocumentConversion(dataUnitDTO, dataUnitDocument);
	}

	private void checkDTOToDocumentConversion(final DataUnitDTO dataUnitDTO, final DataUnitDocument dataUnitDocument) {
		Assertions.assertNotNull(dataUnitDocument);
		Assertions.assertEquals(dataUnitDTO.getId(), dataUnitDocument.getId());
		Assertions.assertEquals(dataUnitDTO.getSchemaId(), dataUnitDocument.getSchemaId());

		final List<DataUnitPropertyDTO> propertyDTOs = dataUnitDTO.getProperties();
		final List<DataUnitPropertyDocument> propertyDocuments = dataUnitDocument.getProperties();
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
	void convertToDTONullTest() {
		final DataUnitDTO dataUnitDTO = converter.convert((DataUnitDocument) null);
		Assertions.assertNull(dataUnitDTO);
	}

	@Test
	void convertToDTONotNullTest() {
		final DataUnitDocument dataUnitDocument = documentGenerator.generateSingleObject();
		final DataUnitDTO dataUnitDTO = converter.convert(dataUnitDocument);
		checkDocumentToDTOConversion(dataUnitDocument, dataUnitDTO);
	}

	private void checkDocumentToDTOConversion(final DataUnitDocument dataUnitDocument, final DataUnitDTO dataUnitDTO) {
		Assertions.assertNotNull(dataUnitDTO);
		Assertions.assertEquals(dataUnitDocument.getId(), dataUnitDTO.getId());
		Assertions.assertEquals(dataUnitDocument.getSchemaId(), dataUnitDTO.getSchemaId());

		final List<DataUnitPropertyDocument> propertyDocuments = dataUnitDocument.getProperties();
		final List<DataUnitPropertyDTO> propertyDTOs = dataUnitDTO.getProperties();
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
	void convertAllToDTOsNullTest() {
		final List<DataUnitDTO> dataUnitDTOs = converter.convert((Iterable<DataUnitDocument>) null);
		Assertions.assertNotNull(dataUnitDTOs);
		Assertions.assertTrue(dataUnitDTOs.isEmpty());
	}

	@Test
	void convertAllToDTOsNotNullTest() {
		final List<DataUnitDocument> dataUnitDocuments = documentGenerator.generateMultipleObjects();
		final List<DataUnitDTO> dataUnitDTOs = converter.convert(dataUnitDocuments);
		Assertions.assertNotNull(dataUnitDTOs);
		Assertions.assertEquals(dataUnitDocuments.size(), dataUnitDTOs.size());

		for (int i = 0; i < dataUnitDocuments.size(); i++) {
			final DataUnitDocument dataUnitDocument = dataUnitDocuments.get(i);
			final DataUnitDTO dataUnitDTO = dataUnitDTOs.get(i);
			checkDocumentToDTOConversion(dataUnitDocument, dataUnitDTO);
		}
	}

}
