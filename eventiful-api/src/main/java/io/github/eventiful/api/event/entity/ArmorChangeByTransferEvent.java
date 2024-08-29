package io.github.eventiful.api.event.entity;

import org.bukkit.block.Dispenser;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a {@link LivingEntity} has involuntarily equipped or unequipped an armor item due to an interaction
 * with an {@link InventoryHolder}, such as a {@link Dispenser} dispensing armor or a {@link Player} applying horse
 * armor to a {@link Horse}.
 *
 * @since 1.0.0
 */
public class ArmorChangeByTransferEvent extends ArmorChangeEvent {
    private final InventoryHolder transferSource;

    @ApiStatus.Internal
    public ArmorChangeByTransferEvent(@NotNull final LivingEntity who, @NotNull final ItemStack currentItem, final ItemStack oldItem, final EquipmentSlot slot, final InventoryHolder transferSource) {
        super(who, currentItem, oldItem, slot);
        this.transferSource = transferSource;
    }

    /**
     * Retrieves the {@link InventoryHolder} that is responsible for the armor change.
     *
     * @return the inventory holder involved in the transfer
     */
    public InventoryHolder getTransferSource() {
        return transferSource;
    }
}
