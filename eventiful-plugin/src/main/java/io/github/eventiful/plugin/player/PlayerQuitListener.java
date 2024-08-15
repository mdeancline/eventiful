package io.github.eventiful.plugin.player;

import io.github.eventiful.api.listener.EventListener;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PlayerQuitListener implements EventListener<PlayerQuitEvent> {
    private final List<PlayerAuditor> auditors = new ObjectArrayList<>(0);

    @Override
    public void handle(final PlayerQuitEvent event) {
        for (final PlayerAuditor auditor : auditors)
            auditor.removePlayer(event.getPlayer());
    }

    public void addAuditor(final PlayerAuditor auditor) {
        auditors.add(auditor);
    }
}
