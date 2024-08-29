package io.github.eventiful.plugin.event;

import io.github.eventiful.api.event.entity.PlayerSneakEvent;
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
