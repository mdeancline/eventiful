package io.github.eventiful.plugin.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemDamageCalculator_1_9 implements ItemDamageCalculator {
    @Override
    public double calculateInflictedDamage(final ItemDamageInfo info) {
        final double damage = info.getInitialDamage();
        final double defense = info.getInitialDefensePoints();
        final double toughness = info.getToughnessPoints();
        final double enchantmentProtection = info.getEnchantmentProtectionPoints();

        final double withDefenseAndToughness = damage * (1 - Math.min(20, Math.max(defense / 5, defense - damage / (2 + toughness / 4))) / 25);

        return withDefenseAndToughness * (1 - (Math.min(20, enchantmentProtection) / 25));
    }
}
