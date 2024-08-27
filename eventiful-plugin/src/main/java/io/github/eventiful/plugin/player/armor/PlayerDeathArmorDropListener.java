package io.github.eventiful.plugin.player.armor;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.player.PlayerArmorUnequipEvent;
import io.github.eventiful.api.listener.EventListener;
import io.github.eventiful.plugin.util.InventoryWrapper;
import io.github.eventiful.plugin.util.PlayerInventoryWrapper;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PlayerDeathArmorDropListener implements EventListener<PlayerDeathEvent> {
    private final EventBus eventBus;

    @Override
    public void handle(final PlayerDeathEvent event) {
        if (event.getKeepInventory()) return;

        final Player player = event.getEntity();
        final InventoryWrapper inventory = new PlayerInventoryWrapper(player.getInventory());

        for (final EquipmentSlot slot : EquipmentSlot.values()) {
            final ItemStack armorItem = inventory.getItem(inventory.getIndex(slot));
            final PlayerArmorUnequipEvent unequipEvent = new PlayerArmorUnequipEvent(player, slot, PlayerArmorUnequipEvent.Cause.DEATH, armorItem);
            eventBus.dispatch(unequipEvent);

            if (unequipEvent.isCancelled())
                event.getDrops().remove(armorItem);
        }
    }
}
