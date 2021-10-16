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

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaControllerTest {

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

}
