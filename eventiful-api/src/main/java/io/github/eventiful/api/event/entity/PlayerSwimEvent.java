package io.github.eventiful.api.event.entity;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when a {@code Player} is swimming in liquid.
 *
 * @since 1.0.0
 */
public class PlayerSwimEvent extends PlayerMoveEvent {

    @ApiStatus.Internal
    public PlayerSwimEvent(final Player player, final Location from, final Location to) {
        super(player, from, to);
    }
}
