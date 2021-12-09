package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.controller.exception.ControllerValidationException;
import com.hurynovich.data_storage.filter.model.DataUnitFilter;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModel;
import com.hurynovich.data_storage.service.dto_service.DataUnitService;
import com.hurynovich.data_storage.service.paginator.Paginator;
import com.hurynovich.data_storage.service.paginator.model.GenericPage;
import com.hurynovich.data_storage.validator.Validator;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class DataUnitController extends AbstractController<DataUnitServiceModel, String> {

	private static final String DATA_UNIT = "dataUnit";

	private static final int ELEMENTS_PER_PAGE = 20;

	private final Validator<DataUnitFilter> filterValidator;

	private final DataUnitService service;

	private final Paginator paginator;

	public DataUnitController(final @NonNull Validator<DataUnitServiceModel> dataUnitValidator,
							  final @NonNull Validator<DataUnitFilter> filterValidator,
							  final @NonNull DataUnitService service,
							  final @NonNull Paginator paginator) {
		super(DATA_UNIT, dataUnitValidator, service);
		this.filterValidator = Objects.requireNonNull(filterValidator);
		this.service = Objects.requireNonNull(service);
		this.paginator = Objects.requireNonNull(paginator);
	}

	@PostMapping("/" + DATA_UNIT)
	public ResponseEntity<DataUnitServiceModel> postDataUnit(final @RequestBody DataUnitServiceModel dataUnit) {
		return post(dataUnit);
	}

	@GetMapping("/" + DATA_UNIT + "/{id}")
	public ResponseEntity<DataUnitServiceModel> getDataUnitById(final @PathVariable String id) {
		return getById(id);
	}

	@GetMapping("/" + DATA_UNIT + "s")
	public ResponseEntity<GenericPage<DataUnitServiceModel>> getDataUnits(
			final @RequestParam(required = false) Integer pageNumber, final @RequestBody DataUnitFilter filter) {
		final ValidationResult validationResult = filterValidator.validate(filter);
		if (validationResult.getType() == ValidationResultType.SUCCESS) {
			final PaginationParams params = paginator.buildParams(pageNumber, ELEMENTS_PER_PAGE);
			final List<DataUnitServiceModel> dataUnits = service.findAll(params, filter);

			return ResponseEntity.ok(paginator.buildPage(dataUnits, service.count(filter), params));
		} else {
			throw new ControllerValidationException(validationResult.getErrors());
		}
	}

	@PutMapping("/" + DATA_UNIT + "/{id}")
	public ResponseEntity<DataUnitServiceModel> putDataUnit(final @PathVariable String id,
															final @RequestBody DataUnitServiceModel dataUnit) {
		return put(id, dataUnit);
	}

	@DeleteMapping("/" + DATA_UNIT + "/{id}")
	public ResponseEntity<DataUnitServiceModel> deleteDataUnitById(final @PathVariable String id) {
		return deleteById(id);
	}
}
