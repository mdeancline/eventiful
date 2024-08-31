package io.github.eventiful.api.event.entity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a {@link LivingEntity}'s armor item takes damage as a result of it being damaged.
 *
 * @since 1.0.0
 */
public class ArmorDamageEvent extends ArmorEvent implements Cancellable {
    private final EntityDamageEvent.DamageCause cause;
    private final double inflictedDamage;
    private boolean cancel;

    @ApiStatus.Internal
    public ArmorDamageEvent(@NotNull final LivingEntity who, @NotNull final ItemStack currentItem, final EntityDamageEvent.DamageCause cause, final double inflictedDamage) {
        super(who, currentItem);
        this.cause = cause;
        this.inflictedDamage = inflictedDamage;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }

    /**
     * Retrieves the cause of the damage inflicted on the armor item.
     *
     * @return the cause of the damage
     */
    public EntityDamageEvent.DamageCause getCause() {
        return cause;
    }

    /**
     * Retrieves the amount of damage inflicted on the armor item. This value takes into account internally set armor
     * toughness, as well as any durability-modifying enchantments and potion effects.
     *
     * @return the amount of damage inflicted on the armor item
     */
    public double getInflictedDamage() {
        return inflictedDamage;
    }
}
