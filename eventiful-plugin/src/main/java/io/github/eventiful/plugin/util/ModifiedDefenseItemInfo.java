package io.github.eventiful.plugin.util;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

@AllArgsConstructor
class ModifiedDefenseItemInfo implements ItemDamageInfo {
    @Delegate
    private final ItemDamageInfo source;
    private final double mod;

    @Override
    public double getDefensePoints() {
        return source.getDefensePoints() + mod;
    }
}
