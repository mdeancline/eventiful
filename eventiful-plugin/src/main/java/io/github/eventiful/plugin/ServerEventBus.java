package io.github.eventiful.plugin;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.EventToken;
import io.github.eventiful.plugin.registration.EventRegistration;

public interface ServerEventBus extends EventBus {
    EventToken register(EventRegistration<?> registration);
}
