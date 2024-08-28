package io.github.eventiful.api.event.mob;

import io.github.eventiful.api.event.ArmorEvent;
import org.bukkit.entity.Mob;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when a {@link Mob} unequips an item formerly used as armor.
 *
 * @apiNote This will not be called when a {@code Mob} dies.
 * @since 1.0.0
 */
public class MobArmorUnequipEvent extends ArmorEvent<Mob> implements Cancellable {
    private boolean cancel;

    @ApiStatus.Internal
    public MobArmorUnequipEvent(final Mob who, final EquipmentSlot slot, final ItemStack oldItem, final ItemStack newItem) {
        super(who, slot, oldItem, newItem);
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
