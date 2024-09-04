package io.github.eventiful.api.exception;

public class PacketCreationException extends PacketException {
    private static final long serialVersionUID = -4720232740783448822L;

    public PacketCreationException(final Exception cause) {
        super(cause);
    }

    public PacketCreationException(final String message, final Exception cause) {
        super(message, cause);
    }

    public PacketCreationException(final String message) {
        super(message);
    }
}
