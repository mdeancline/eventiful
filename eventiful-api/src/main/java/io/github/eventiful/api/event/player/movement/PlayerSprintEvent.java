package io.github.eventiful.api.event.player.movement;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when a {@code Player} is sprinting.
 *
 * @since 1.0.0
 */
public class PlayerSprintEvent extends PlayerMoveEvent {

    @ApiStatus.Internal
    public PlayerSprintEvent(final Player player, final Location from, final Location to) {
        super(player, from, to);
    }
}
