package com.hurynovich.data_storage.service.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.Identified;
import com.hurynovich.data_storage.service.DTOService;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class GenericDTOService<T extends Identified<I>, U extends Identified<I>, I extends Serializable>
		implements DTOService<T, I> {

	private final PagingAndSortingRepository<U, I> repository;

	private final Converter<T, U> converterToPersistent;

	private final Converter<U, T> converterFromPersistent;

	public GenericDTOService(final PagingAndSortingRepository<U, I> repository,
							 final Converter<T, U> converterToPersistent,
							 final Converter<U, T> converterFromPersistent) {
		this.repository = repository;
		this.converterToPersistent = converterToPersistent;
		this.converterFromPersistent = converterFromPersistent;
	}

	@Override
	public T save(final T t) {
		return converterFromPersistent.convert(repository.save(converterToPersistent.convert(t)));
	}

	@Override
	public Optional<T> findById(final I id) {
		final Optional<U> optionalResult = repository.findById(id);

		return optionalResult.map(converterFromPersistent::convert);
	}

	@Override
	public List<T> findAll() {
		return converterFromPersistent.convertAll(repository.findAll());
	}

	@Override
	public T update(final T t) {
		return save(t);
	}

	@Override
	public void deleteById(final I id) {
		repository.deleteById(id);
	}

}
