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
        final PlayerArmorEquipEvent equipEvent = new PlayerArmorEquipEvent(player, slot, PlayerArmorEquipEvent.Cause.DRAG);
        equipEvent.setArmorItem(event.getOldCursor());
        eventBus.dispatch(equipEvent);

        if (equipEvent.isCancelled())
            event.setCancelled(true);
        else
            event.setCursor(equipEvent.getArmorItem());
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
