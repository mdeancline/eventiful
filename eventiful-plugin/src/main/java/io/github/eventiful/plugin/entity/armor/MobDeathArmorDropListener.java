package io.github.eventiful.plugin.entity.armor;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.entity.MobArmorUnequipEvent;
import io.github.eventiful.api.listener.EventListener;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class MobDeathArmorDropListener implements EventListener<EntityDeathEvent> {
    private final EventBus eventBus;
    private final EquipmentSlotResolver slotResolver;

    @Override
    public void handle(final EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Mob))
            return;

        dispatchForArmorSlots(event);
    }

    private void dispatchForArmorSlots(final EntityDeathEvent event) {
        for (final EquipmentSlot slot : slotResolver.getArmorPieces())
            dispatchAsUnequipEvent(event, slot);
    }

    private void dispatchAsUnequipEvent(final EntityDeathEvent event, final EquipmentSlot slot) {
        final Mob mob = (Mob) event.getEntity();
        final ItemStack armorItem = mob.getEquipment().getItem(slot);
        final MobArmorUnequipEvent unequipEvent = new MobArmorUnequipEvent(mob, slot, MobArmorUnequipEvent.Cause.DEATH, armorItem);
        eventBus.dispatch(unequipEvent);

        if (unequipEvent.isCancelled())
            event.getDrops().remove(armorItem);
    }
}
