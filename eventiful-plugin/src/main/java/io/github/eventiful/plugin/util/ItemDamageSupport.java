package io.github.eventiful.plugin.util;

import lombok.experimental.UtilityClass;
import net.insprill.spigotutils.MinecraftVersion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@UtilityClass
public class ItemDamageSupport {
    private static final Set<PotionEffectType> RESISTANCE_EFFECTS = Set.of(PotionEffectType.RESISTANCE, PotionEffectType.FIRE_RESISTANCE);

    @SuppressWarnings("deprecation")
    public void setDamage(final ItemStack currentItem, final ItemDamageInfo info) {
        if (MinecraftVersion.isAtLeast(MinecraftVersion.v1_13_0)) {
            final ItemMeta meta = currentItem.getItemMeta();
            final Damageable damageableMeta = toDamageableMeta(Objects.requireNonNull(meta).clone());
            damageableMeta.setDamage((int) info.getInitialDamage());
            currentItem.setItemMeta(damageableMeta);
        } else
            currentItem.setDurability((short) info.getInitialDamage());
    }

    public Damageable toDamageableMeta(final ItemMeta meta) {
        if (!(meta instanceof Damageable))
            throw new IllegalArgumentException("ItemMeta is not Damageable");

        return (Damageable) meta;
    }

    public ItemDamageInfo newInfoFromPotionEffects(final ItemStack itemStack, final Collection<PotionEffect> effects) {
        double resistance = 0;

        for (final PotionEffect effect : effects)
            if (RESISTANCE_EFFECTS.contains(effect.getType()))
                resistance += effect.getAmplifier();

        return new PotionAffectedItemDamageInfo(newInfo(itemStack), resistance);
    }

    public ItemDamageInfo newInfo(final ItemStack itemStack) {
        return MinecraftVersion.isAtLeast(MinecraftVersion.v1_13_0)
                ? new DamageableItemInfo(itemStack)
                : new LegacyItemDamageInfo(itemStack);
    }

    public ItemDamageInfo merge(final ItemDamageInfo original, final ItemDamageInfo merged) {
        return new MergedItemDamageInfo(original, merged);
    }

    public ItemDamageInfo toNoDamage(final ItemDamageInfo info) {
        return new NonDamagedItemInfo(info);
    }

    public ItemDamageCalculator newItemDamageCalculator() {
        return MinecraftVersion.isAtLeast(MinecraftVersion.v1_9_0)
                ? new ItemDamageCalculator_1_9()
                : new LegacyItemDamageCalculator();
    }
}
