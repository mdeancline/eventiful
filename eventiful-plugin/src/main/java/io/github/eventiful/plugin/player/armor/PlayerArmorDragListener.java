package io.github.eventiful.plugin.player.armor;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.entity.ArmorEvent;
import io.github.eventiful.api.event.player.PlayerArmorEquipEvent;
import io.github.eventiful.api.listener.CancellableEventListener;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryDragEvent;

@AllArgsConstructor
public class PlayerArmorDragListener extends CancellableEventListener<InventoryDragEvent> {
    private final EventBus eventBus;

    @Override
    protected void handleCancellable(final InventoryDragEvent event) {
        final ArmorEvent.Type armorType = ArmorEvent.Type.of(event.getOldCursor());

        if (armorType != null)
            handleAsEquipEvent(event, armorType);
    }

    private void handleAsEquipEvent(final InventoryDragEvent event, final ArmorEvent.Type armorType) {
        final Player player = (Player) event.getWhoClicked();
        final PlayerArmorEquipEvent equipEvent = new PlayerArmorEquipEvent(player, armorType, PlayerArmorEquipEvent.Cause.INVENTORY_DRAG);
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
