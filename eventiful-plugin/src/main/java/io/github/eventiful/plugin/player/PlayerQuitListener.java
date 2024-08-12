package io.github.eventiful.plugin.player;

import io.github.eventiful.api.listener.EventListener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerQuitListener implements EventListener<PlayerQuitEvent> {
    private final List<PlayerAuditor> auditors = new ArrayList<>(0);

    @Override
    public void handle(final PlayerQuitEvent event) {
        for (final PlayerAuditor auditor : auditors)
            auditor.removePlayer(event.getPlayer());
    }

    public void addAuditor(final PlayerAuditor auditor) {
        auditors.add(auditor);
    }
}
