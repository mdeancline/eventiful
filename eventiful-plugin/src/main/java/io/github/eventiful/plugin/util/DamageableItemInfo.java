package io.github.eventiful.plugin.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.*;

class DamageableItemInfo implements ItemDamageInfo {
    private static final Set<Enchantment> DURABILITY_ENCHANTMENTS = Set.of(Enchantment.UNBREAKING, Enchantment.MENDING);

    private final Damageable meta;
    private final Map<Enchantment, Integer> enchantments;

    public DamageableItemInfo(final ItemStack itemStack) {
        this.meta = ItemDamageSupport.toDamageableMeta(Objects.requireNonNull(itemStack.getItemMeta()).clone());
        this.enchantments = new HashMap<>(itemStack.getEnchantments());
    }

    @Override
    public double getInitialDamage() {
        return meta.getDamage();
    }

    @Override
    public double getDefensePoints() {
        double defensePoints = 0;

        for (final Map.Entry<Enchantment, Integer> entry : enchantments.entrySet())
            if (DURABILITY_ENCHANTMENTS.contains(entry.getKey()))
                defensePoints += entry.getValue();

        return defensePoints + getModifiersAmount(Attribute.GENERIC_ARMOR);
    }

    @Override
    public double getPotionResistancePoints() {
        return 0;
    }

    @Override
    public double getEnchantmentProtectionPoints() {
        return 0;
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
