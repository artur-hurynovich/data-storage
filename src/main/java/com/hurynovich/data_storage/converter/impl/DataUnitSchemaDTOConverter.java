package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.model.ArgDescriptor;
import com.hurynovich.data_storage.model.AbstractEntity_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.utils.MassProcessingUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class DataUnitSchemaDTOConverter extends AbstractDTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity, Long> {

	protected DataUnitSchemaDTOConverter() {
		super(Map.of(0, new ArgDescriptor<>(AbstractEntity_.ID, Long.class, DataUnitSchemaEntity::getId),
				1, new ArgDescriptor<>(DataUnitSchemaEntity_.NAME, String.class, DataUnitSchemaEntity::getName),
				2, new ArgDescriptor<>(DataUnitSchemaEntity_.PROPERTY_SCHEMAS, List.class, schema -> MassProcessingUtils.
						processQuietly(schema.getPropertySchemas(), convertPropertySchemaFunction()))));
	}

	private static Function<DataUnitPropertySchemaEntity, DataUnitPropertySchemaDTO> convertPropertySchemaFunction() {
		return source -> {
			final DataUnitPropertySchemaDTO target;
			if (source != null) {
				target = new DataUnitPropertySchemaDTO(source.getId(), source.getName(), source.getType());
			} else {
				target = null;
			}

			return target;
		};
	}

	@Override
	protected Class<DataUnitSchemaEntity> getTargetClass() {
		return DataUnitSchemaEntity.class;
	}

	@Override
	protected Class<DataUnitSchemaDTO> getDTOClass() {
		return DataUnitSchemaDTO.class;
	}

}
