package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.controller.exception.ControllerValidationException;
import com.hurynovich.data_storage.converter.ApiConverter;
import com.hurynovich.data_storage.model.ApiModel;
import com.hurynovich.data_storage.model.ServiceModel;
import com.hurynovich.data_storage.service.dto_service.BaseService;
import com.hurynovich.data_storage.utils.ValidationErrorMessageUtils;
import com.hurynovich.data_storage.validator.Validator;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class AbstractController<T extends ApiModel<I>, U extends ServiceModel<I>, I extends Serializable> {

	private final String serviceModelName;

	private final Validator<T> validator;

	private final BaseService<U, I> service;

	private final ApiConverter<T, U> converter;

	public AbstractController(final @NonNull String serviceModelName,
							  final @NonNull Validator<T> validator,
							  final @NonNull BaseService<U, I> service,
							  final @NonNull ApiConverter<T, U> converter) {
		this.serviceModelName = Objects.requireNonNull(serviceModelName);
		this.validator = Objects.requireNonNull(validator);
		this.service = Objects.requireNonNull(service);
		this.converter = Objects.requireNonNull(converter);
	}

	protected ResponseEntity<T> post(final @NonNull T t) {
		if (t.getId() == null) {
			final ValidationResult validationResult = validator.validate(t);
			if (validationResult.getType() == ValidationResultType.SUCCESS) {
				final T apiModel = converter.convert(service.save(converter.convert(t)));

				return new ResponseEntity<>(apiModel, HttpStatus.CREATED);
			} else {
				throw new ControllerValidationException(validationResult.getErrors());
			}
		} else {
			throw new ControllerValidationException(
					Set.of(ValidationErrorMessageUtils.buildIsNotNullErrorMessage(serviceModelName + ".id")));
		}
	}

	protected ResponseEntity<T> getById(final @NonNull I id) {
		final Optional<U> optional = service.findById(id);
		if (optional.isPresent()) {
			final T apiModel = converter.convert(optional.get());

			return ResponseEntity.ok(apiModel);
		} else {
			throw new ControllerValidationException(HttpStatus.NOT_FOUND,
					Set.of(ValidationErrorMessageUtils.buildNotFoundByIdErrorMessage(serviceModelName, id)));
		}
	}

	protected ResponseEntity<T> put(final @NonNull I id,
									final @NonNull T t) {
		if (id.equals(t.getId())) {
			final ValidationResult validationResult = validator.validate(t);
			if (validationResult.getType() == ValidationResultType.SUCCESS) {
				final T apiModel = converter.convert(service.save(converter.convert(t)));

				return ResponseEntity.ok(apiModel);
			} else {
				throw new ControllerValidationException(validationResult.getErrors());
			}
		} else {
			throw new ControllerValidationException(Set.of("'" + serviceModelName + ".id' should be equal to path variable 'id'"));
		}
	}

	protected ResponseEntity<T> deleteById(final @PathVariable I id) {
		service.deleteById(id);

		return ResponseEntity.noContent().build();
	}
}
