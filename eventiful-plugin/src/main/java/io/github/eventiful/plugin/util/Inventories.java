package io.github.eventiful.plugin.util;

import io.github.eventiful.api.event.entity.ArmorEvent;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Set;

@UtilityClass
public class Inventories {
    private static final Set<Material> HEAD_MATERIALS = EnumSet.noneOf(Material.class);

    static {
        for (final Material material : Material.values())
            if (material.name().contains("HEAD") || material.name().contains("SKULL"))
                HEAD_MATERIALS.add(material);
    }

    public void equipArmorFrom(final ArmorEvent<?> event, final InventoryWrapper inventory) {
        inventory.equipArmor(new ArmorItemHolder(event.getSlot(), event.getArmorItem()));
    }

    @Nullable
    public EquipmentSlot matchEquipmentSlot(final ItemStack item) {
        return matchEquipmentSlot(item.getType());
    }

    @Nullable
    public EquipmentSlot matchEquipmentSlot(final Material material) {
        if (EnchantmentTarget.ARMOR_HEAD.includes(material) || HEAD_MATERIALS.contains(material)) {
            return EquipmentSlot.HEAD;
        } else if (EnchantmentTarget.ARMOR_TORSO.includes(material) || material.name().equals("ELYTRA")) {
            return EquipmentSlot.CHEST;
        } else if (EnchantmentTarget.ARMOR_LEGS.includes(material)) {
            return EquipmentSlot.LEGS;
        } else if (EnchantmentTarget.ARMOR_FEET.includes(material)) {
            return EquipmentSlot.FEET;
        } else {
            return null;
        }
    }
}
