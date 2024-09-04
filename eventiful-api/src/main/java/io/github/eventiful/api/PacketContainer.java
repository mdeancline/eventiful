package io.github.eventiful.api;

import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a contained Minecraft network packet that can be sent to or received from the server.
 *
 * @see PacketBridge#newPacket(String)
 * @see PacketBridge#newPacket(byte)
 * @since 1.0.0
 */
@ApiStatus.NonExtendable
public interface PacketContainer {

    /**
     * Writes a value to the specified index within the packet's data structure.
     * The packet's fields are organized sequentially, with each index corresponding to a specific field.
     * The value must match the expected type for that field.
     *
     * @param index the position in the packet's data structure where the value will be written
     * @param value the value to be written (e.g., an integer for player position, a string for chat messages)
     * @apiNote If you are working with a native Minecraft packet, refer to the
     * <a href="https://wiki.vg/Protocol">Minecraft protocol wiki</a> to determine what data should be written to each index.
     */
    void write(int index, Object value);

    /**
     * Retrieves the value stored at the specified index in the packet's data structure. This can be used to
     * inspect or modify the packet's contents before sending or after receiving it. The returned value will be
     * of the type corresponding to the field at that index.
     *
     * @param index the position in the packet's data structure from which to retrieve the value
     * @return the value stored at the specified index, cast to the expected type
     */
    Object get(int index);

    /**
     * Returns the internal representation of the packet, which typically corresponds to the actual network packet
     * used by the Minecraft server.
     *
     * @return the internal handle of the packet, which may vary based on the server version
     */
    Object getHandle();

    /**
     * Retrieves the packet's direction.
     *
     * @return the {@link PacketDirection} representing the packet's direction
     */
    PacketDirection getDirection();

    /**
     * Retrieves the current state of the packet.
     *
     * @return the {@link PacketState} representing the packet's current state in the network pipeline
     */
    PacketState getState();

    /**
     * Retrieves the name of this packet as defined by its internal representation. This is typically a recognizable
     * identifier, such as {@code PacketPlayOutChat} for chat messages.
     *
     * @return the name of the packet
     */
    String getName();
}
