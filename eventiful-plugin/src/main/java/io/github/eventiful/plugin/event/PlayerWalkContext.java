package io.github.eventiful.plugin.event;

import io.github.eventiful.api.event.entity.PlayerWalkEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerWalkContext implements PlayerMoveContext<PlayerWalkEvent> {
    @Override
    public boolean appliesTo(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (player.isSprinting() || player.isFlying()) return false;

        final Location from = event.getFrom();
        final Location to = event.getTo();
        return from.getX() != to.getX() || from.getZ() != to.getZ();
    }

    @Override
    public PlayerWalkEvent transform(final PlayerMoveEvent event) {
        return new PlayerWalkEvent(event.getPlayer(), event.getFrom(), event.getTo());
    }
}
