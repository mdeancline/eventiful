package io.github.eventiful.api.event.player;

import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 * Called when a {@code Player} has their armor equipped by a {@link Dispenser}.
 *
 * @since 1.0.0
 */
public class AppliedPlayerArmorEvent extends PlayerArmorEquipEvent {
    private final InventoryHolder applicator;

    public AppliedPlayerArmorEvent(final Player who, final EquipmentSlot slot, final ItemStack oldItem, final ItemStack newItem, final InventoryHolder applicator) {
        super(who, slot, oldItem, newItem);
        this.applicator = applicator;
    }

    public InventoryHolder getApplicator() {
        return applicator;
    }
}
