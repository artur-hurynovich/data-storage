package com.hurynovich.data_storage.event;

import com.hurynovich.data_storage.event.model.Event;
import org.springframework.lang.NonNull;

public interface EventListener<T> {

	void onEvent(@NonNull Event<T> event);
}
