package io.github.eventiful.plugin.player.movement.context;

import io.github.eventiful.api.event.player.movement.PlayerSprintEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerSprintContext implements PlayerMoveContext<PlayerSprintEvent> {
    @Override
    public boolean appliesTo(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        return player.isSprinting() && !player.isFlying();
    }

    @Override
    public PlayerSprintEvent transform(final PlayerMoveEvent event) {
        return new PlayerSprintEvent(event.getPlayer(), event.getFrom(), event.getTo());
    }
}
