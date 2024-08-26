package io.github.eventiful.plugin.player.armor;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.entity.ArmorEvent;
import io.github.eventiful.api.event.player.PlayerArmorUnequipEvent;
import io.github.eventiful.api.listener.EventListener;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Objects;

@AllArgsConstructor
public class PlayerArmorBreakListener implements EventListener<PlayerItemBreakEvent> {
    private final EventBus eventBus;

    @Override
    public void handle(final PlayerItemBreakEvent event) {
        final ItemStack brokenItem = event.getBrokenItem();
        final ArmorEvent.Type armorType = ArmorEvent.Type.of(brokenItem);

        if (armorType != null)
            handleAsUnequipEvent(event, armorType, brokenItem);
    }

    private void handleAsUnequipEvent(final PlayerItemBreakEvent event, final ArmorEvent.Type armorType, final ItemStack brokenArmor) {
        final Player player = event.getPlayer();
        final PlayerArmorUnequipEvent unequipEvent = new PlayerArmorUnequipEvent(player, armorType, PlayerArmorUnequipEvent.Cause.BROKE);
        unequipEvent.setArmorItem(brokenArmor);
        eventBus.dispatch(unequipEvent);

        if (unequipEvent.isCancelled())
            retainDamagedArmor(armorType, brokenArmor, player);
        else
            unequipEvent.setArmorItem(unequipEvent.getArmorItem());
    }

    private static void retainDamagedArmor(final ArmorEvent.Type armorType, final ItemStack brokenArmor, final Player player) {
        final ItemStack clonedBrokenArmor = brokenArmor.clone();
        final Damageable damageMeta = (Damageable) brokenArmor.getItemMeta();
        final Damageable clonedDamageMeta = Objects.requireNonNull(damageMeta).clone();
        clonedDamageMeta.setDamage(damageMeta.getDamage() - 1);
        clonedBrokenArmor.setAmount(brokenArmor.getAmount());
        clonedBrokenArmor.setItemMeta(clonedDamageMeta);
        player.getInventory().setItem(armorType.getInventorySlot(), clonedBrokenArmor);
    }
}
