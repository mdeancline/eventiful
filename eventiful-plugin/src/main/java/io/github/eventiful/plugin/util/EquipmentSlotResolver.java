package io.github.eventiful.plugin.util;

import lombok.AllArgsConstructor;
import net.insprill.spigotutils.MinecraftVersion;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Set;

@AllArgsConstructor
public class EquipmentSlotResolver {
    private static final EquipmentSlot[] ARMOR_PIECE_SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    private static final Set<EntityType> ENTITIES_WITH_BODY_ARMOR = EnumSet.of(EntityType.HORSE, EntityType.WOLF);
    private static final Set<Material> HEAD_MATERIALS = EnumSet.noneOf(Material.class);

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
            final EquipmentSlot[] slots = getEntityArmorSlots();
            return slots.length == 1 ? slots[0] : null;
        } else {
            return null;
        }
    }

    private EquipmentSlot[] getEntityArmorSlots() {
        return MinecraftVersion.isAtLeast(MinecraftVersion.v1_21_0)
                ? new EquipmentSlot[]{EquipmentSlot.BODY}
                : ARMOR_PIECE_SLOTS;
    }

    public EquipmentSlot[] getArmorSlotsFor(final LivingEntity entity) {
        final EntityEquipment equipment = entity.getEquipment();
        if (equipment == null)
            return null;

        return ENTITIES_WITH_BODY_ARMOR.contains(entity.getType())
                ? getEntityArmorSlots()
                : ARMOR_PIECE_SLOTS;
    }
}
