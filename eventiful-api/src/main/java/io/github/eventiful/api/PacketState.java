package io.github.eventiful.api;

import org.bukkit.entity.Player;

/**
 * Represents the different states of packets in the Minecraft server communication process.
 */
public enum PacketState {

    /**
     * Indicates that a packet is in the handshaking phase, which involves establishing a connection to the server.
     */
    HANDSHAKING,

    /**
     * Indicates that a packet is involved in the login process, which includes initializing {@link Player} data
     * upon their login to the server.
     */
    LOGIN,

    /**
     * Indicates that a packet is used during gameplay to update the client with game events and world changes.
     */
    PLAY,

    /**
     * Indicates that a packet is used for responding to status requests, such as client queries about the server
     * status and responses to HTTP status requests.
     */
    STATUS
}
