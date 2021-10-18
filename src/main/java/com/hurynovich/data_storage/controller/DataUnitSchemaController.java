package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.model.GenericValidatedResponse;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.dto_service.DTOService;
import com.hurynovich.data_storage.validator.DTOValidator;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
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

	private final DTOValidator<DataUnitSchemaDTO> validator;

	private final DTOService<DataUnitSchemaDTO, Long> service;

	public DataUnitSchemaController(final DTOValidator<DataUnitSchemaDTO> validator,
									final DTOService<DataUnitSchemaDTO, Long> service) {
		this.validator = validator;
		this.service = service;
	}

	@PostMapping("/schema")
	public ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> postSchema(final @RequestBody DataUnitSchemaDTO dataUnitSchema) {
		final ValidationResult validationResult;
		final DataUnitSchemaDTO body;
		final HttpStatus status;
		if (dataUnitSchema.getId() == null) {
			validationResult = validator.validate(dataUnitSchema);

			if (validationResult.getType() == ValidationResultType.SUCCESS) {
				body = service.save(dataUnitSchema);
				status = HttpStatus.CREATED;
			} else {
				body = null;
				status = HttpStatus.BAD_REQUEST;
			}
		} else {
			validationResult = new ValidationResult();
			validationResult.setType(ValidationResultType.FAILURE);
			validationResult.addError("'dataUnitSchema.id' should be null");

			body = null;
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<>(new GenericValidatedResponse<>(validationResult, body), status);
	}

	@GetMapping("/schema/{id}")
	public ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> getSchemaById(final @PathVariable Long id) {
		final ValidationResult validationResult;
		final DataUnitSchemaDTO body;
		final HttpStatus status;
		final Optional<DataUnitSchemaDTO> dataUnitSchemaOptional = service.findById(id);
		if (dataUnitSchemaOptional.isPresent()) {
			validationResult = new ValidationResult();
			body = dataUnitSchemaOptional.get();
			status = HttpStatus.OK;
		} else {
			validationResult = new ValidationResult();
			validationResult.setType(ValidationResultType.FAILURE);
			validationResult.addError("'dataUnitSchema' with id = " + id + " not found");

			body = null;
			status = HttpStatus.NOT_FOUND;
		}

		return new ResponseEntity<>(new GenericValidatedResponse<>(validationResult, body), status);
	}

	@GetMapping("/schemas")
	public ResponseEntity<GenericValidatedResponse<List<DataUnitSchemaDTO>>> getSchemas() {
		return ResponseEntity.ok(new GenericValidatedResponse<>(new ValidationResult(), service.findAll()));
	}

	@PutMapping("/schema/{id}")
	public ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> putSchema(final @PathVariable Long id,
																				 final @RequestBody DataUnitSchemaDTO dataUnitSchema) {
		final ValidationResult validationResult;
		final DataUnitSchemaDTO body;
		final HttpStatus status;
		if (id.equals(dataUnitSchema.getId())) {
			validationResult = validator.validate(dataUnitSchema);

			if (validationResult.getType() == ValidationResultType.SUCCESS) {
				body = service.save(dataUnitSchema);
				status = HttpStatus.OK;
			} else {
				body = null;
				status = HttpStatus.BAD_REQUEST;
			}
		} else {
			validationResult = new ValidationResult();
			validationResult.setType(ValidationResultType.FAILURE);
			validationResult.addError("'dataUnitSchema.id' should be equal to path variable 'id'");

			body = null;
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<>(new GenericValidatedResponse<>(validationResult, body), status);
	}

	@DeleteMapping("/schema/{id}")
	public ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> deleteSchemaById(final @PathVariable Long id) {
		service.deleteById(id);

		return new ResponseEntity<>(new GenericValidatedResponse<>(new ValidationResult(), null), HttpStatus.OK);
	}

}
