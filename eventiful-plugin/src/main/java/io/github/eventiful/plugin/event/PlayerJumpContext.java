package io.github.eventiful.plugin.event;

import io.github.eventiful.api.event.entity.PlayerJumpEvent;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.AllArgsConstructor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

// https://www.spigotmc.org/threads/accurate-playerjumpevent-do-actions-when-players-jump.138946/
@AllArgsConstructor
public class PlayerJumpContext implements PlayerMoveContext<PlayerJumpEvent> {
    private static final double BLOCK_SLAB_HEIGHT = 0.5;
    private static final double BLOCK_STEP_HEIGHT_MIN = 0.035;
    private static final double BLOCK_STEP_HEIGHT_MAX = 0.037;
    private static final double BLOCK_HEIGHT_MIN = 0.116;
    private static final double BLOCK_HEIGHT_MAX = 0.118;

    private final Object2IntMap<UUID> jumps = new Object2IntOpenHashMap<>();
    private final UntypedStatisticRepository repository;

    @SuppressWarnings("deprecation")
    @Override
    public boolean appliesTo(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (player.isFlying()) return false;

        final UUID uuid = player.getUniqueId();
        final int current = repository.get(player, Statistic.JUMP);
        final int last = jumps.get(uuid);
        if (last != current) jumps.put(uuid, current);

        return isActualJump(event);
    }

    private boolean isActualJump(final PlayerMoveEvent event) {
        final double newY = event.getTo().getY();
        final double oldY = event.getFrom().getY();
        return oldY < newY && !isAffectedByBlock(newY - oldY);
    }

    private boolean isAffectedByBlock(final double yDif) {
        return yDif == BLOCK_SLAB_HEIGHT
                || (yDif > BLOCK_STEP_HEIGHT_MIN && yDif < BLOCK_STEP_HEIGHT_MAX)
                || (yDif > BLOCK_HEIGHT_MIN && yDif < BLOCK_HEIGHT_MAX);
    }

    @Override
    public PlayerJumpEvent transform(final PlayerMoveEvent event) {
        return new PlayerJumpEvent(event.getPlayer(), event.getFrom(), event.getTo());
    }
}
