package io.github.eventiful.plugin.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;

public class DamageableItemInfo implements ItemDamageInfo {
    private final Damageable meta;

    public DamageableItemInfo(final ItemStack itemStack) {
        final ItemMeta meta = itemStack.getItemMeta();
        if (!(meta instanceof Damageable))
            throw new IllegalArgumentException("ItemMeta of ItemStack is not Damageable");

        this.meta = (Damageable) meta;
    }

    @Override
    public double getInitialDamage() {
        return meta.getDamage();
    }

    @Override
    public double getHealth() {
        return meta.getMaxDamage() - meta.getDamage();
    }

    @Override
    public double getDefensePoints() {
        final Collection<AttributeModifier> modifiers = meta.getAttributeModifiers(Attribute.GENERIC_ARMOR_TOUGHNESS);
        double toughness = 0;

        if (modifiers == null || modifiers.isEmpty())
            return toughness;

        for (final AttributeModifier modifier : modifiers)
            toughness += modifier.getAmount();

        return toughness;
    }
}
