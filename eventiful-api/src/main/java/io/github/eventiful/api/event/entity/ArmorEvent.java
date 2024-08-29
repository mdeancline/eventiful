package io.github.eventiful.api.event.entity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an event related to armor interactions with a {@link LivingEntity}.
 *
 * @since 1.0.0
 */
public abstract class ArmorEvent extends TypedEntityEvent<LivingEntity> {
    private final ItemStack currentItem;

    protected ArmorEvent(@NotNull final LivingEntity who, @NotNull final ItemStack currentItem) {
        super(who);
        this.currentItem = currentItem;
    }

    /**
     * Retrieves the {@link ItemStack} representing the current armor piece involved in the event.
     *
     * @return the armor item stack
     */
    public final ItemStack getCurrentItem() {
        return currentItem;
    }
}
