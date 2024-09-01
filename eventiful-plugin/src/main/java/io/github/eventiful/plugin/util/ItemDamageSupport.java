package io.github.eventiful.plugin.util;

import lombok.experimental.UtilityClass;
import net.insprill.spigotutils.MinecraftVersion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

@UtilityClass
public class ItemDamageSupport {
    public void setDamage(final ItemStack currentItem, final ItemDamageInfo info) {
        if (MinecraftVersion.isAtLeast(MinecraftVersion.v1_13_0)) {
            final ItemMeta meta = currentItem.getItemMeta();
            final Damageable damageableMeta = toDamageableMeta(Objects.requireNonNull(meta).clone());
            damageableMeta.setDamage((int) info.getInitialDamage());
            currentItem.setItemMeta(damageableMeta);
        } else
            //noinspection deprecation
            currentItem.setDurability((short) info.getInitialDamage());
    }

    public Damageable toDamageableMeta(final ItemMeta meta) {
        if (!(meta instanceof Damageable))
            throw new IllegalArgumentException("ItemMeta is not Damageable");

        return (Damageable) meta;
    }

    public ItemDamageInfo newInfo(final ItemStack itemStack) {
        return MinecraftVersion.isAtLeast(MinecraftVersion.v1_13_0)
                ? new ItemDamageInfo_1_13(itemStack)
                : new LegacyItemDamageInfo(itemStack);
    }

    public ItemDamageInfo toNoDamageInfo(final ItemDamageInfo info) {
        return new NonDamagedItemInfo(info);
    }

    public ItemDamageCalculator newItemDamageCalculator() {
        return MinecraftVersion.isAtLeast(MinecraftVersion.v1_9_0)
                ? new ItemDamageCalculator_1_9()
                : new LegacyItemDamageCalculator();
    }
}
