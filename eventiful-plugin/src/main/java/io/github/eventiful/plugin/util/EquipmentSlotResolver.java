package io.github.eventiful.plugin.util;

import io.github.eventiful.plugin.VersionHandler;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Item;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Set;

@AllArgsConstructor
public class EquipmentSlotResolver {
    private static final EquipmentSlot[] ARMOR_PIECE_SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    private static final Set<Material> HEAD_MATERIALS = EnumSet.noneOf(Material.class);
    private static final int BODY_ARMOR_VERSION_INTRO_INT = 1_9;

    private final VersionHandler versionHandler;

    static {
        for (final Material material : Material.values())
            if (material.name().contains("HEAD") || material.name().contains("SKULL"))
                HEAD_MATERIALS.add(material);
    }

    @Nullable
    public EquipmentSlot getArmorSlotFor(final Item item) {
        return getArmorSlotFor(item.getItemStack());
    }

    @Nullable
    public EquipmentSlot getArmorSlotFor(final ItemStack item) {
        return getArmorSlotFor(item.getType());
    }

    @Nullable
    public EquipmentSlot getArmorSlotFor(final Material material) {
        if (EnchantmentTarget.ARMOR_HEAD.includes(material) || HEAD_MATERIALS.contains(material)) {
            return EquipmentSlot.HEAD;
        } else if (EnchantmentTarget.ARMOR_TORSO.includes(material)) {
            return EquipmentSlot.CHEST;
        } else if (EnchantmentTarget.ARMOR_LEGS.includes(material)) {
            return EquipmentSlot.LEGS;
        } else if (EnchantmentTarget.ARMOR_FEET.includes(material)) {
            return EquipmentSlot.FEET;
        } else if (material.name().contains("ARMOR")) {
            return versionHandler.getMinecraftVersionInt() >= BODY_ARMOR_VERSION_INTRO_INT
                    ? EquipmentSlot.BODY : null;
        } else {
            return null;
        }
    }

    public EquipmentSlot[] getArmorPieces() {
        return ARMOR_PIECE_SLOTS;
    }
}
