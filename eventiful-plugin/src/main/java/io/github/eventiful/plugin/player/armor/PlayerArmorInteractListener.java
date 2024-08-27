package io.github.eventiful.plugin.player.armor;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.player.PlayerArmorEquipEvent;
import io.github.eventiful.api.listener.CancellableEventListener;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PlayerArmorInteractListener extends CancellableEventListener<PlayerInteractEvent> {
    private final EventBus eventBus;
    private final EquipmentSlotResolver slotResolver;

    @Override
    protected void handleCancellable(final PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR || event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        final EquipmentSlot slot = slotResolver.getArmorSlotFor(event.getMaterial());

        if (slot != null && event.useInteractedBlock() != Event.Result.DENY)
            dispatchAsEquipEvent(event, slot);
    }

    private void dispatchAsEquipEvent(final PlayerInteractEvent event, final EquipmentSlot slot) {
        final ItemStack item = event.getItem();
        if (item == null) return;

        final Player player = event.getPlayer();
        final PlayerArmorEquipEvent equipEvent = new PlayerArmorEquipEvent(player, slot, PlayerArmorEquipEvent.Cause.RIGHT_CLICK);
        equipEvent.setArmorItem(item);
        eventBus.dispatch(equipEvent);

        if (equipEvent.isCancelled())
            event.setCancelled(true);
        else
            player.getInventory().setItem(slot, equipEvent.getArmorItem());
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
