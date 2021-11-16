package com.hurynovich.data_storage.event.impl;

import com.hurynovich.data_storage.event.EventHandler;
import com.hurynovich.data_storage.event.EventListener;
import com.hurynovich.data_storage.event.model.Event;
import com.hurynovich.data_storage.event.model.EventType;
import com.hurynovich.data_storage.model.AbstractDTO;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Executors;

class GenericEventListener<T extends AbstractDTO<?>> implements EventListener<T> {

	private final BasicThreadFactory threadFactory = new BasicThreadFactory.
			Builder().
			namingPattern("DTOServiceEventListener-%d").
			build();

	private final Map<EventType, EventHandler<T>> handlersByEventType;

	public GenericEventListener(
			final @NonNull Map<EventType, EventHandler<T>> handlersByEventType) {
		this.handlersByEventType = Collections.unmodifiableMap(handlersByEventType);
	}

	@Override
	public void onEvent(final @NonNull Event<T> event) {
		Executors.newSingleThreadExecutor(threadFactory).submit(() -> handleEvent(event));
	}

	private void handleEvent(final @NonNull Event<T> event) {
		final EventHandler<T> handler = handlersByEventType.get(event.getEventType());
		if (handler != null) {
			handler.handle(event);
		}
	}

}
