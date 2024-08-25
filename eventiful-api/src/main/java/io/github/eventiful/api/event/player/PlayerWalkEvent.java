package io.github.eventiful.api.event.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when a {@code Player} is walking.
 *
 * @since 1.0.0
 */
public class PlayerWalkEvent extends PlayerMoveEvent {

    @ApiStatus.Internal
    public PlayerWalkEvent(final Player player, final Location from, final Location to) {
        super(player, from, to);
    }
}
