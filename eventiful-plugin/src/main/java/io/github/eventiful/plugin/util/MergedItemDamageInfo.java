package io.github.eventiful.plugin.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class MergedItemDamageInfo implements ItemDamageInfo {
    private final ItemDamageInfo original;
    private final ItemDamageInfo merged;

    @Override
    public double getInitialDamage() {
        return Math.max(original.getInitialDamage(), merged.getInitialDamage());
    }

    @Override
    public double getDefensePoints() {
        return Math.max(original.getDefensePoints(), merged.getDefensePoints());
    }

    @Override
    public double getPotionResistancePoints() {
        return Math.max(original.getPotionResistancePoints(), merged.getPotionResistancePoints());
    }

    @Override
    public double getEnchantmentProtectionPoints() {
        return Math.max(original.getEnchantmentProtectionPoints(), merged.getEnchantmentProtectionPoints());
    }

    @Override
    public double getToughnessPoints() {
        return Math.max(original.getToughnessPoints(), merged.getToughnessPoints());
    }
}
