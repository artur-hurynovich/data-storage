package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.controller.model.GenericValidatedResponse;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.service.dto_service.BaseDTOService;
import com.hurynovich.data_storage.validator.DTOValidationHelper;
import com.hurynovich.data_storage.validator.DTOValidator;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
public class DataUnitController {

	private final DTOValidator<DataUnitDTO> validator;

	private final DTOValidationHelper helper;

	private final BaseDTOService<DataUnitDTO, String> service;

	public DataUnitController(final @NonNull DTOValidator<DataUnitDTO> validator,
							  final @NonNull DTOValidationHelper helper,
							  final @NonNull BaseDTOService<DataUnitDTO, String> service) {
		this.validator = Objects.requireNonNull(validator);
		this.helper = Objects.requireNonNull(helper);
		this.service = Objects.requireNonNull(service);
	}

	@PostMapping("/dataUnit")
	public ResponseEntity<GenericValidatedResponse<DataUnitDTO>> postDataUnit(
			final @RequestBody DataUnitDTO dataUnit) {
		final ValidationResult validationResult;
		final DataUnitDTO body;
		final HttpStatus status;
		if (dataUnit.getId() != null) {
			validationResult = new ValidationResult();
			helper.applyIsNotNullError("dataUnit.id", validationResult);
			body = null;
			status = HttpStatus.BAD_REQUEST;
		} else {
			validationResult = validator.validate(dataUnit);
			if (validationResult.getType() == ValidationResultType.SUCCESS) {
				body = service.save(dataUnit);
				status = HttpStatus.CREATED;
			} else {
				body = null;
				status = HttpStatus.BAD_REQUEST;
			}
		}


		return new ResponseEntity<>(new GenericValidatedResponse<>(validationResult, body), status);
	}

	@GetMapping("/dataUnit/{id}")
	public ResponseEntity<GenericValidatedResponse<DataUnitDTO>> getDataUnitById(final @PathVariable String id) {
		final ValidationResult validationResult;
		final DataUnitDTO body;
		final HttpStatus status;
		final Optional<DataUnitDTO> dataUnitOptional = service.findById(id);
		if (dataUnitOptional.isPresent()) {
			validationResult = new ValidationResult();
			body = dataUnitOptional.get();
			status = HttpStatus.OK;
		} else {
			validationResult = new ValidationResult();
			helper.applyNotFoundByIdError("dataUnit", id, validationResult);
			body = null;
			status = HttpStatus.NOT_FOUND;
		}

		return new ResponseEntity<>(new GenericValidatedResponse<>(validationResult, body), status);
	}

	@PutMapping("/dataUnit/{id}")
	public ResponseEntity<GenericValidatedResponse<DataUnitDTO>> putDataUnit(
			final @PathVariable String id,
			final @RequestBody DataUnitDTO dataUnit) {
		final ValidationResult validationResult;
		final DataUnitDTO body;
		final HttpStatus status;
		if (!id.equals(dataUnit.getId())) {
			validationResult = new ValidationResult();
			validationResult.setType(ValidationResultType.FAILURE);
			validationResult.addError("'dataUnit.id' should be equal to path variable 'id'");
			body = null;
			status = HttpStatus.BAD_REQUEST;
		} else {
			validationResult = validator.validate(dataUnit);
			if (validationResult.getType() == ValidationResultType.SUCCESS) {
				body = service.save(dataUnit);
				status = HttpStatus.OK;
			} else {
				body = null;
				status = HttpStatus.BAD_REQUEST;
			}
		}


		return new ResponseEntity<>(new GenericValidatedResponse<>(validationResult, body), status);
	}

	@DeleteMapping("/dataUnit/{id}")
	public ResponseEntity<GenericValidatedResponse<DataUnitDTO>> deleteDataUnitById(final @PathVariable String id) {
		service.deleteById(id);

		return new ResponseEntity<>(
				new GenericValidatedResponse<>(new ValidationResult(), null), HttpStatus.NO_CONTENT);
	}

}
