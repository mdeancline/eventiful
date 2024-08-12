package io.github.eventiful.plugin.player;

import io.github.eventiful.api.listener.EventListener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerJoinListener implements EventListener<PlayerJoinEvent> {
    private final List<PlayerAuditor> auditors = new ArrayList<>(0);

    @Override
    public void handle(final PlayerJoinEvent event) {
        for (final PlayerAuditor auditor : auditors)
            auditor.addPlayer(event.getPlayer());
    }

    public void addAuditor(final PlayerAuditor auditor) {
        auditors.add(auditor);
    }
}
