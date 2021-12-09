package com.hurynovich.data_storage.event.impl;

import com.hurynovich.data_storage.event.EventHandler;
import com.hurynovich.data_storage.event.EventListener;
import com.hurynovich.data_storage.event.model.EventType;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.service.dto_service.DataUnitService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class EventListenerConfig {

	@Bean
	public EventListener<DataUnitSchemaServiceModel> dataUnitSchemaEventListener(
			final @NonNull DataUnitService dataUnitService) {
		final Map<EventType, EventHandler<DataUnitSchemaServiceModel>> handlersByEventType =
				new EnumMap<>(EventType.class);
		handlersByEventType.put(EventType.DELETE, new DataUnitSchemaDeleteEventHandler(dataUnitService));

		return new GenericEventListener<>(handlersByEventType);
	}
}
