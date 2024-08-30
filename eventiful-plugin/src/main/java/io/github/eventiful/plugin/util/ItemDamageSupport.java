package io.github.eventiful.plugin.util;

import lombok.experimental.UtilityClass;
import net.insprill.spigotutils.MinecraftVersion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.Set;

@UtilityClass
public class ItemDamageSupport {
    private static final Set<PotionEffectType> RESISTANCE_EFFECT_TYPES = Set.of(PotionEffectType.RESISTANCE, PotionEffectType.FIRE_RESISTANCE);

    @SuppressWarnings("deprecation")
    public void setDamage(final ItemStack currentItem, final ItemDamageInfo info) {
        if (MinecraftVersion.isAtLeast(MinecraftVersion.v1_9_0)) {
            final ItemMeta meta = currentItem.getItemMeta();
            checkForDamageableMeta(meta);

            final Damageable damageableMeta = (Damageable) meta.clone();
            damageableMeta.setDamage((int) info.getInitialDamage());
            currentItem.setItemMeta(damageableMeta);
        } else
            currentItem.setDurability((short) info.getInitialDamage());
    }

    private void checkForDamageableMeta(final ItemMeta meta) {
        if (!(meta instanceof Damageable))
            throw new IllegalArgumentException("ItemMeta is not Damageable");
    }

    public ItemDamageInfo createInfoWithResistanceEffects(final ItemStack itemStack, final Collection<PotionEffect> effects) {
        double mod = 0;

        for (final PotionEffect effect : effects)
            if (RESISTANCE_EFFECT_TYPES.contains(effect.getType()))
                mod += effect.getAmplifier();

        return new ModifiedDefenseItemInfo(createInfo(itemStack), mod);
    }

    public ItemDamageInfo createInfo(final ItemStack itemStack) {
        if (MinecraftVersion.isAtLeast(MinecraftVersion.v1_13_0))
            return new DamageableItemInfo(itemStack);
        else
            return new LegacyItemDamageInfo(itemStack);
    }

    public ItemDamageInfo createNoDamageInfo(final ItemDamageInfo info) {
        return new NonDamagedItemInfo(info);
    }
}
