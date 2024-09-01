package io.github.eventiful.plugin.util;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
class LegacyItemDamageInfo extends AbstractItemDamageInfo {
    private final double initialDamage;
    private final double initialDefensePoints;

    public LegacyItemDamageInfo(final ItemStack itemStack) {
        super(itemStack.getEnchantments());
        //noinspection deprecation
        initialDefensePoints = itemStack.getDurability();
        initialDamage = itemStack.getType().getMaxDurability() - initialDefensePoints;
    }

    @Override
    public double getToughnessPoints() {
        return 0;
    }
}
