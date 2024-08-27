package io.github.eventiful.api.event.armor;

import io.github.eventiful.api.event.entity.SpecificEntityEvent;
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
}
