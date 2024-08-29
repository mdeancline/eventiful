package io.github.eventiful.api.event.entity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a {@link LivingEntity} has equipped or unequipped an armor item.
 *
 * @since 1.0.0
 */
public class ArmorChangeEvent extends ArmorEvent implements Cancellable {
    private final ItemStack oldItem;
    private final EquipmentSlot slot;
    private boolean cancel;

    @ApiStatus.Internal
    public ArmorChangeEvent(@NotNull final LivingEntity who, @NotNull final ItemStack currentItem, final ItemStack oldItem, final EquipmentSlot slot) {
        super(who, currentItem);
        this.oldItem = oldItem;
        this.slot = slot;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }

    /**
     * Retrieves the {@link ItemStack} representing the old armor piece that was replaced.
     *
     * @return the old armor item stack
     */
    public final ItemStack getOldItem() {
        return oldItem;
    }

    /**
     * Retrieves the {@link EquipmentSlot} where the armor change occurred.
     *
     * @return the equipment slot
     */
    public final EquipmentSlot getSlot() {
        return slot;
    }
}
