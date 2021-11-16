package com.hurynovich.data_storage.event.impl;

import com.hurynovich.data_storage.event.EventHandler;
import com.hurynovich.data_storage.event.EventListener;
import com.hurynovich.data_storage.event.model.Event;
import com.hurynovich.data_storage.event.model.EventType;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class GenericEventListenerTest {

	private EventListener<DataUnitSchemaDTO> eventListener;

	@Mock
	private EventHandler<DataUnitSchemaDTO> eventHandler;

	private final TestObjectGenerator<DataUnitSchemaDTO> schemaGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@BeforeEach
	public void initEventListener() {
		final Map<EventType, EventHandler<DataUnitSchemaDTO>> handlersByEventType =
				new EnumMap<>(EventType.class);
		handlersByEventType.put(EventType.DELETE, eventHandler);

		eventListener = new GenericEventListener<>(handlersByEventType);
	}

	@Test
	void onEventTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		final Event<DataUnitSchemaDTO> event = new Event<>(schema, EventType.DELETE);
		eventListener.onEvent(event);

		Mockito.verify(eventHandler).handle(event);
	}

}
