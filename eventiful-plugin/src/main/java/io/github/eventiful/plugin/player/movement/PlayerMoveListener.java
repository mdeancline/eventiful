package io.github.eventiful.plugin.player.movement;

import io.github.eventiful.api.listener.CancellableEventListener;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener extends CancellableEventListener<PlayerMoveEvent> {
    private final PlayerMoveEventCaller playerMoveCaller;

    public PlayerMoveListener(final PlayerMoveEventCaller playerMoveCaller) {
        this.playerMoveCaller = playerMoveCaller;
    }

    @Override
    protected void handleCancellable(final PlayerMoveEvent event) {
        playerMoveCaller.call(event);
    }

    @Override
    public EventPriority getPriority() {
        return EventPriority.LOWEST;
    }
}
