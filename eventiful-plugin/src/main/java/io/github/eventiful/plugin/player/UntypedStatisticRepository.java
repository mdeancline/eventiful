package io.github.eventiful.plugin.player;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.UUID;

public final class UntypedStatisticRepository implements PlayerAuditor {
    private final Map<UUID, TObjectIntMap<Statistic>> statistics = new IdentityHashMap<>();

    @Override
    public void addPlayer(final Player player) {
        final TObjectIntMap<Statistic> preciseStatistics = new TObjectIntHashMap<>();
        statistics.put(player.getUniqueId(), preciseStatistics);

        for (final Statistic statistic : Statistic.values())
            if (statistic.getType() == Statistic.Type.UNTYPED)
                preciseStatistics.put(statistic, player.getStatistic(statistic));
    }

    @Override
    public void removePlayer(final Player player) {
        statistics.remove(player.getUniqueId());
    }

    public int get(final Player player, final Statistic statistic) {
        return statistics.get(player.getUniqueId()).get(statistic);
    }

    public void save(final PlayerStatisticIncrementEvent event) {
        final Statistic statistic = event.getStatistic();

        if (statistic.getType() == Statistic.Type.UNTYPED) {
            final TObjectIntMap<Statistic> preciseStatistics = statistics.get(event.getPlayer().getUniqueId());

            if (preciseStatistics != null)
                preciseStatistics.put(statistic, event.getNewValue());
        }
    }
}
