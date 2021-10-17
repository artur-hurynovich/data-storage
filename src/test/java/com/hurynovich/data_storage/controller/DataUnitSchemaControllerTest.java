package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.dto_service.DTOService;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaControllerTest {

	private final Long incorrectId = 1L;

	@Mock
	private DTOService<DataUnitSchemaDTO, Long> service;

	private DataUnitSchemaController controller;

	private final TestObjectGenerator<DataUnitSchemaDTO> dtoGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@BeforeEach
	public void initController() {
		controller = new DataUnitSchemaController(service);
	}

	@Test
	void postSchemaTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		Mockito.when(service.save(schemaDTO)).thenReturn(schemaDTO);

		final ResponseEntity<DataUnitSchemaDTO> response = controller.postSchema(schemaDTO);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assertions.assertEquals(schemaDTO, response.getBody());
	}

	@Test
	void getSchemaByIdTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final Long id = schemaDTO.getId();
		Mockito.when(service.findById(id)).thenReturn(Optional.of(schemaDTO));

		final ResponseEntity<DataUnitSchemaDTO> response = controller.getSchemaById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(schemaDTO, response.getBody());
	}

	@Test
	void getSchemaByIdEmptyTest() {
		Mockito.when(service.findById(incorrectId)).thenReturn(Optional.empty());

		final ResponseEntity<DataUnitSchemaDTO> response = controller.getSchemaById(incorrectId);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

	@Test
	void getSchemasTest() {
		final List<DataUnitSchemaDTO> schemaDTOs = dtoGenerator.generateMultipleObjects();
		Mockito.when(service.findAll()).thenReturn(schemaDTOs);

		final ResponseEntity<List<DataUnitSchemaDTO>> response = controller.getSchemas();
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertTrue(Objects.deepEquals(schemaDTOs, response.getBody()));
	}

	@Test
	void getSchemasEmptyTest() {
		Mockito.when(service.findAll()).thenReturn(new ArrayList<>());

		final ResponseEntity<List<DataUnitSchemaDTO>> response = controller.getSchemas();
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		final List<DataUnitSchemaDTO> schemaDTOs = response.getBody();
		Assertions.assertNotNull(schemaDTOs);
		Assertions.assertTrue(schemaDTOs.isEmpty());
	}

	@Test
	void putSchemaTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		Mockito.when(service.save(schemaDTO)).thenReturn(schemaDTO);

		final ResponseEntity<DataUnitSchemaDTO> response = controller.putSchema(schemaDTO.getId(), schemaDTO);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(schemaDTO, response.getBody());
	}

	@Test
	void deleteSchemaByIdTest() {
		final DataUnitSchemaDTO schemaDTO = dtoGenerator.generateSingleObject();
		final Long id = schemaDTO.getId();

		final ResponseEntity<DataUnitSchemaDTO> response = controller.deleteSchemaById(id);
		Mockito.verify(service).deleteById(id);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

}
