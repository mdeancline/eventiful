package io.github.eventiful.api.listener.decorator;

import io.github.eventiful.api.listener.EventListener;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * An {@link EventListener} decorator that restricts it to excluding only the specified set of {@link Event} types. Unlike
 * {@link EventExclusion}, it ensures that the decorated {@code EventListener} will ignore events that exactly match
 * the given types, not their derivative types.
 *
 * @since 1.0.0
 * @param <T> {@inheritDoc}
 */
public class IdentityEventExclusion<T extends Event> extends EventCircumscription<T> {

    /**
     * Constructs a new {@code IdentityEventExclusion} instance.
     *
     * @param listener the {@code EventListener} to be modified
     * @param ignoredTypes the types of {@code Event} that the modified {@code EventListener} should strictly ignore
     */
    @SafeVarargs
    public IdentityEventExclusion(@NotNull final EventListener<T> listener, final Class<? extends T>... ignoredTypes) {
        super(listener, ignoredTypes);
    }

    @Override
    public void handle(final T event) {
        if (!circumscribedTypes.contains(event.getClass()))
            listener.handle(event);
    }
}
