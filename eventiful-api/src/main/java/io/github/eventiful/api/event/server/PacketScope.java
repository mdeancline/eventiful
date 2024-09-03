package io.github.eventiful.api.event.server;

import org.bukkit.Server;
import org.bukkit.entity.Player;

/**
 * Describes the direction of a {@link Packet}'s flow within the network communication between the {@link Server} and a
 * {@link Player} (client).
 *
 * @since 1.0.0
 */
public enum PacketScope {

    /**
     * Indicates that the packet is inbound, meaning it was received from the client.
     */
    CLIENT,

    /**
     * Indicates that the packet is outbound, meaning it was sent from the server.
     */
    SERVER,
}
