package io.github.eventiful.api.event.server;

import org.bukkit.Server;

/**
 * Represents a network packet that can be sent to or received from the {@link Server}. The manipulation of its contents
 * follows the conventions of the Minecraft protocol as described in the
 * <a href="https://wiki.vg/Protocol">protocol wiki</a>.
 *
 * @since 1.0.0
 */
public interface Packet {

    /**
     * Sets a value at the specified index within the packet's data structure.
     *
     * @param index the position in the packet's data structure where the value should be set
     * @param value the value to be written into the packet
     */
    void write(int index, Object value);

    /**
     * Retrieves the value stored at the specified index within the packet's data structure.
     *
     * @param index the position in the packet's data structure from which to retrieve the value
     * @return the value at the specified index
     */
    Object get(int index);

    /**
     * Returns the internal representation or handle of the packet. This handle may correspond to the actual network
     * packet used by the server.
     *
     * @return the internal handle or representation of the packet
     */
    Object getHandle();

    /**
     * Retrieves the scope of the packet.
     *
     * @return the {@link PacketScope} representing the packet's scope
     */
    PacketScope getScope();

    /**
     * Retrieves the current state of the packet.
     *
     * @return the {@link PacketState} representing the packet's state
     */
    PacketState getState();

    /**
     * Returns the name of this packet, typically matching its Minecraft protocol name.
     *
     * @return the name of the packet as defined in the protocol
     */
    String getName();
}
