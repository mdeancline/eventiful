package io.github.eventiful.api.listener;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * Handles any {@link Event} that implements the {@link Cancellable} interface.
 * <p>
 * If an incoming {@code Event} of this type is cancelled before being passed to this {@code EventListener}, the logic
 * in {@link CancellableEventListener#handleCancellable(Event)} will not be executed.
 *
 * @param <T> {@inheritDoc} and implements {@code Cancellable}
 * @apiNote If {@link CancellableEventListener#isIgnoringCancelled()}, which can be overridden,
 * returns {@code false} when called in {@link CancellableEventListener#handle(Event)},
 * the listener will not honor the event's cancelled state and will proceed to handle it as usual.
 * @since 1.0.0
 */
public abstract class CancellableEventListener<T extends Event & Cancellable> implements EventListener<T> {
    @Override
    public final void handle(final T event) {
        if (event.isCancelled() && isIgnoringCancelled()) return;
        handleCancellable(event);
    }

    /**
     * Template method to be implemented by subclasses to handle the incoming {@link Cancellable} event.
     *
     * @param event the incoming event to be processed
     */
    protected abstract void handleCancellable(T event);

    /**
     * Determines whether this {@code EventListener} should ignore events that are already cancelled.
     *
     * @return {@code true} if cancelled events should be ignored, {@code false} otherwise
     */
    protected boolean isIgnoringCancelled() {
        return false;
    }
}
