package io.github.eventiful.plugin.util;

import lombok.Getter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
class LegacyItemDamageInfo implements ItemDamageInfo {
    private static final Set<Enchantment> DURABILITY_ENCHANTMENTS = Set.of(Enchantment.UNBREAKING, Enchantment.MENDING);

    private final Map<Enchantment, Integer> enchantments;
    private final double durability;
    private final double initialDamage;

    @SuppressWarnings("deprecation")
    public LegacyItemDamageInfo(final ItemStack itemStack) {
        enchantments = new HashMap<>(itemStack.getEnchantments());
        durability = itemStack.getDurability();
        initialDamage = itemStack.getType().getMaxDurability() - durability;
    }

    @Override
    public double getDefensePoints() {
        return durability;
    }

    @Override
    public double getPotionResistancePoints() {
        return 0;
    }

    @Override
    public double getEnchantmentProtectionPoints() {
        double points = 0;

        for (final Map.Entry<Enchantment, Integer> entry : enchantments.entrySet())
            if (DURABILITY_ENCHANTMENTS.contains(entry.getKey()))
                points += entry.getValue();

        return points;
    }

    @Override
    public double getToughnessPoints() {
        return 0;
    }
}
