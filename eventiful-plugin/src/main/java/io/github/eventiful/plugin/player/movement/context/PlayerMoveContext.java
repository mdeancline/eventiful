package io.github.eventiful.plugin.player.movement.context;

import org.bukkit.event.player.PlayerMoveEvent;

public interface PlayerMoveContext<T extends PlayerMoveEvent> {
    boolean appliesTo(PlayerMoveEvent event);

    T transform(PlayerMoveEvent event);
}
