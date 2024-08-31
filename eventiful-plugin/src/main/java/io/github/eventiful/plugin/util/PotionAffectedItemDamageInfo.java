package io.github.eventiful.plugin.util;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

@AllArgsConstructor
class PotionAffectedItemDamageInfo implements ItemDamageInfo {
    @Delegate
    private final ItemDamageInfo source;
    private final double potionResistance;

    @Override
    public double getPotionResistancePoints() {
        return potionResistance;
    }
}
