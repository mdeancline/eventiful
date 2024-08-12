package io.github.eventiful.api.listener.decorator;

import io.github.eventiful.api.listener.EventListener;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * An {@code EventListener} decorator that excludes specified {@link Event} types. It ensures that the decorated
 * {@code EventListener} will <b>not</b> respond to events of the given types.
 *
 * @param <T> {@inheritDoc}
 * @see IdentityEventExclusion
 * @since 1.0.0
 */
public class EventExclusion<T extends Event> extends EventCircumscription<T> {

    /**
     * Constructs a new {@code EventExclusion} decorator.
     *
     * @param listener     the {@code EventListener} to be decorated
     * @param ignoredTypes the {@code Event} types that the decorated {@code EventListener} should ignore
     */
    @SafeVarargs
    public EventExclusion(@NotNull final EventListener<T> listener, final Class<? extends T>... ignoredTypes) {
        super(listener, ignoredTypes);
    }

    @Override
    public void handle(final T event) {
        final Class<? extends Event> type = event.getClass();

        for (final Class<?> ignoredType : circumscribedTypes)
            if (ignoredType.isInstance(type)) return;

        listener.handle(event);
    }
}
