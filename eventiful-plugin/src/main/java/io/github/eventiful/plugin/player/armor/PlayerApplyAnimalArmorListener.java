package io.github.eventiful.plugin.player.armor;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.listener.CancellableEventListener;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Mob;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PlayerApplyAnimalArmorListener extends CancellableEventListener<InventoryClickEvent> {
    private final EventBus eventBus;
    private final EquipmentSlotResolver slotResolver;

    @Override
    protected void handleCancellable(final InventoryClickEvent event) {
        final ItemStack cursor = event.getCursor();
        if (cursor == null) return;

        final EquipmentSlot slot = slotResolver.getArmorSlotFor(cursor);
        final InventoryHolder holder = event.getInventory().getHolder();

        if (slot != null && holder instanceof Mob) {
            //TODO: handle equipping/unequipping of armor inside mob inventory
        }
    }
}
