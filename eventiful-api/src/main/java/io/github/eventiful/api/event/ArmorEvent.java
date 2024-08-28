package io.github.eventiful.api.event;

import io.github.eventiful.api.listener.EventListener;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

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
    private final ItemStack oldItem;
    private final ItemStack newItem;

    protected ArmorEvent(@NotNull final T who, @NotNull final EquipmentSlot slot, @NotNull final ItemStack oldItem, @NotNull final ItemStack newItem) {
        super(who);
        this.slot = slot;
        this.oldItem = oldItem;
        this.newItem = newItem;
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
     * Retrieves the {@link ItemStack} representing the old armor piece involved in the event.
     *
     * @return the armor item stack
     */
    public final ItemStack getOldItem() {
        return oldItem;
    }

    /**
     * Retrieves the {@link ItemStack} representing the new armor piece involved in the event.
     *
     * @return the armor item stack
     */
    public final ItemStack getNewItem() {
        return newItem;
    }
}
