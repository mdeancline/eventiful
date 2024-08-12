package io.github.eventiful.api.listener.decorator;

import io.github.eventiful.api.listener.EventListener;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * An {@code EventListener} decorator that restricts it to handling only a specified set of {@code Event} types. It
 * ensures that the decorated {@code EventListener} will only respond to events of the given types.
 *
 * @param <T> {@inheritDoc}
 * @see IdentityEventInclusion
 * @since 1.0.0
 */
public class EventInclusion<T extends Event> extends EventCircumscription<T> {

    /**
     * Constructs a new {@code EventInclusion} instance.
     *
     * @param listener     the {@code EventListener} to be modified
     * @param boundedTypes the types of {@code Event} that the modified {@code EventListener} should handle
     */
    @SafeVarargs
    public EventInclusion(@NotNull final EventListener<T> listener, final Class<? extends T>... boundedTypes) {
        super(listener, boundedTypes);
    }

    @Override
    public void handle(final T event) {
        final Class<? extends Event> type = event.getClass();

        for (final Class<?> boundedType : circumscribedTypes) {
            if (boundedType.isInstance(type)) {
                listener.handle(event);
                break;
            }
        }
    }
}
