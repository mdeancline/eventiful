package io.github.eventiful.api;

/**
 * Describes the direction of a packet's flow within the Minecraft protocol communication between the server and a
 * player.
 *
 * @since 1.0.0
 */
public enum PacketDirection {

    /**
     * Indicates that the packet is inbound, meaning it was received from the client.
     */
    CLIENT,

    /**
     * Indicates that the packet is outbound, meaning it was sent from the server.
     */
    SERVER;

    /**
     * Retrieves the opposite direction of this packet flow. If the current direction is {@code CLIENT},
     * it will return {@code SERVER}, and vice versa.
     *
     * @return the opposite {@link PacketDirection} of the current direction
     */
    public PacketDirection getOpposite() {
        return this == CLIENT ? SERVER : CLIENT;
    }
}
