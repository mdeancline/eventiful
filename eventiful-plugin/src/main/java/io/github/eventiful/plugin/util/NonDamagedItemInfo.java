package io.github.eventiful.plugin.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class NonDamagedItemInfo implements ItemDamageInfo {
    @Delegate
    private final ItemDamageInfo source;

    @Override
    public double getInitialDamage() {
        return 0;
    }
}
