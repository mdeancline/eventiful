package io.github.eventiful.plugin.registration;

import io.github.eventiful.api.EventToken;

public interface EventTokenProvider {
    EventToken createToken(EventRegistration<?> registration);
}
