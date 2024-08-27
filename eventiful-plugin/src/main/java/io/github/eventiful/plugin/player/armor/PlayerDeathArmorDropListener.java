package io.github.eventiful.plugin.player.armor;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.player.PlayerArmorUnequipEvent;
import io.github.eventiful.api.listener.EventListener;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import lombok.AllArgsConstructor;
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
        if (!event.getKeepInventory())
            for (final EquipmentSlot slot : slotResolver.getArmorPieces())
                dispatchAsUnequipEvent(event, slot);
    }

    private void dispatchAsUnequipEvent(final PlayerDeathEvent event, final EquipmentSlot slot) {
        final Player player = event.getEntity();
        final ItemStack armorItem = player.getInventory().getItem(slot);
        final PlayerArmorUnequipEvent unequipEvent = new PlayerArmorUnequipEvent(player, slot, PlayerArmorUnequipEvent.Cause.DEATH, armorItem);
        eventBus.dispatch(unequipEvent);

        if (unequipEvent.isCancelled())
            event.getDrops().remove(armorItem);
    }
}
