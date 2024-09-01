package io.github.eventiful.plugin.util;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.bukkit.enchantments.Enchantment;

import java.util.Map;
import java.util.Set;

abstract class AbstractItemDamageInfo implements ItemDamageInfo {
    private static final Set<Enchantment> DURABILITY_ENCHANTMENTS = Set.of(Enchantment.UNBREAKING);

    private final Object2IntMap<Enchantment> enchantments;

    protected AbstractItemDamageInfo(final Map<Enchantment, Integer> enchantments) {
        this.enchantments = new Object2IntOpenHashMap<>(enchantments);
    }

    @Override
    public double getEnchantmentProtectionPoints() {
        double points = 0;

        for (final Object2IntMap.Entry<Enchantment> entry : enchantments.object2IntEntrySet())
            if (DURABILITY_ENCHANTMENTS.contains(entry.getKey()))
                points += entry.getIntValue();

        return points;
    }
}
