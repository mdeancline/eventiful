package io.github.eventiful.plugin.player;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

import java.util.Map;
import java.util.UUID;

public final class UntypedStatisticRepository implements PlayerAuditor {
    private final Map<UUID, Object2IntMap<Statistic>> statistics = new Object2ObjectOpenHashMap<>();

    @Override
    public void addPlayer(final Player player) {
        final Object2IntMap<Statistic> preciseStatistics = new Object2IntOpenHashMap<>();
        statistics.put(player.getUniqueId(), preciseStatistics);

        for (final Statistic statistic : Statistic.values())
            if (statistic.getType() == Statistic.Type.UNTYPED)
                preciseStatistics.put(statistic, player.getStatistic(statistic));
    }

    @Override
    public void removePlayer(final Player player) {
        statistics.remove(player.getUniqueId());
    }

    @SuppressWarnings("deprecation")
    public int get(final Player player, final Statistic statistic) {
        return statistics.get(player.getUniqueId()).get(statistic);
    }

    public void save(final PlayerStatisticIncrementEvent event) {
        final Statistic statistic = event.getStatistic();

        if (statistic.getType() == Statistic.Type.UNTYPED) {
            final Object2IntMap<Statistic> preciseStatistics = statistics.get(event.getPlayer().getUniqueId());
            preciseStatistics.put(statistic, event.getNewValue());
        }
    }
}
