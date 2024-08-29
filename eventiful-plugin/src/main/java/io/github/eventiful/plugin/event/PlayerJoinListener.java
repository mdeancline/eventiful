package io.github.eventiful.plugin.event;

import io.github.eventiful.api.listener.EventListener;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class PlayerJoinListener implements EventListener<PlayerJoinEvent> {
    private final List<PlayerAuditor> auditors = new ObjectArrayList<>(0);

    @Override
    public void handle(final PlayerJoinEvent event) {
        for (final PlayerAuditor auditor : auditors)
            auditor.addPlayer(event.getPlayer());
    }

    public void addAuditor(final PlayerAuditor auditor) {
        auditors.add(auditor);
    }
}
