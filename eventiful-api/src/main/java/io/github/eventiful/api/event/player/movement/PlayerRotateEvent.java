package io.github.eventiful.api.event.player.movement;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Called when a {@code Player} rotates their body.
 *
 * @since 1.0.0
 */
public class PlayerRotateEvent extends PlayerMoveEvent {

    /**
     * Constructs a new {@code PlayerRotateEvent}.
     *
     * @param player the {@code Player} who jumped
     * @param from the {@code Location} where the jump started
     * @param to the {@code Location} where the jump ended
     */
    public PlayerRotateEvent(final Player player, final Location from, final Location to) {
        super(player, from, to);
    }
}
