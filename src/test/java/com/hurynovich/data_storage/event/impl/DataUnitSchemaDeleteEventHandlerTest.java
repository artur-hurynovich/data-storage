package com.hurynovich.data_storage.event.impl;

import com.hurynovich.data_storage.event.EventHandler;
import com.hurynovich.data_storage.event.model.Event;
import com.hurynovich.data_storage.event.model.EventType;
import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelGenerator;
import com.hurynovich.data_storage.service.dto_service.DataUnitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaDeleteEventHandlerTest {

	private EventHandler<DataUnitSchemaServiceModel> eventHandler;

	@Mock
	private DataUnitService dataUnitService;

	private final ModelGenerator<DataUnitSchemaServiceModel> schemaGenerator =
			new DataUnitSchemaServiceModelGenerator();

	@BeforeEach
	public void initService() {
		eventHandler = new DataUnitSchemaDeleteEventHandler(dataUnitService);
	}

	@Test
	void handleTest() {
		final DataUnitSchemaServiceModel schema = schemaGenerator.generate();
		eventHandler.handle(new Event<>(schema, EventType.DELETE));

		Mockito.verify(dataUnitService).deleteAllBySchemaId(schema.getId());
	}
}
