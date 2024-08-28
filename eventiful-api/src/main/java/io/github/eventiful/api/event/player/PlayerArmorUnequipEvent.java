package io.github.eventiful.api.event.player;

import io.github.eventiful.api.event.ArmorEvent;
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
    private boolean cancel;

    @ApiStatus.Internal
    public PlayerArmorUnequipEvent(final Player player, final EquipmentSlot slot, final ItemStack oldItem, final ItemStack newItem) {
        super(player, slot, oldItem, newItem);
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
}
