package io.github.eventiful.api.exception;

import org.bukkit.event.Event;

/**
 * Thrown when an {@link Event} is triggered in an incorrect thread or state.
 * For instance, this exception is thrown if an asynchronous event is triggered on the main thread,
 * or if a synchronous event is triggered asynchronously.
 *
 * @since 1.0.0
 */
public class EventConcurrencyException extends EventException {
    private static final long serialVersionUID = -2070124872114185740L;

    /**
     * Constructs a new {@code EventConcurrencyException} with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public EventConcurrencyException(final String message) {
        super(message);
    }

    /**
     * Constructs a new {@code EventConcurrencyException} with the specified cause.
     *
     * @param cause The underlying exception that caused this exception to be thrown.
     */
    public EventConcurrencyException(final Exception cause) {
        super(cause);
    }
}
