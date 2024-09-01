package io.github.eventiful.plugin;

import io.github.eventiful.api.EventBus;
import lombok.Setter;
import lombok.experimental.Delegate;

@Setter
public class EventBusReference implements EventBus {
    @Delegate
    private EventBus reference;

    public boolean hasReference() {
        return reference != null;
    }
}
