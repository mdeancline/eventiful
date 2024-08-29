package io.github.eventiful.plugin.event;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.entity.ArmorChangeEvent;
import io.github.eventiful.api.listener.CancellableEventListener;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PlayerArmorDragListener extends CancellableEventListener<InventoryDragEvent> {
    private final EventBus eventBus;
    private final EquipmentSlotResolver slotResolver;

    @Override
    protected void handleCancellable(final InventoryDragEvent event) {
        final ItemStack oldItem = event.getOldCursor();
        final EquipmentSlot slot = slotResolver.getArmorSlotFor(oldItem);

        if (slot != null)
            dispatchAsChangeEvent(event, slot, oldItem);
    }

    private void dispatchAsChangeEvent(final InventoryDragEvent event, final EquipmentSlot slot, final ItemStack oldItem) {
        final Player player = (Player) event.getWhoClicked();
        final ItemStack cursor = event.getCursor();
        final ItemStack newItem = cursor == null ? new ItemStack(Material.AIR) : cursor;
        final ArmorChangeEvent changeEvent = new ArmorChangeEvent(player, newItem, oldItem, slot);
        eventBus.dispatch(changeEvent);

        if (changeEvent.isCancelled())
            event.setCancelled(true);
    }

    @Override
    public EventPriority getPriority() {
        return EventPriority.HIGHEST;
    }

    @Override
    protected boolean isIgnoringCancelled() {
        return true;
    }
}
