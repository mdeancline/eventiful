package io.github.eventiful.plugin.player.armor;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.entity.ArmorEvent;
import io.github.eventiful.api.event.player.PlayerArmorUnequipEvent;
import io.github.eventiful.api.listener.EventListener;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@AllArgsConstructor
public class PlayerDeathArmorDropListener implements EventListener<PlayerDeathEvent> {
    private final EventBus eventBus;

    @Override
    public void handle(final PlayerDeathEvent event) {
        if (event.getKeepInventory()) return;

        final Player player = event.getEntity();
        final PlayerInventory inventory = player.getInventory();

        for (final ArmorEvent.Type type : ArmorEvent.Type.values()) {
            final ItemStack armorItem = inventory.getItem(type.getInventorySlot());
            final PlayerArmorUnequipEvent unequipEvent = new PlayerArmorUnequipEvent(player, type, PlayerArmorUnequipEvent.Cause.DEATH);
            unequipEvent.setArmorItem(armorItem);
            eventBus.dispatch(new PlayerArmorUnequipEvent(player, type, PlayerArmorUnequipEvent.Cause.DEATH));
            inventory.setItem(type.getInventorySlot(), unequipEvent.getArmorItem());
        }
    }
}
