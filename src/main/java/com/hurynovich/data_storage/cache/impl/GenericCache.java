package com.hurynovich.data_storage.cache.impl;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.hurynovich.data_storage.cache.Cache;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.Optional;

class GenericCache<K, V> implements Cache<K, V> {

	private final com.github.benmanes.caffeine.cache.Cache<K, V> underlying =
			Caffeine.newBuilder().build();

	@Override
	public void store(final @NonNull K key, final @NonNull V value) {
		underlying.put(key, Objects.requireNonNull(value));
	}

	@Override
	public Optional<V> get(final @NonNull K key) {
		return Optional.ofNullable(underlying.getIfPresent(key));
	}

	@Override
	public boolean contains(final @NonNull K key) {
		return underlying.asMap().containsKey(key);
	}

	@Override
	public void invalidate(final @NonNull K key) {
		underlying.invalidate(key);
	}

}
