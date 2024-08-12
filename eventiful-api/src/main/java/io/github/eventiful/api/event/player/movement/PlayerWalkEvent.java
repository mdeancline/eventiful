package io.github.eventiful.api.event.player.movement;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Called when a {@code Player} is walking.
 *
 * @since 1.0.0
 */
public class PlayerWalkEvent extends PlayerMoveEvent {

    /**
     * Constructs a new {@code PlayerWalkEvent}.
     *
     * @param player the {@code Player} who is walking
     * @param from the {@code Location} where the walking started
     * @param to the {@code Location} where the walking ended
     */
    public PlayerWalkEvent(final Player player, final Location from, final Location to) {
        super(player, from, to);
    }
}
