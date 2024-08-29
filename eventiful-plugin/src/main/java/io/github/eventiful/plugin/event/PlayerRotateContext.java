package io.github.eventiful.plugin.event;

import io.github.eventiful.api.event.entity.PlayerRotateEvent;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerRotateContext implements PlayerMoveContext<PlayerRotateEvent> {
    @Override
    public boolean appliesTo(final PlayerMoveEvent event) {
        final Location from = event.getFrom();
        final Location to = event.getTo();
        return from.getYaw() != to.getYaw() || from.getPitch() != to.getPitch();
    }

    @Override
    public PlayerRotateEvent transform(final PlayerMoveEvent event) {
        return new PlayerRotateEvent(event.getPlayer(), event.getFrom(), event.getTo());
    }
}
