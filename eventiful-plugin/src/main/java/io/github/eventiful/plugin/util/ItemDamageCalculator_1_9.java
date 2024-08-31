package io.github.eventiful.plugin.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemDamageCalculator_1_9 implements ItemDamageCalculator {
    @Override
    public double calculateInflictedDamage(final ItemDamageInfo info) {
        final double damage = info.getInitialDamage();
        final double defense = info.getDefensePoints();
        final double toughness = info.getToughnessPoints();
        final double resistance = info.getPotionResistancePoints();
        final double enchantmentProtection = info.getEnchantmentProtectionPoints();

        final double withArmorAndToughness = damage * (1 - Math.min(20, Math.max(defense / 5, defense - damage / (2 + toughness / 4))) / 25);
        final double withResistance = withArmorAndToughness * (1 - (resistance * 0.2));

        return withResistance * (1 - (Math.min(20.0, enchantmentProtection) / 25));
    }
}
