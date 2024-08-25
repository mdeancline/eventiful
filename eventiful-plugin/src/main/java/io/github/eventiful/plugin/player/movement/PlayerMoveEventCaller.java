package io.github.eventiful.plugin.player.movement;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.listener.CancellableEventListener;
import io.github.eventiful.plugin.player.movement.context.PlayerMoveContext;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveEventCaller extends CancellableEventListener<PlayerMoveEvent> {
    private final EventBus eventBus;
    private final PlayerMoveContext<?>[] contexts;

    public PlayerMoveEventCaller(final EventBus eventBus, final PlayerMoveContext<?>... contexts) {
        this.eventBus = eventBus;
        this.contexts = contexts;
    }

    @Override
    public void handleCancellable(final PlayerMoveEvent event) {
        for (final PlayerMoveContext<?> context : contexts) {
            if (context.appliesTo(event)) {
                final PlayerMoveEvent transformed = context.transform(event);
                eventBus.dispatch(transformed);

                if (transformed.isCancelled()) event.setCancelled(true);
            }
        }
    }
}
