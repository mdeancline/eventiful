package io.github.eventiful.plugin;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.EventToken;
import io.github.eventiful.plugin.registration.EventRegistration;

import java.util.concurrent.CompletionStage;

public interface ServerEventBus extends EventBus {
    CompletionStage<EventToken> register(EventRegistration<?> registration);

    void setServerLoaded(boolean serverLoaded);
}
