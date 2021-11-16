package com.hurynovich.data_storage.event.impl;

import com.hurynovich.data_storage.event.EventHandler;
import com.hurynovich.data_storage.event.model.Event;
import com.hurynovich.data_storage.event.model.EventType;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.dto_service.DataUnitService;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaDeleteEventHandlerTest {

	private EventHandler<DataUnitSchemaDTO> eventHandler;

	@Mock
	private DataUnitService dataUnitService;

	private final TestObjectGenerator<DataUnitSchemaDTO> schemaGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@BeforeEach
	public void initService() {
		eventHandler = new DataUnitSchemaDeleteEventHandler(dataUnitService);
	}

	@Test
	void handleTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		eventHandler.handle(new Event<>(schema, EventType.DELETE));

		Mockito.verify(dataUnitService).deleteAllBySchemaId(schema.getId());
	}

}
