package io.github.eventiful.plugin;

import io.github.eventiful.plugin.registration.EventRegistration;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EventLogger {
    private final Logger logger;

    public EventLogger(final Logger logger) {
        this.logger = logger;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    public void logDeprecation(final EventRegistration<?> registration) {
        final StringBuilder builder = new StringBuilder("Detected registration of deprecated event ")
                .append(registration.getEventType().getName())
                .append(" from ")
                .append(registration.getListener().getClass().getName());

        logger.log(Level.WARNING, builder.toString());
    }
}
