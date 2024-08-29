package io.github.eventiful.plugin.util;

import lombok.AllArgsConstructor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.meta.Damageable;

import java.util.Collection;

@AllArgsConstructor
public class ArmorDamageCalculator {
    private final Damageable itemMeta;

    public double calculateFinalDamage(final double damage) {
        final double health = itemMeta.getMaxDamage() - itemMeta.getDamage();
        final double toughness = calculateTotalToughness();

        // https://minecraft.fandom.com/wiki/Armor#Armor_toughness
        return damage * (1 - Math.min(1, Math.max(health / 5, health - ((4 * damage) / (toughness + 8))) / 25));
    }

    private double calculateTotalToughness() {
        final Collection<AttributeModifier> modifiers = itemMeta.getAttributeModifiers(Attribute.GENERIC_ARMOR_TOUGHNESS);
        double toughness = 0;
        if (modifiers == null || modifiers.isEmpty())
            return toughness;

        for (final AttributeModifier modifier : modifiers)
            toughness += modifier.getAmount();

        return toughness;
    }
}
