package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.dto_service.DTOService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class DataUnitSchemaController {

	private final DTOService<DataUnitSchemaDTO, Long> dataUnitSchemaService;

	public DataUnitSchemaController(final DTOService<DataUnitSchemaDTO, Long> dataUnitSchemaService) {
		this.dataUnitSchemaService = dataUnitSchemaService;
	}

	@PostMapping("/schema")
	public ResponseEntity<DataUnitSchemaDTO> postSchema(final @RequestBody DataUnitSchemaDTO dataUnitSchema) {
		return new ResponseEntity<>(dataUnitSchemaService.save(dataUnitSchema), HttpStatus.CREATED);
	}

	@GetMapping("/schema/{id}")
	public ResponseEntity<DataUnitSchemaDTO> getSchemaById(final @PathVariable Long id) {
		final Optional<DataUnitSchemaDTO> dataUnitSchemaOptional = dataUnitSchemaService.findById(id);

		return dataUnitSchemaOptional.
				map(ResponseEntity::ok).
				orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/schemas")
	public ResponseEntity<List<DataUnitSchemaDTO>> getSchemas() {
		return ResponseEntity.ok(dataUnitSchemaService.findAll());
	}

	@PutMapping("/schema/{id}")
	public ResponseEntity<DataUnitSchemaDTO> putSchema(final @PathVariable Long id,
													   final @RequestBody DataUnitSchemaDTO dataUnitSchema) {
		return ResponseEntity.ok(dataUnitSchemaService.save(dataUnitSchema));
	}

	@DeleteMapping("/schema/{id}")
	public ResponseEntity<DataUnitSchemaDTO> deleteSchemaById(final @PathVariable Long id) {
		dataUnitSchemaService.deleteById(id);

		return ResponseEntity.noContent().build();
	}

}
