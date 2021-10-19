package com.hurynovich.data_storage.service.data_unit_property_check_processor.impl;

import com.hurynovich.data_storage.model.DataUnitPropertyType;
import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.service.data_unit_property_check_processor.DataUnitPropertyValueCheckProcessor;
import com.hurynovich.data_storage.service.data_unit_property_check_processor.exception.DataUnitPropertyValueCheckProcessorException;
import com.hurynovich.data_storage.service.data_unit_property_value_checker.DataUnitPropertyValueTypeChecker;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class DataUnitPropertyValueCheckProcessorImpl implements DataUnitPropertyValueCheckProcessor {

	private final Map<DataUnitPropertyType, DataUnitPropertyValueTypeChecker> dataUnitPropertyTypeCheckerByType;

	public DataUnitPropertyValueCheckProcessorImpl(final @NonNull Map<DataUnitPropertyType, DataUnitPropertyValueTypeChecker> dataUnitPropertyTypeCheckerByType) {
		this.dataUnitPropertyTypeCheckerByType = dataUnitPropertyTypeCheckerByType;
	}

	@Override
	public boolean processCheck(final @NonNull DataUnitPropertySchemaDTO schema, final @Nullable Object value) {
		boolean checkResult;

		final DataUnitPropertyType type = Objects.requireNonNull(schema.getType());
		final DataUnitPropertyValueTypeChecker dataUnitPropertyValueTypeChecker = dataUnitPropertyTypeCheckerByType.
				get(type);
		if (dataUnitPropertyValueTypeChecker != null) {
			checkResult = value == null || dataUnitPropertyValueTypeChecker.check(value);
		} else {
			throw new DataUnitPropertyValueCheckProcessorException("No dataUnitPropertyValueTypeChecker for" +
					"DataUnitPropertyType=" + type);
		}

		return checkResult;
	}

}
