package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.filter.model.CriteriaComparison;
import com.hurynovich.data_storage.filter.model.DataUnitFilter;
import com.hurynovich.data_storage.filter.model.DataUnitPropertyCriteria;
import com.hurynovich.data_storage.model.Identified;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.service.data_unit_property_check_processor.DataUnitPropertyValueCheckProcessor;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaService;
import com.hurynovich.data_storage.utils.ValidationErrorMessageUtils;
import com.hurynovich.data_storage.validator.Validator;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
class DataUnitFilterValidator implements Validator<DataUnitFilter> {

	private final DataUnitSchemaService schemaService;

	private final Map<DataUnitPropertyType, Set<CriteriaComparison>> criteriaComparisonsByPropertyType;

	private final DataUnitPropertyValueCheckProcessor valueCheckProcessor;

	public DataUnitFilterValidator(final @NonNull DataUnitSchemaService schemaService,
								   final @NonNull Map<DataUnitPropertyType, Set<CriteriaComparison>> criteriaComparisonsByPropertyType,
								   final @NonNull DataUnitPropertyValueCheckProcessor valueCheckProcessor) {
		this.schemaService = Objects.requireNonNull(schemaService);
		this.criteriaComparisonsByPropertyType = Collections.unmodifiableMap(criteriaComparisonsByPropertyType);
		this.valueCheckProcessor = valueCheckProcessor;
	}

	@Override
	public ValidationResult validate(final @NonNull DataUnitFilter filter) {
		final ValidationResult result = new ValidationResult();
		final Long schemaId = filter.getSchemaId();
		final Optional<DataUnitSchemaServiceModel> schemaOptional = schemaService.findById(schemaId);
		if (schemaOptional.isEmpty()) {
			result.setType(ValidationResultType.FAILURE);
			result.addError(ValidationErrorMessageUtils.
					buildNotFoundByIdErrorMessage("dataUnitSchema", schemaId));
		} else {
			final DataUnitPropertyCriteriaValidationContext context =
					DataUnitPropertyCriteriaValidationContext.of(schemaOptional.get());

			filter.getCriteria().forEach(criteria -> validateCriteria(context, criteria, result));
		}

		return result;
	}

	private void validateCriteria(final @NonNull DataUnitPropertyCriteriaValidationContext context,
								  final @NonNull DataUnitPropertyCriteria criteria,
								  final @NonNull ValidationResult result) {
		final Long propertySchemaId = criteria.getPropertySchemaId();
		if (!context.isValidPropertySchemaId(propertySchemaId)) {
			result.setType(ValidationResultType.FAILURE);
			result.addError(ValidationErrorMessageUtils.
					buildNotFoundByIdErrorMessage("dataUnitPropertySchema", propertySchemaId));
		}

		if (!context.isUniquePropertySchemaId(propertySchemaId)) {
			result.setType(ValidationResultType.FAILURE);
			result.addError(ValidationErrorMessageUtils.
					buildFoundDuplicateErrorMessage("filter.criteria.propertySchemaId", propertySchemaId));
		}

		final DataUnitPropertySchemaServiceModel propertySchema = context.getPropertySchema(propertySchemaId);
		if (propertySchema != null) {
			final Set<CriteriaComparison> criteriaComparisons = criteriaComparisonsByPropertyType.get(propertySchema.getType());
			final CriteriaComparison criteriaComparison = criteria.getComparison();
			if (!criteriaComparisons.contains(criteriaComparison)) {
				result.setType(ValidationResultType.FAILURE);
				result.addError("'filter.criteria.comparison' '" + criteriaComparison + "' is incorrect " +
						"for dataUnitProperty with schemaId = '" + propertySchemaId + "'");
			}

			final Object comparisonPattern = criteria.getComparisonPattern();
			if (!valueCheckProcessor.processCheck(propertySchema, comparisonPattern)) {
				result.setType(ValidationResultType.FAILURE);
				result.addError("'filter.criteria.comparisonPattern' '" + comparisonPattern + "' is incorrect " +
						"for dataUnitProperty with schemaId = '" + propertySchemaId + "'");
			}
		}
	}

	private static class DataUnitPropertyCriteriaValidationContext {

		private final Set<Long> uniquePropertySchemaIds = new HashSet<>();

		private final Map<Long, DataUnitPropertySchemaServiceModel> propertySchemasById;

		private DataUnitPropertyCriteriaValidationContext(
				final @NonNull Map<Long, DataUnitPropertySchemaServiceModel> propertySchemasById) {
			this.propertySchemasById = propertySchemasById;
		}

		public static DataUnitPropertyCriteriaValidationContext of(final @NonNull DataUnitSchemaServiceModel schema) {
			return new DataUnitPropertyCriteriaValidationContext(schema.getPropertySchemas().stream().
					collect(Collectors.toMap(Identified::getId, Function.identity())));
		}

		public boolean isValidPropertySchemaId(final @NonNull Long propertySchemaId) {
			return propertySchemasById.containsKey(propertySchemaId);
		}

		public boolean isUniquePropertySchemaId(final @NonNull Long propertySchemaId) {
			return uniquePropertySchemaIds.add(propertySchemaId);
		}

		public DataUnitPropertySchemaServiceModel getPropertySchema(final @NonNull Long propertySchemaId) {
			return propertySchemasById.get(propertySchemaId);
		}
	}
}
