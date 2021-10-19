package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.model.DataUnitPropertyType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@RestController
public class DataUnitPropertyTypesController {

	private final Set<DataUnitPropertyType> availablePropertyTypes =
			new LinkedHashSet<>(Arrays.asList(DataUnitPropertyType.values()));

	@GetMapping("/dataUnitPropertyTypes")
	public ResponseEntity<Set<DataUnitPropertyType>> getPropertyTypes() {
		return ResponseEntity.ok(availablePropertyTypes);
	}

}
