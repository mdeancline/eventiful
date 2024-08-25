package io.github.eventiful.api.listener.decorator;

import io.github.eventiful.api.listener.EventListener;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * An {@link EventListener} decorator that restricts it to including only the specified set of {@link Event} types.
 * Unlike {@link EventInclusion}, it ensures that the decorated {@code EventListener} will only respond to events that
 * exactly match the given types, not their derivative types.
 *
 * @since 1.0.0
 * @param <T> {@inheritDoc}
 */
public class IdentityEventInclusion<T extends Event> extends EventCircumscription<T> {

    /**
     * Constructs a new {@code IdentityEventInclusion} instance.
     *
     * @param listener the {@code EventListener} to be modified
     * @param boundedTypes the exact types of {@code Event} that the modified {@code EventListener} should handle
     */
    @SafeVarargs
    public IdentityEventInclusion(@NotNull final EventListener<T> listener, final Class<? extends T>... boundedTypes) {
        super(listener, boundedTypes);
    }

    @Override
    public void handle(final T event) {
        if (circumscribedTypes.contains(event.getClass()))
            source.handle(event);
    }
}
