package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.controller.exception.ControllerValidationException;
import com.hurynovich.data_storage.model.AbstractDTO;
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
import java.util.Optional;
import java.util.Set;

public class AbstractController<T extends AbstractDTO<I>, I extends Serializable> {

	private final String dtoName;

	private final Validator<T> validator;

	private final BaseService<T, I> service;

	public AbstractController(final @NonNull String dtoName,
							  final @NonNull Validator<T> validator,
							  final @NonNull BaseService<T, I> service) {
		this.dtoName = dtoName;
		this.validator = validator;
		this.service = service;
	}

	protected ResponseEntity<T> post(final @NonNull T t) {
		if (t.getId() == null) {
			final ValidationResult validationResult = validator.validate(t);
			if (validationResult.getType() == ValidationResultType.SUCCESS) {
				return new ResponseEntity<>(service.save(t), HttpStatus.CREATED);
			} else {
				throw new ControllerValidationException(validationResult.getErrors());
			}
		} else {
			throw new ControllerValidationException(
					Set.of(ValidationErrorMessageUtils.buildIsNotNullErrorMessage(dtoName + ".id")));
		}
	}

	protected ResponseEntity<T> getById(final @NonNull I id) {
		final Optional<T> optional = service.findById(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			throw new ControllerValidationException(HttpStatus.NOT_FOUND,
					Set.of(ValidationErrorMessageUtils.buildNotFoundByIdErrorMessage(dtoName, id)));
		}
	}

	protected ResponseEntity<T> put(final @NonNull I id,
									final @NonNull T t) {
		if (id.equals(t.getId())) {
			final ValidationResult validationResult = validator.validate(t);
			if (validationResult.getType() == ValidationResultType.SUCCESS) {
				return ResponseEntity.ok(service.save(t));
			} else {
				throw new ControllerValidationException(validationResult.getErrors());
			}
		} else {
			throw new ControllerValidationException(Set.of("'" + dtoName + ".id' should be equal to path variable 'id'"));
		}
	}

	protected ResponseEntity<T> deleteById(final @PathVariable I id) {
		service.deleteById(id);

		return ResponseEntity.noContent().build();
	}
}
