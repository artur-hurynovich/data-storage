package com.hurynovich.data_storage.service.config;

import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import com.hurynovich.data_storage.service.data_unit_property_value_checker.DataUnitPropertyValueTypeChecker;
import com.hurynovich.data_storage.service.data_unit_property_value_checker.impl.DataUnitPropertyValueBooleanTypeChecker;
import com.hurynovich.data_storage.service.data_unit_property_value_checker.impl.DataUnitPropertyValueDateTypeChecker;
import com.hurynovich.data_storage.service.data_unit_property_value_checker.impl.DataUnitPropertyValueFloatTypeChecker;
import com.hurynovich.data_storage.service.data_unit_property_value_checker.impl.DataUnitPropertyValueIntegerTypeChecker;
import com.hurynovich.data_storage.service.data_unit_property_value_checker.impl.DataUnitPropertyValueTextTypeChecker;
import com.hurynovich.data_storage.service.data_unit_property_value_checker.impl.DataUnitPropertyValueTimeTypeChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

@Configuration
public class DataUnitPropertyValueCheckerConfig {

	@Bean
	public Map<DataUnitPropertyType, DataUnitPropertyValueTypeChecker> dataUnitPropertyValueTypeCheckersByType() {
		final EnumMap<DataUnitPropertyType, DataUnitPropertyValueTypeChecker> dataUnitPropertyTypeCheckerByType =
				new EnumMap<>(DataUnitPropertyType.class);

		dataUnitPropertyTypeCheckerByType.put(DataUnitPropertyType.TEXT, dataUnitPropertyValueTextTypeChecker());
		dataUnitPropertyTypeCheckerByType.put(DataUnitPropertyType.INTEGER, dataUnitPropertyValueIntegerTypeChecker());
		dataUnitPropertyTypeCheckerByType.put(DataUnitPropertyType.FLOAT, dataUnitPropertyValueFloatTypeChecker());
		dataUnitPropertyTypeCheckerByType.put(DataUnitPropertyType.BOOLEAN, dataUnitPropertyValueBooleanTypeChecker());
		dataUnitPropertyTypeCheckerByType.put(DataUnitPropertyType.DATE, dataUnitPropertyValueDateTypeChecker());
		dataUnitPropertyTypeCheckerByType.put(DataUnitPropertyType.TIME, dataUnitPropertyValueTimeTypeChecker());

		return Collections.unmodifiableMap(dataUnitPropertyTypeCheckerByType);
	}

	@Bean
	public DataUnitPropertyValueTypeChecker dataUnitPropertyValueTextTypeChecker() {
		return new DataUnitPropertyValueTextTypeChecker();
	}

	@Bean
	public DataUnitPropertyValueTypeChecker dataUnitPropertyValueIntegerTypeChecker() {
		return new DataUnitPropertyValueIntegerTypeChecker();
	}

	@Bean
	public DataUnitPropertyValueTypeChecker dataUnitPropertyValueFloatTypeChecker() {
		return new DataUnitPropertyValueFloatTypeChecker();
	}

	@Bean
	public DataUnitPropertyValueTypeChecker dataUnitPropertyValueBooleanTypeChecker() {
		return new DataUnitPropertyValueBooleanTypeChecker();
	}

	@Bean
	public DataUnitPropertyValueTypeChecker dataUnitPropertyValueDateTypeChecker() {
		return new DataUnitPropertyValueDateTypeChecker();
	}

	@Bean
	public DataUnitPropertyValueTypeChecker dataUnitPropertyValueTimeTypeChecker() {
		return new DataUnitPropertyValueTimeTypeChecker();
	}

}
