package io.github.eventiful.plugin.player.armor;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.mob.AppliedMobArmorEvent;
import io.github.eventiful.api.listener.CancellableEventListener;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Mob;
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
        if (event.getCursor() == null) return;

        final InventoryHolder holder = event.getInventory().getHolder();
        final Mob mob;

        if (holder instanceof Mob && (mob = (Mob) holder).getEquipment() != null)
            dispatchAsAppliedArmorEvent(event, mob);
    }

    private void dispatchAsAppliedArmorEvent(final InventoryClickEvent event, final Mob mob) {
        final ItemStack cursor = event.getCursor();
        final InventoryType.SlotType slotType = event.getSlotType();
        final EquipmentSlot slot = slotResolver.getArmorSlotFor(Objects.requireNonNull(cursor));

        if (slotType == InventoryType.SlotType.ARMOR && slot != null) {
            final ItemStack oldItem = Objects.requireNonNull(mob.getEquipment()).getItem(slot);
            final AppliedMobArmorEvent appliedArmorEvent = new AppliedMobArmorEvent(mob, slot, oldItem, cursor, event.getWhoClicked());
            eventBus.dispatch(appliedArmorEvent);

            if (appliedArmorEvent.isCancelled())
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
