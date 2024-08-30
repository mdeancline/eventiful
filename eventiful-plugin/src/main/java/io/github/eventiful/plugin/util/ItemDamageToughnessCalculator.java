package io.github.eventiful.plugin.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemDamageToughnessCalculator implements ItemDamageCalculator {
    @Override
    public double calculateInflictedDamage(final ItemDamageInfo info) {
        final double damage = info.getInitialDamage();
        final double health = info.getHealth();
        final double toughness = info.getDefensePoints();

        // https://www.spigotmc.org/threads/tutorial-calculating-damage-taken-by-a-player-manually.424680/
        return damage * (1 - Math.min(1, Math.max(health / 5, health - ((4 * damage) / (toughness + 8))) / 25));
    }
}
