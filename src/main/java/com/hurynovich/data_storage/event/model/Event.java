package com.hurynovich.data_storage.event.model;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class Event<T> {

	private final T payload;

	private final EventType eventType;

	public Event(final @NonNull T payload, final @NonNull EventType eventType) {
		this.payload = Objects.requireNonNull(payload);
		this.eventType = Objects.requireNonNull(eventType);
	}

	public T getPayload() {
		return payload;
	}

	public EventType getEventType() {
		return eventType;
	}
}
