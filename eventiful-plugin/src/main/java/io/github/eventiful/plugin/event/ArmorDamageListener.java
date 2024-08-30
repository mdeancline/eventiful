package io.github.eventiful.plugin.event;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.entity.ArmorDamageEvent;
import io.github.eventiful.api.listener.CancellableEventListener;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import io.github.eventiful.plugin.util.ItemDamageCalculator;
import io.github.eventiful.plugin.util.ItemDamageInfo;
import io.github.eventiful.plugin.util.ItemDamageSupport;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@AllArgsConstructor
public class ArmorDamageListener extends CancellableEventListener<EntityDamageEvent> {
    private final EventBus eventBus;
    private final EquipmentSlotResolver slotResolver;
    private final ItemDamageCalculator damageCalculator;

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
        final ItemDamageInfo damageInfo = ItemDamageSupport.createInfoWithResistanceEffects(currentItem, damaged.getActivePotionEffects());
        final double inflictedDamage = damageCalculator.calculateInflictedDamage(damageInfo);
        final ArmorDamageEvent armorDamageEvent = new ArmorDamageEvent(damaged, currentItem, event.getCause(), inflictedDamage);
        eventBus.dispatch(armorDamageEvent);

        if (armorDamageEvent.isCancelled())
            ItemDamageSupport.setDamage(currentItem, ItemDamageSupport.createNoDamageInfo(damageInfo));
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
