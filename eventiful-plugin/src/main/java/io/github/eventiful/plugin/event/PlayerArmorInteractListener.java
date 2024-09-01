package io.github.eventiful.plugin.event;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.entity.ArmorChangeEvent;
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

import java.util.Objects;

@AllArgsConstructor
public class PlayerArmorInteractListener extends CancellableEventListener<PlayerInteractEvent> {
    private final EventBus eventBus;
    private final EquipmentSlotResolver slotResolver;

    @Override
    protected void handleCancellable(final PlayerInteractEvent event) {
        final Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)
            return;

        final EquipmentSlot slot = slotResolver.getArmorSlotFor(event.getMaterial());

        if (slot != null && event.useInteractedBlock() != Event.Result.DENY)
            dispatchAsChangeEvent(event, slot);
    }

    private void dispatchAsChangeEvent(final PlayerInteractEvent event, final EquipmentSlot slot) {
        final ItemStack newItem = event.getItem();
        if (newItem == null) return;

        final Player player = event.getPlayer();
        final ItemStack oldItem = Objects.requireNonNull(player.getEquipment()).getItem(slot);
        final ArmorChangeEvent equipEvent = new ArmorChangeEvent(player, newItem, oldItem, slot);
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
