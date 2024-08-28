package io.github.eventiful.plugin.entity.armor;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.mob.MobArmorEquipEvent;
import io.github.eventiful.api.listener.CancellableEventListener;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import lombok.AllArgsConstructor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@AllArgsConstructor
public class MobPickupArmorListener extends CancellableEventListener<EntityPickupItemEvent> {
    private final EventBus eventBus;
    private final EquipmentSlotResolver slotResolver;

    @Override
    protected void handleCancellable(final EntityPickupItemEvent event) {
        final EquipmentSlot slot = slotResolver.getArmorSlotFor(event.getItem());
        final LivingEntity entity = event.getEntity();

        if (slot != null && entity.getEquipment() != null && entity instanceof Mob)
            dispatchAsEquipEvent(event, slot);
    }

    private void dispatchAsEquipEvent(final EntityPickupItemEvent event, final EquipmentSlot slot) {
        final Mob mob = (Mob) event.getEntity();
        final ItemStack oldItem = Objects.requireNonNull(mob.getEquipment()).getItem(slot);
        final ItemStack newItem = event.getItem().getItemStack();
        final MobArmorEquipEvent equipEvent = new MobArmorEquipEvent(mob, slot, oldItem, newItem);
        eventBus.dispatch(equipEvent);

        if (equipEvent.isCancelled())
            event.setCancelled(true);
    }
}
