package io.github.eventiful.plugin.event;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.entity.ArmorChangeEvent;
import io.github.eventiful.api.listener.CancellableEventListener;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import lombok.AllArgsConstructor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@AllArgsConstructor
public class EntityPickupArmorListener extends CancellableEventListener<EntityPickupItemEvent> {
    private final EventBus eventBus;
    private final EquipmentSlotResolver slotResolver;

    @Override
    protected void handleCancellable(final EntityPickupItemEvent event) {
        final EquipmentSlot slot = slotResolver.getArmorSlotFor(event.getItem());
        final LivingEntity entity = event.getEntity();

        if (slot != null && entity.getEquipment() != null)
            dispatchAsEquipEvent(event, entity, slot);
    }

    private void dispatchAsEquipEvent(final EntityPickupItemEvent event, final LivingEntity entity, final EquipmentSlot slot) {
        final ItemStack oldItem = Objects.requireNonNull(entity.getEquipment()).getItem(slot);
        final ItemStack newItem = event.getItem().getItemStack();
        final ArmorChangeEvent equipEvent = new ArmorChangeEvent(entity, newItem, oldItem, slot);
        eventBus.dispatch(equipEvent);

        if (equipEvent.isCancelled())
            event.setCancelled(true);
    }
}
