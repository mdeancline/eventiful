package io.github.eventiful.api.event.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when a {@code Player} simultaneously sneaks and moves.
 *
 * @since 1.0.0
 */
public class PlayerSneakEvent extends PlayerMoveEvent {

    @ApiStatus.Internal
    public PlayerSneakEvent(final Player player, final Location from, final Location to) {
        super(player, from, to);
    }
}
