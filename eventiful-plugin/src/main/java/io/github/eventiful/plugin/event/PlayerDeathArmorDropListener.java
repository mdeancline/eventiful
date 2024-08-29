package io.github.eventiful.plugin.event;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.entity.ArmorChangeEvent;
import io.github.eventiful.api.listener.EventListener;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PlayerDeathArmorDropListener implements EventListener<PlayerDeathEvent> {
    private final EventBus eventBus;
    private final EquipmentSlotResolver slotResolver;

    @Override
    public void handle(final PlayerDeathEvent event) {
        if (!event.getKeepInventory()) {
            final EquipmentSlot[] armorSlots = slotResolver.getArmorSlotsFor(event.getEntity());

            for (final EquipmentSlot slot : armorSlots)
                dispatchAsChangeEvent(event, slot);
        }
    }

    private void dispatchAsChangeEvent(final PlayerDeathEvent event, final EquipmentSlot slot) {
        final Player player = event.getEntity();
        final ItemStack oldItem = player.getInventory().getItem(slot);
        final ArmorChangeEvent changeEvent = new ArmorChangeEvent(player, new ItemStack(Material.AIR), oldItem, slot);
        eventBus.dispatch(changeEvent);

        if (changeEvent.isCancelled())
            event.getDrops().remove(oldItem);
    }
}
