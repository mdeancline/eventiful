package io.github.eventiful.api.event.player.movement;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Called when a {@code Player} simultaneously sneaks and moves.
 *
 * @since 1.0.0
 */
public class PlayerSneakEvent extends PlayerMoveEvent {

    /**
     * Constructs a new {@code PlayerSneakEvent}.
     *
     * @param player the {@code Player} who is sneaking
     * @param from the {@code Location} where the sneaking started
     * @param to the {@code Location} where the sneaking ended
     */
    public PlayerSneakEvent(final Player player, final Location from, final Location to) {
        super(player, from, to);
    }
}
