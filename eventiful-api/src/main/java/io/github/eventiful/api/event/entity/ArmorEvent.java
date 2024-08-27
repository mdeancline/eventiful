package io.github.eventiful.api.event.entity;

import io.github.eventiful.api.listener.EventListener;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
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
    private final Type type;
    private ItemStack armorItem;

    protected ArmorEvent(@NotNull final T who, @NotNull final Type type) {
        super(who);
        this.type = type;
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
     * Returns the type of armor involved in the event.
     *
     * @return the armor type
     */
    public Type getArmorType() {
        return type;
    }

    /**
     * Returns the {@link ItemStack} representing the armor involved in the event.
     *
     * @return the armor item stack
     */
    public ItemStack getArmorItem() {
        return armorItem;
    }

    /**
     * Sets the item stack representing the armor involved in the event.
     *
     * @param armorItem the new armor item stack
     */
    public void setArmorItem(final ItemStack armorItem) {
        this.armorItem = armorItem;
    }

    /**
     * Represents the different types of armor that can be involved in the event.
     *
     * @since 1.0.0
     */
    public enum Type {

        /**
         * Represents a helmet-type armor piece, which is equipped in the head slot (inventory slot 5).
         */
        HELMET(5),

        /**
         * Represents a chest plate-type armor piece, which is equipped in the torso slot (inventory slot 6).
         */
        CHEST_PLATE(6),

        /**
         * Represents leggings-type armor, which is equipped in the leg slot (inventory slot 7).
         */
        LEGGINGS(7),

        /**
         * Represents boots-type armor, which is equipped in the feet slot (inventory slot 8).
         */
        BOOTS(8);

        private static final Set<Material> HEAD_MATERIALS = EnumSet.noneOf(Material.class);

        private final int slot;

        Type(final int slot) {
            this.slot = slot;
        }

        static {
            for (final Material material : Material.values())
                if (material.name().contains("HEAD") || material.name().contains("SKULL"))
                    HEAD_MATERIALS.add(material);
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

        /**
         * Retrieves the inventory slot associated with this armor {@link Type}.
         * <p>
         * The returned slot number corresponds to the default inventory slot where this armor type
         * would be equipped (e.g., helmet in slot 5, chest plate in slot 6, etc.).
         * </p>
         *
         * @return The inventory slot number associated with this armor type.
         */
        public int getInventorySlot() {
            return slot;
        }
    }
}
