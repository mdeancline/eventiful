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
public class EntityArmorDamageListener extends CancellableEventListener<EntityDamageEvent> {
    private final EventBus eventBus;
    private final EquipmentSlotResolver slotResolver;
    private final ItemDamageCalculator itemDamageCalculator;

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
        final ItemDamageInfo info = createItemDamageInfo(damaged, currentItem);
        final double inflictedDamage = itemDamageCalculator.calculateInflictedDamage(info);
        final ArmorDamageEvent armorDamageEvent = new ArmorDamageEvent(damaged, currentItem, event.getCause(), inflictedDamage);
        eventBus.dispatch(armorDamageEvent);

        if (armorDamageEvent.isCancelled())
            ItemDamageSupport.setDamage(currentItem, ItemDamageSupport.toNoDamage(info));
    }

    private ItemDamageInfo createItemDamageInfo(final LivingEntity damaged, final ItemStack currentItem) {
        final ItemDamageInfo info = ItemDamageSupport.newInfoFromPotionEffects(currentItem, damaged.getActivePotionEffects());
        return ItemDamageSupport.merge(info, ItemDamageSupport.newInfo(currentItem));
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
