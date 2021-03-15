package com.hurynovich.data_storage.cache.impl;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.hurynovich.data_storage.cache.Cache;

import java.util.ArrayList;
import java.util.List;

public class GenericCache<K, V> implements Cache<K, V> {

	private final com.github.benmanes.caffeine.cache.Cache<K, V> storage;

	public GenericCache() {
		storage = Caffeine.newBuilder().build();
	}

	@Override
	public V get(final K key) {
		return storage.getIfPresent(key);
	}

	@Override
	public V update(final K key, final V value) {
		storage.put(key, value);

		return value;
	}

	@Override
	public List<V> getAll() {
		return new ArrayList<>(storage.asMap().values());
	}

	@Override
	public void invalidate(final K key) {
		storage.invalidate(key);
	}

}
