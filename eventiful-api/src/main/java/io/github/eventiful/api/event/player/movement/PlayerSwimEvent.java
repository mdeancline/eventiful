package io.github.eventiful.api.event.player.movement;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Called when a {@code Player} is swimming in liquid.
 *
 * @since 1.0.0
 */
public class PlayerSwimEvent extends PlayerMoveEvent {

    /**
     * Constructs a new {@code PlayerSwimEvent}.
     *
     * @param player the {@code Player} who is swimming
     * @param from the {@code Location} where the swimming started
     * @param to the {@code Location} where the swimming ended
     */
    public PlayerSwimEvent(final Player player, final Location from, final Location to) {
        super(player, from, to);
    }
}
