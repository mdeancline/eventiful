package io.github.eventiful.api.event.entity;

import io.github.eventiful.api.listener.EventListener;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Set;

/**
 * Represents an event related to armor interactions with a living entity.
 * This event can be extended to handle specific armor-related scenarios.
 *
 * @param <T> any type extending {@code LivingEntity}
 * @see ArmorEvent#type()
 * @since 1.0.0
 */
public abstract class ArmorEvent<T extends LivingEntity> extends SpecificEntityEvent<T> {
    private final EquipmentSlot slot;
    protected ItemStack armorItem;

    protected ArmorEvent(@NotNull final T who, @NotNull final EquipmentSlot slot) {
        super(who);
        this.slot = slot;
    }

    /**
     * Retrieves the class type for {@link ArmorEvent} with a generic {@code LivingEntity} type parameter so that
     * {@link EventListener}s can be properly registered with it.
     *
     * @return the event type
     */
    @SuppressWarnings("unchecked")
    public static Class<ArmorEvent<LivingEntity>> type() {
        return (Class<ArmorEvent<LivingEntity>>) (Class<?>) ArmorEvent.class;
    }

    /**
     * Returns the {@link EquipmentSlot} involved in the event.
     *
     * @return the slot
     */
    public final EquipmentSlot getSlot() {
        return slot;
    }

    /**
     * Returns the {@link ItemStack} representing the armor involved in the event.
     *
     * @return the armor item stack
     */
    public final ItemStack getArmorItem() {
        return armorItem;
    }

    /**
     * Represents the different types of armor that can be involved in the event.
     *
     * @since 1.0.0
     */
    public enum Type {

        /**
         * Represents a helmet-type armor piece, which is equipped in the head slot.
         */
        HELMET(EquipmentSlot.HEAD),

        /**
         * Represents a chest plate-type armor piece, which is equipped in the torso slot.
         */
        CHEST_PLATE(EquipmentSlot.CHEST),

        /**
         * Represents leggings-type armor, which is equipped in the leg slot.
         */
        LEGGINGS(EquipmentSlot.LEGS),

        /**
         * Represents boots-type armor, which is equipped in the feet slot.
         */
        BOOTS(EquipmentSlot.FEET);

        private static final Set<Material> HEAD_MATERIALS = EnumSet.noneOf(Material.class);

        private final EquipmentSlot slot;

        static {
            for (final Material material : Material.values())
                if (material.name().contains("HEAD") || material.name().contains("SKULL"))
                    HEAD_MATERIALS.add(material);
        }

        Type(final EquipmentSlot slot) {
            this.slot = slot;
        }

        /**
         * Determines the {@link Type} based on the provided {@link ItemStack}.
         *
         * @param itemStack The {@link ItemStack} to analyze.
         * @return The corresponding {@link Type} if the item is armor; {@code null} otherwise.
         */
        @Nullable
        public static Type of(@NotNull final ItemStack itemStack) {
            return of(itemStack.getType());
        }

        /**
         * Determines the {@link Type} based on the provided {@link Material}.
         *
         * @param material The {@link Material} to analyze.
         * @return The corresponding {@link Type} if the material is armor; {@code null} otherwise.
         */
        @Nullable
        public static Type of(@NotNull final Material material) {
            if (EnchantmentTarget.ARMOR_HEAD.includes(material) || HEAD_MATERIALS.contains(material)) {
                return HELMET;
            } else if (EnchantmentTarget.ARMOR_TORSO.includes(material) || material.name().equals("ELYTRA")) {
                return CHEST_PLATE;
            } else if (EnchantmentTarget.ARMOR_LEGS.includes(material)) {
                return LEGGINGS;
            } else if (EnchantmentTarget.ARMOR_FEET.includes(material)) {
                return BOOTS;
            } else {
                return null;
            }
        }

        public EquipmentSlot getEquipmentSlot() {
            return slot;
        }
    }
}
