package io.github.eventiful.plugin.event;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.entity.ArmorDamageEvent;
import io.github.eventiful.api.listener.CancellableEventListener;
import io.github.eventiful.plugin.util.ArmorDamageCalculator;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Objects;

@AllArgsConstructor
public class ArmorDamageListener extends CancellableEventListener<EntityDamageEvent> {
    private final EventBus eventBus;
    private final EquipmentSlotResolver slotResolver;

    @Override
    protected void handleCancellable(final EntityDamageEvent event) {
        final Entity damaged = event.getEntity();
        final LivingEntity livingDamaged;

        if (damaged instanceof LivingEntity && (livingDamaged = (LivingEntity) damaged).getEquipment() != null)
            handleLivingEntityDamage(event, livingDamaged);
    }

    private void handleLivingEntityDamage(final EntityDamageEvent event, final LivingEntity damaged) {
        final EquipmentSlot[] armorSlots = slotResolver.getArmorSlotsFor(damaged);

        for (final EquipmentSlot slot : armorSlots)
            dispatchAsArmorDamageEvent(event, damaged, slot);
    }

    private void dispatchAsArmorDamageEvent(final EntityDamageEvent event, final LivingEntity damaged, final EquipmentSlot slot) {
        final ItemStack currentItem = Objects.requireNonNull(damaged.getEquipment()).getItem(slot);
        final Damageable meta = (Damageable) Objects.requireNonNull(currentItem.getItemMeta());
        final ArmorDamageCalculator damageCalculator = new ArmorDamageCalculator(meta);
        final double inflictedDamage = damageCalculator.calculateFinalDamage(event.getDamage());
        final ArmorDamageEvent armorDamageEvent = new ArmorDamageEvent(damaged, currentItem, event.getCause(), meta, inflictedDamage);
        eventBus.dispatch(armorDamageEvent);

        if (armorDamageEvent.isCancelled()) {
            final Damageable clonedMeta = meta.clone();
            clonedMeta.setDamage((int) (meta.getDamage() - inflictedDamage));
            currentItem.setItemMeta(clonedMeta);
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
