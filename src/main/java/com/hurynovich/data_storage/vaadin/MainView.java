package com.hurynovich.data_storage.vaadin;

import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.DTOService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

	public MainView(final DTOService<DataUnitSchemaDTO, Long> schemaService) {
		final Grid<DataUnitSchemaDTO> schemaGrid = new Grid<>(DataUnitSchemaDTO.class);
		schemaGrid.setColumns("name");
		schemaGrid.setItems(schemaService.findAll());
		add(schemaGrid);
	}

}
