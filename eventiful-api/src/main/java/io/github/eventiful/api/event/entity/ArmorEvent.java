package io.github.eventiful.api.event.entity;

import io.github.eventiful.api.listener.EventListener;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

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

    protected ArmorEvent(final T who, final Type type) {
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
     * Enum representing the different types of armor that can be involved in the event.
     */
    public enum Type {
        HELMET,
        CHEST_PLATE,
        LEGGINGS,
        BOOTS
    }
}
