package io.github.eventiful.plugin.player.movement.context;

import io.github.eventiful.api.event.player.movement.PlayerSneakEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerSneakContext implements PlayerMoveContext<PlayerSneakEvent> {
    @Override
    public boolean appliesTo(final PlayerMoveEvent event) {
        return event.getPlayer().isSneaking();
    }

    @Override
    public PlayerSneakEvent transform(final PlayerMoveEvent event) {
        return new PlayerSneakEvent(event.getPlayer(), event.getFrom(), event.getTo());
    }
}
