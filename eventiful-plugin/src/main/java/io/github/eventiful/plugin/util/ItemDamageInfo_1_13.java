package io.github.eventiful.plugin.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Collection;
import java.util.Objects;

class ItemDamageInfo_1_13 extends AbstractItemDamageInfo {
    private final Damageable meta;

    public ItemDamageInfo_1_13(final ItemStack itemStack) {
        super(itemStack.getEnchantments());
        this.meta = ItemDamageSupport.toDamageableMeta(Objects.requireNonNull(itemStack.getItemMeta()).clone());
    }

    @Override
    public double getInitialDamage() {
        return meta.getDamage();
    }

    @Override
    public double getInitialDefensePoints() {
        return getModifiersAmount(Attribute.GENERIC_ARMOR);
    }

    @Override
    public double getToughnessPoints() {
        return getModifiersAmount(Attribute.GENERIC_ARMOR_TOUGHNESS);
    }

    private double getModifiersAmount(final Attribute attribute) {
        final Collection<AttributeModifier> modifiers = meta.getAttributeModifiers(attribute);
        double value = 0;

        if (modifiers == null || modifiers.isEmpty())
            return value;

        for (final AttributeModifier modifier : modifiers)
            value += modifier.getAmount();

        return value;
    }
}
