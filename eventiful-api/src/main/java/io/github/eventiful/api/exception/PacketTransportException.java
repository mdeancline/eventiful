package io.github.eventiful.api.exception;

public class PacketTransportException extends PacketException {
    private static final long serialVersionUID = -850335848204931236L;

    public PacketTransportException(final Exception cause) {
        super(cause);
    }
}
