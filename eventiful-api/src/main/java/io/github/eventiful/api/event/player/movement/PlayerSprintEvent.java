package io.github.eventiful.api.event.player.movement;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Called when a {@code Player} is sprinting.
 *
 * @since 1.0.0
 */
public class PlayerSprintEvent extends PlayerMoveEvent {

    /**
     * Constructs a new {@code PlayerSprintEvent}.
     *
     * @param player the {@code Player} who is sprinting
     * @param from the {@code Location} where the sprinting started
     * @param to the {@code Location} where the sprinting ended
     */
    public PlayerSprintEvent(final Player player, final Location from, final Location to) {
        super(player, from, to);
    }
}
