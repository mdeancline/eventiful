package io.github.eventiful.api.event.server;

import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicesManager;

/**
 * Represents a stream of packets that can be dispatched to players or managed in other ways.
 *
 * @apiNote The only valid way to retrieve a {@code PacketStream} instance is by loading the relevant service from the
 * {@link ServicesManager}.
 * <pre>
 * {@code
 * PacketStream stream = Bukkit.getServicesManager().load(PacketStream.class);
 * }
 * </pre>
 * @since 1.0.0
 */
public interface PacketStream {

    /**
     * Dispatches a packet to all relevant players.
     *
     * @param packet the packet to be dispatched
     */
    void dispatch(Packet packet);

    /**
     * Dispatches a packet to a specific player.
     *
     * @param packet the packet to be dispatched
     * @param player the player to whom the packet will be sent
     */
    void dispatch(Packet packet, Player player);

    /**
     * Creates a new packet based on a Minecraft packet name.
     *
     * @param name the name of the Minecraft packet
     * @return the newly created packet
     */
    Packet newPacket(String name, Object... parameters);
}
