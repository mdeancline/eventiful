package io.github.eventiful.plugin.event;

import org.bukkit.event.player.PlayerMoveEvent;

public interface PlayerMoveContext<T extends PlayerMoveEvent> {
    boolean appliesTo(PlayerMoveEvent event);

    T transform(PlayerMoveEvent event);
}
