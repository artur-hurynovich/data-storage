package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.Identified;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class GenericConverter<T extends Identified<V>, U extends Identified<V>, V extends Serializable>
		implements Converter<T, U> {

	private final ModelMapper modelMapper = new ModelMapper();

	@Override
	public U convert(final T source) {
		final U target;
		if (source != null) {
			target = modelMapper.map(source, getTargetClass());
		} else {
			target = null;
		}

		return target;
	}

	protected abstract Class<U> getTargetClass();

	@Override
	public List<U> convertAll(final Iterable<T> sources) {
		final List<U> targets = new ArrayList<>();
		if (sources != null) {
			targets.addAll(StreamSupport.stream(sources.spliterator(), false).
					filter(Objects::nonNull).
					map(this::convert).
					collect(Collectors.toList()));
		}

		return targets;
	}

}
