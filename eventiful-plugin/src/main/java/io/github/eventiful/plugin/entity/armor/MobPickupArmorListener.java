package io.github.eventiful.plugin.entity.armor;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.entity.MobArmorEquipEvent;
import io.github.eventiful.api.listener.CancellableEventListener;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class MobPickupArmorListener extends CancellableEventListener<EntityPickupItemEvent> {
    private final EventBus eventBus;
    private final EquipmentSlotResolver slotResolver;

    @Override
    protected void handleCancellable(final EntityPickupItemEvent event) {
        final EquipmentSlot slot = slotResolver.getArmorSlotFor(event.getItem());
        final Entity entity = event.getEntity();

        if (slot != null && entity instanceof Mob) {
            final Mob mob = (Mob) event.getEntity();
            final ItemStack armorItem = mob.getEquipment().getItem(slot);
            final MobArmorEquipEvent equipEvent = new MobArmorEquipEvent(mob, slot, MobArmorEquipEvent.Cause.ITEM_PICKUP);
            equipEvent.setArmorItem(armorItem);
            eventBus.dispatch(equipEvent);

            if (equipEvent.isCancelled())
                event.setCancelled(true);
        }
    }
}
