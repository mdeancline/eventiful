package io.github.eventiful.plugin.util;

import lombok.AllArgsConstructor;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class LegacyItemDamageInfo implements ItemDamageInfo {
    private final ItemStack itemStack;

    @SuppressWarnings("deprecation")
    @Override
    public double getInitialDamage() {
        return itemStack.getDurability();
    }

    @Override
    public double getHealth() {
        return itemStack.getType().getMaxDurability() - getInitialDamage();
    }

    @Override
    public double getDefensePoints() {
        return 0;
    }
}
