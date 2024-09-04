package io.github.eventiful.api.exception;

public class PacketException extends RuntimeException {
    private static final long serialVersionUID = -5548660868176662861L;

    public PacketException(final String message) {
        super(message);
    }

    public PacketException(final String message, final Exception cause) {
        super(message, cause);
    }

    public PacketException(final Exception cause) {
        super(cause);
    }
}
