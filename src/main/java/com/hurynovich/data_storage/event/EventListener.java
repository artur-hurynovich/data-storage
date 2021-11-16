package com.hurynovich.data_storage.event;

import com.hurynovich.data_storage.event.model.Event;
import com.hurynovich.data_storage.model.AbstractDTO;
import org.springframework.lang.NonNull;

public interface EventListener<T extends AbstractDTO<?>> {

	void onEvent(@NonNull Event<T> event);

}
