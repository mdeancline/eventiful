package io.github.eventiful.plugin.event;

import io.github.eventiful.api.event.entity.PlayerSprintEvent;
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
