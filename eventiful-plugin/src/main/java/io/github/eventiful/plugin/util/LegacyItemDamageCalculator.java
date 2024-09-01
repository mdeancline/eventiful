package io.github.eventiful.plugin.util;

public class LegacyItemDamageCalculator implements ItemDamageCalculator {
    @Override
    public double calculateInflictedDamage(final ItemDamageInfo info) {
        final double defenseReduction = Math.min(info.getInitialDefensePoints() * 0.04, 0.8);
        final double enchantmentProtectionReduction = Math.min(info.getEnchantmentProtectionPoints() * 0.05, 0.8);
        
        return info.getInitialDamage() * (1 - defenseReduction) * (1 - enchantmentProtectionReduction);
    }
}
