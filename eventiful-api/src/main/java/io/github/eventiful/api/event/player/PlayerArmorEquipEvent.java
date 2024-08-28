package io.github.eventiful.api.event.player;

import io.github.eventiful.api.event.ArmorEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when a {@code Player} equips an item to be used as armor.
 *
 * @since 1.0.0
 */
public class PlayerArmorEquipEvent extends ArmorEvent<Player> implements Cancellable {
    private boolean cancelled;

    @ApiStatus.Internal
    public PlayerArmorEquipEvent(final Player who, final EquipmentSlot slot, final ItemStack oldItem, final ItemStack newItem) {
        super(who, slot, oldItem, newItem);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
