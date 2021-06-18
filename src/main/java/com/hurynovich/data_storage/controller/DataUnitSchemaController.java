package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.model.GenericResponseBodyWrapper;
import com.hurynovich.data_storage.model.ValidationResult;
import com.hurynovich.data_storage.model.ValidationResultType;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.DTOService;
import com.hurynovich.data_storage.utils.ErrorUtils;
import com.hurynovich.data_storage.validation.DTOValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class DataUnitSchemaController {

	private final DTOService<DataUnitSchemaDTO, Long> dataUnitSchemaService;

	private final DTOValidator<DataUnitSchemaDTO, Long> validator;

	public DataUnitSchemaController(final DTOService<DataUnitSchemaDTO, Long> dataUnitSchemaService,
									final DTOValidator<DataUnitSchemaDTO, Long> validator) {
		this.dataUnitSchemaService = dataUnitSchemaService;
		this.validator = validator;
	}

	@GetMapping("/schemas")
	public ResponseEntity<GenericResponseBodyWrapper> getSchemas() {
		final ValidationResult validationResult = new ValidationResult();
		final List<DataUnitSchemaDTO> object = dataUnitSchemaService.findAll();
		final GenericResponseBodyWrapper response =
				new GenericResponseBodyWrapper(validationResult, object);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/schema")
	public ResponseEntity<GenericResponseBodyWrapper> postSchema(final DataUnitSchemaDTO dataUnitSchema) {
		final ResponseEntity<GenericResponseBodyWrapper> responseEntity;

		final ValidationResult validationResult = validator.validateOnSave(dataUnitSchema);
		if (validationResult.getType() == ValidationResultType.SUCCESS) {
			responseEntity = ResponseEntity.ok(
					new GenericResponseBodyWrapper(validationResult, dataUnitSchemaService.save(dataUnitSchema)));
		} else {
			responseEntity = ResponseEntity.badRequest().body(
					new GenericResponseBodyWrapper(validationResult, dataUnitSchema));
		}

		return responseEntity;
	}

	@PutMapping("/schema")
	public ResponseEntity<GenericResponseBodyWrapper> putSchema(final DataUnitSchemaDTO dataUnitSchema) {
		final ResponseEntity<GenericResponseBodyWrapper> responseEntity;

		final ValidationResult validationResult = validator.validateOnUpdate(dataUnitSchema);
		if (validationResult.getType() == ValidationResultType.SUCCESS) {
			responseEntity = ResponseEntity.ok(
					new GenericResponseBodyWrapper(validationResult, dataUnitSchemaService.update(dataUnitSchema)));
		} else {
			responseEntity = ResponseEntity.badRequest().body(
					new GenericResponseBodyWrapper(validationResult, dataUnitSchema));
		}

		return responseEntity;
	}

	@DeleteMapping("/schema")
	public ResponseEntity<GenericResponseBodyWrapper> deleteSchema(final Long dataUnitSchemaId) {
		final ResponseEntity<GenericResponseBodyWrapper> responseEntity;

		final ValidationResult validationResult = validator.validateOnDelete(dataUnitSchemaId);
		if (validationResult.getType() == ValidationResultType.SUCCESS) {
			final Optional<DataUnitSchemaDTO> dataUnitSchemaOptional = dataUnitSchemaService.findById(dataUnitSchemaId);

			if (dataUnitSchemaOptional.isPresent()) {
				responseEntity = ResponseEntity.ok(
						new GenericResponseBodyWrapper(validationResult, dataUnitSchemaOptional.get()));
			} else {
				validationResult.setType(ValidationResultType.FAILURE);

				validationResult.addError(ErrorUtils.buildNotFoundByIdError(dataUnitSchemaId));

				responseEntity = ResponseEntity.ok(
						new GenericResponseBodyWrapper(validationResult, dataUnitSchemaId));
			}
		} else {
			responseEntity = ResponseEntity.badRequest().body(
					new GenericResponseBodyWrapper(validationResult, dataUnitSchemaId));
		}

		return responseEntity;
	}

}
