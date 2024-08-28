package io.github.eventiful.api.event.mob;

import io.github.eventiful.api.event.ArmorEvent;
import org.bukkit.entity.Mob;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when a {@link Mob} equips an item to be used as armor.
 *
 * @since 1.0.0
 */
public class MobArmorEquipEvent extends ArmorEvent<Mob> implements Cancellable {
    private boolean cancel;

    @ApiStatus.Internal
    public MobArmorEquipEvent(final Mob who, final EquipmentSlot slot, final ItemStack oldItem, final ItemStack newItem) {
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
