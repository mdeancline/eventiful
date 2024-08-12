package io.github.eventiful.plugin.player;

import io.github.eventiful.api.listener.EventListener;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

@AllArgsConstructor
public class PlayerStatisticIncrementListener implements EventListener<PlayerStatisticIncrementEvent> {
    private final UntypedStatisticRepository repository;

    @Override
    public void handle(final PlayerStatisticIncrementEvent event) {
        repository.save(event);
    }

    @Override
    public EventPriority getPriority() {
        return EventPriority.LOWEST;
    }
}
