package io.github.eventiful.plugin.player.armor;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.player.PlayerArmorEquipEvent;
import io.github.eventiful.api.listener.CancellableEventListener;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import lombok.AllArgsConstructor;
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
        final EquipmentSlot slot = slotResolver.getArmorSlotFor(event.getOldCursor());

        if (slot != null)
            dispatchAsEquipEvent(event, slot);
    }

    private void dispatchAsEquipEvent(final InventoryDragEvent event, final EquipmentSlot slot) {
        final Player player = (Player) event.getWhoClicked();
        final ItemStack oldItem = event.getOldCursor();
        final ItemStack newItem = event.getCursor();
        final PlayerArmorEquipEvent equipEvent = new PlayerArmorEquipEvent(player, slot, oldItem, newItem);
        eventBus.dispatch(equipEvent);

        if (equipEvent.isCancelled())
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
