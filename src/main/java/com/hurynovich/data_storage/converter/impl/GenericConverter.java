package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.exception.ConverterException;
import com.hurynovich.data_storage.model.Identified;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class GenericConverter<T extends Identified<V>, U extends Identified<V>, V extends Serializable>
		implements Converter<T, U> {

	private static final String INSTANTIATION_EXCEPTION_MESSAGE = "Failed to instantiate class of type '%s'";

	private final String[] ignoreProperties;

	protected GenericConverter() {
		ignoreProperties = new String[0];
	}

	protected GenericConverter(final String[] ignoreProperties) {
		this.ignoreProperties = ignoreProperties;
	}

	@Override
	public U convert(final T source) {
		final U target;
		if (source != null) {
			target = instantiateTarget();

			BeanUtils.copyProperties(source, target, ignoreProperties);
		} else {
			target = null;
		}

		return target;
	}

	private U instantiateTarget() {
		final Class<U> targetClass = getTargetClass();

		try {
			return targetClass.getDeclaredConstructor().newInstance();
		} catch (final InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new ConverterException(String.format(INSTANTIATION_EXCEPTION_MESSAGE, targetClass));
		}
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
