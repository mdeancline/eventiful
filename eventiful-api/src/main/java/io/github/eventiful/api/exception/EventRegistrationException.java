package io.github.eventiful.api.exception;

/**
 * Thrown when an error occurs during the registration of an event or event system setup.
 *
 * @since 1.0.0
 */
public class EventRegistrationException extends EventException {
    private static final long serialVersionUID = 2288442292693219705L;

    /**
     * Constructs a new {@code EventRegistrationException} with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception.
     */
    public EventRegistrationException(final String message) {
        super(message);
    }

    /**
     * Constructs a new {@code EventRegistrationException} with the specified cause.
     *
     * @param cause the underlying exception that caused this exception to be thrown.
     */
    public EventRegistrationException(final Exception cause) {
        super(cause);
    }
}
