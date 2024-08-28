package io.github.eventiful.api.event.mob;

import org.bukkit.entity.Mob;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 * @since 1.0.0
 */
public class AppliedMobArmorEvent extends MobArmorEquipEvent {
    private final InventoryHolder applicator;

    public AppliedMobArmorEvent(final Mob who, final EquipmentSlot slot, final ItemStack oldItem, final ItemStack newItem, final InventoryHolder applicator) {
        super(who, slot, oldItem, newItem);
        this.applicator = applicator;
    }

    public InventoryHolder getApplicator() {
        return applicator;
    }
}
