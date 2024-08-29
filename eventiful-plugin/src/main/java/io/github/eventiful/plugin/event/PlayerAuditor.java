package io.github.eventiful.plugin.event;

import org.bukkit.entity.Player;

public interface PlayerAuditor {
    void addPlayer(Player player);

    void removePlayer(Player player);
}
