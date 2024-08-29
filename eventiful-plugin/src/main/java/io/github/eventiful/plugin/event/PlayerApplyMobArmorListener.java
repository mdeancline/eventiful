package io.github.eventiful.plugin.event;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.entity.ArmorChangeByTransferEvent;
import io.github.eventiful.api.listener.CancellableEventListener;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import lombok.AllArgsConstructor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@AllArgsConstructor
public class PlayerApplyMobArmorListener extends CancellableEventListener<InventoryClickEvent> {
    private final EventBus eventBus;
    private final EquipmentSlotResolver slotResolver;

    @Override
    protected void handleCancellable(final InventoryClickEvent event) {
        final ItemStack cursor = event.getCursor();
        if (event.getCursor() == null)
            return;

        final InventoryHolder holder = event.getInventory().getHolder();
        final LivingEntity entity;

        if (holder instanceof LivingEntity && (entity = (LivingEntity) holder).getEquipment() != null)
            dispatchAsTransferEvent(event, entity, cursor);
    }

    private void dispatchAsTransferEvent(final InventoryClickEvent event, final LivingEntity entity, final ItemStack cursor) {
        final InventoryType.SlotType slotType = event.getSlotType();
        final EquipmentSlot slot = slotResolver.getArmorSlotFor(cursor);

        if (slotType == InventoryType.SlotType.ARMOR && slot != null) {
            final ItemStack oldItem = Objects.requireNonNull(entity.getEquipment()).getItem(slot);
            final ArmorChangeByTransferEvent transferEvent = new ArmorChangeByTransferEvent(entity, cursor, oldItem, slot, event.getWhoClicked());
            eventBus.dispatch(transferEvent);

            if (transferEvent.isCancelled())
                event.setCancelled(true);
        }
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
