package com.hurynovich.data_storage.event.impl;

import com.hurynovich.data_storage.event.EventHandler;
import com.hurynovich.data_storage.event.model.Event;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.service.dto_service.DataUnitService;
import org.springframework.lang.NonNull;

import java.util.Objects;

class DataUnitSchemaDeleteEventHandler implements EventHandler<DataUnitSchemaServiceModel> {

	private final DataUnitService dataUnitService;

	public DataUnitSchemaDeleteEventHandler(final @NonNull DataUnitService dataUnitService) {
		this.dataUnitService = Objects.requireNonNull(dataUnitService);
	}

	@Override
	public void handle(final @NonNull Event<DataUnitSchemaServiceModel> event) {
		final Long schemaId = event.getPayload().getId();

		dataUnitService.deleteAllBySchemaId(Objects.requireNonNull(schemaId));
	}
}
