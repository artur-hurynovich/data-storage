package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.converter.exception.ConverterException;
import org.modelmapper.ModelMapper;
import org.springframework.core.ResolvableType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractConverter<T, U> implements Converter<T, U> {

	private final ModelMapper modelMapper = new ModelMapper();

	private final Class<U> targetClass;

	private final Map<String, PropertyDescriptor> sourcePropertyDescriptorsByName;

	private final List<PropertyDescriptor> targetPropertyDescriptors;

	protected AbstractConverter(final @NonNull Class<T> sourceClass, final @NonNull Class<U> targetClass) {
		this.targetClass = targetClass;

		sourcePropertyDescriptorsByName = resolvePropertyDescriptors(sourceClass).stream().
				collect(Collectors.toMap(PropertyDescriptor::getName, propertyDescriptor -> propertyDescriptor));

		targetPropertyDescriptors = resolvePropertyDescriptors(targetClass);
	}

	private List<PropertyDescriptor> resolvePropertyDescriptors(final @NonNull Class<?> clazz) {
		try {
			return Arrays.asList(Introspector.getBeanInfo(clazz).getPropertyDescriptors());
		} catch (final IntrospectionException e) {
			throw new ConverterException("Failed to get BeanInfo for class " + clazz, e);
		}
	}

	@Override
	public U convert(final @Nullable T source, final @Nullable String... ignoreProperties) {
		final U target;
		if (source != null) {
			target = instantiate(targetClass);

			copyProperties(source, target, ignoreProperties);
		} else {
			target = null;
		}

		return target;
	}

	private U instantiate(final @NonNull Class<U> targetClass) {
		final U instance;
		try {
			instance = ReflectionUtils.accessibleConstructor(targetClass).newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new ConverterException("Failed to instantiate object of type" + targetClass, e);
		}

		return instance;
	}

	private void copyProperties(final @NonNull T source, final @NonNull U target,
								final @Nullable String... ignoreProperties) {
		final List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : Collections.emptyList());

		for (PropertyDescriptor targetPd : targetPropertyDescriptors) {
			final Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null && !ignoreList.contains(targetPd.getName())) {
				final PropertyDescriptor sourcePd = sourcePropertyDescriptorsByName.get(targetPd.getName());
				if (sourcePd != null) {
					final Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null) {
						try {
							processCopyProperties(source, target, readMethod, writeMethod);
						} catch (final Exception e) {
							throw new ConverterException(
									"Could not copy property '" + targetPd.getName() + "' from source to target", e);
						}
					}
				}
			}
		}
	}

	private void processCopyProperties(final @NonNull T source, final @NonNull U target,
									   final @NonNull Method readMethod, final @NonNull Method writeMethod)
			throws InvocationTargetException, IllegalAccessException {
		final ResolvableType sourceResolvableType = ResolvableType.forMethodReturnType(readMethod);
		final ResolvableType targetResolvableType = ResolvableType.forMethodParameter(writeMethod, 0);

		boolean isAssignable =
				(sourceResolvableType.hasUnresolvableGenerics() || targetResolvableType.hasUnresolvableGenerics() ?
						ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType()) :
						targetResolvableType.isAssignableFrom(sourceResolvableType));

		final Object value;
		if (isAssignable) {
			value = readMethod.invoke(source);
		} else {
			value = modelMapper.map(readMethod.invoke(source), targetResolvableType.getType());
		}

		writeMethod.invoke(target, value);
	}

}
