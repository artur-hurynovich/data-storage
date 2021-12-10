package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.converter.ApiConverter;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.service.dto_service.MassReadService;
import com.hurynovich.data_storage.service.paginator.Paginator;
import com.hurynovich.data_storage.service.paginator.model.GenericPage;
import com.hurynovich.data_storage.validator.Validator;
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
import java.util.stream.Collectors;

@RestController
public class DataUnitSchemaController
		extends AbstractController<DataUnitSchemaApiModel, DataUnitSchemaServiceModel, Long> {

	private static final String DATA_UNIT_SCHEMA = "dataUnitSchema";

	private static final int ELEMENTS_PER_PAGE = 20;

	private final MassReadService<DataUnitSchemaServiceModel, Long> service;

	private final Paginator paginator;

	private final ApiConverter<DataUnitSchemaApiModel, DataUnitSchemaServiceModel> converter;

	public DataUnitSchemaController(final @NonNull Validator<DataUnitSchemaApiModel> validator,
									final @NonNull MassReadService<DataUnitSchemaServiceModel, Long> service,
									final @NonNull Paginator paginator,
									final @NonNull ApiConverter<DataUnitSchemaApiModel, DataUnitSchemaServiceModel> converter) {
		super(DATA_UNIT_SCHEMA, validator, service, converter);
		this.service = Objects.requireNonNull(service);
		this.paginator = Objects.requireNonNull(paginator);
		this.converter = Objects.requireNonNull(converter);
	}

	@PostMapping("/" + DATA_UNIT_SCHEMA)
	public ResponseEntity<DataUnitSchemaApiModel> postSchema(final @RequestBody DataUnitSchemaApiModel schema) {
		return post(schema);
	}

	@GetMapping("/" + DATA_UNIT_SCHEMA + "/{id}")
	public ResponseEntity<DataUnitSchemaApiModel> getSchemaById(final @PathVariable Long id) {
		return getById(id);
	}

	@GetMapping("/" + DATA_UNIT_SCHEMA + "s")
	public ResponseEntity<GenericPage<DataUnitSchemaApiModel>> getSchemas(
			final @RequestParam(required = false) Integer pageNumber) {
		final PaginationParams params = paginator.buildParams(pageNumber, ELEMENTS_PER_PAGE);
		final List<DataUnitSchemaApiModel> schemas = service.findAll(params).stream().
				map(converter::convert).
				collect(Collectors.toList());

		return ResponseEntity.ok(paginator.buildPage(schemas, service.count(), params));
	}

	@PutMapping("/" + DATA_UNIT_SCHEMA + "/{id}")
	public ResponseEntity<DataUnitSchemaApiModel> putSchema(final @PathVariable Long id,
															final @RequestBody DataUnitSchemaApiModel schema) {
		return put(id, schema);
	}

	@DeleteMapping("/" + DATA_UNIT_SCHEMA + "/{id}")
	public ResponseEntity<DataUnitSchemaApiModel> deleteSchemaById(final @PathVariable Long id) {
		return deleteById(id);
	}
}
