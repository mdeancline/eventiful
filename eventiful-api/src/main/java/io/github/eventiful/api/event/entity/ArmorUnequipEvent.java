package io.github.eventiful.api.event.entity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when a {@link LivingEntity} unequips an item formerly used as armor.
 *
 * @since 1.0.0
 */
public class ArmorUnequipEvent extends ArmorEvent<LivingEntity> implements Cancellable {
    private final Cause cause;
    private boolean cancelled;

    @ApiStatus.Internal
    public ArmorUnequipEvent(final LivingEntity who, final Type type, final Cause cause) {
        super(who, type);
        this.cause = cause;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Gets the cause of the armor unequip event.
     *
     * @return The cause of the unequip event.
     */
    public Cause getCause() {
        return cause;
    }

    /**
     * Represents the various causes for an entity unequipping armor.
     *
     * @since 1.0.0
     */
    public enum Cause {

        /**
         * Indicates that the armor broke due to damage.
         */
        BROKE,

        /**
         * Indicates that the entity died, resulting in the armor being unequipped.
         */
        DEATH,

        /**
         * Indicates that the armor was unequipped by a dispenser.
         */
        DISPENSER,

        /**
         * Indicates that the armor was removed through external means, such as a command or plugin.
         */
        EXTERNAL
    }
}
