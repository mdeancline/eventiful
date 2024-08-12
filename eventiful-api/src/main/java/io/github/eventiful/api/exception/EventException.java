package io.github.eventiful.api.exception;

/**
 * @since 1.0.0
 */
public class EventException extends RuntimeException {
    private static final long serialVersionUID = -8846723334655955872L;

    public EventException(final String message) {
        super(message);
    }

    public EventException(final Exception cause) {
        super(cause);
    }
}
