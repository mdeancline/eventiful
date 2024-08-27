package io.github.eventiful.api.event.player;

import io.github.eventiful.api.event.armor.ArmorEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when a {@code Player} unequips an item formerly used as armor.
 *
 * @since 1.0.0
 */
public class PlayerArmorUnequipEvent extends ArmorEvent<Player> implements Cancellable {
    private final Cause cause;
    private boolean cancel;

    @ApiStatus.Internal
    public PlayerArmorUnequipEvent(final Player player, final EquipmentSlot slot, final Cause cause, final ItemStack armorItem) {
        super(player, slot);
        this.cause = cause;
        this.armorItem = armorItem;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }

    public Cause getCause() {
        return cause;
    }

    /**
     * Represents various situations for a {@code Player} equipping a piece of armor.
     */
    public enum Cause {
        BROKE,

        DEATH,

        DRAG,

        SHIFT_CLICK,
    }
}
