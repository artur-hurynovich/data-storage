package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.converter.exception.DTOConverterException;
import com.hurynovich.data_storage.converter.model.ArgDescriptor;
import com.hurynovich.data_storage.model.AbstractDTO;
import com.hurynovich.data_storage.model.Identified;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractDTOConverter<T extends AbstractDTO<I>, U extends Identified<I>, I extends Serializable>
		implements DTOConverter<T, U, I> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDTOConverter.class);

	private final ModelMapper modelMapper;

	private final Map<Integer, ArgDescriptor<U, ?>> argDescriptorsByIdx;

	private final Set<String> validPropertyNames;

	private final String[] emptyIgnoreProperties = new String[0];

	protected AbstractDTOConverter(final @NonNull ModelMapper modelMapper,
								   final @NonNull Map<Integer, ArgDescriptor<U, ?>> argDescriptorsByIdx) {
		/*
		 * According to http://modelmapper.org/user-manual/faq/, ModelMapper is thread-safe and
		 * can be injected as a singleton
		 */
		this.modelMapper = modelMapper;
		this.argDescriptorsByIdx = argDescriptorsByIdx;

		validPropertyNames = argDescriptorsByIdx.values().stream().
				map(ArgDescriptor::getName).
				collect(Collectors.toSet());
	}

	@Override
	public U convert(final @Nullable T source) {
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
	public T convert(final @Nullable U source) {
		return convert(source, emptyIgnoreProperties);
	}

	@Override
	public T convert(final @Nullable U source, final String... ignoreProperties) {
		final T target;
		if (source != null) {
			final Set<String> ignorePropertiesSet = buildIgnoreProperties(ignoreProperties);
			validateIgnoreProperties(ignorePropertiesSet);
			final Class<?>[] argTypes = new Class[argDescriptorsByIdx.size()];
			final Object[] args = new Object[argDescriptorsByIdx.size()];
			argDescriptorsByIdx.forEach((idx, argDescriptor) -> {
				argTypes[idx] = argDescriptor.getType();

				final Object argValue;
				if (ignorePropertiesSet.contains(argDescriptor.getName())) {
					if (argDescriptor.getType().isAssignableFrom(List.class)) {
						argValue = Collections.emptyList();
					} else {
						argValue = null;
					}
				} else {
					argValue = argDescriptor.getValueFunction().apply(source);
				}

				args[idx] = argValue;
			});

			final Class<T> dtoClass = getDTOClass();
			try {
				final Constructor<T> constructor = ReflectionUtils.accessibleConstructor(dtoClass, argTypes);
				target = constructor.newInstance(args);
			} catch (final NoSuchMethodException e) {
				throw new DTOConverterException("Constructor for type '" + dtoClass +
						"' with arguments " + Arrays.deepToString(argTypes) + " not found", e);
			} catch (final InvocationTargetException | InstantiationException | IllegalAccessException e) {
				throw new DTOConverterException("Failed to instantiate object of type '" + dtoClass + "'", e);
			}
		} else {
			target = null;
		}

		return target;
	}

	private Set<String> buildIgnoreProperties(final String... ignoreProperties) {
		return ignoreProperties != null ?
				Stream.of(ignoreProperties).collect(Collectors.toSet()) : Collections.emptySet();
	}

	private void validateIgnoreProperties(final @NonNull Set<String> ignoreProperties) {
		ignoreProperties.forEach(property -> {
			if (!validPropertyNames.contains(property)) {
				LOGGER.warn("Unknown ignore property name '{}' for type '{}'", property, getDTOClass());
			}
		});
	}

	protected abstract Class<T> getDTOClass();

}
