package io.github.eventiful.plugin.player.armor;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.player.PlayerArmorUnequipEvent;
import io.github.eventiful.api.listener.EventListener;
import io.github.eventiful.plugin.util.Inventories;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Objects;

@AllArgsConstructor
public class PlayerArmorBreakListener implements EventListener<PlayerItemBreakEvent> {
    private final EventBus eventBus;

    @Override
    public void handle(final PlayerItemBreakEvent event) {
        final EquipmentSlot slot = Inventories.matchEquipmentSlot(event.getBrokenItem());

        if (slot != null)
            dispatchAsUnequipEvent(event, slot);
    }

    private void dispatchAsUnequipEvent(final PlayerItemBreakEvent event, final EquipmentSlot slot) {
        final ItemStack brokenItem = event.getBrokenItem();
        final Player player = event.getPlayer();
        final PlayerArmorUnequipEvent unequipEvent = new PlayerArmorUnequipEvent(player, slot, PlayerArmorUnequipEvent.Cause.BROKE, brokenItem);
        eventBus.dispatch(unequipEvent);

        if (unequipEvent.isCancelled())
            player.getInventory().setItem(slot, recoverBrokenItem(brokenItem));
    }

    private static ItemStack recoverBrokenItem(final ItemStack brokenItem) {
        final ItemStack clonedBrokenArmor = brokenItem.clone();
        final Damageable damageMeta = (Damageable) brokenItem.getItemMeta();
        final Damageable clonedDamageMeta = Objects.requireNonNull(damageMeta).clone();
        clonedDamageMeta.setDamage(damageMeta.getDamage() - 1);
        clonedBrokenArmor.setAmount(brokenItem.getAmount());
        clonedBrokenArmor.setItemMeta(clonedDamageMeta);

        return clonedBrokenArmor;
    }
}
