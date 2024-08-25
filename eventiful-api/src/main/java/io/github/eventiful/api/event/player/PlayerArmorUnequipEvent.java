package io.github.eventiful.api.event.player;

import io.github.eventiful.api.event.entity.ArmorEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when a {@code Player} unequips an item formerly used as armor.
 *
 * @since 1.0.0
 */
public class PlayerArmorUnequipEvent extends ArmorEvent<Player> implements Cancellable {
    private final Cause cause;
    private boolean cancelled;

    @ApiStatus.Internal
    public PlayerArmorUnequipEvent(final Player player, final Type type, final Cause cause) {
        super(player, type);
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
     * Represents the various causes for a player unequipping armor.
     */
    public enum Cause {

        /**
         * The armor broke due to damage.
         */
        BROKE,

        /**
         * The player died, resulting in the armor being unequipped.
         */
        DEATH,

        /**
         * The armor was unequipped by a dispenser.
         */
        DISPENSER,

        /**
         * The armor was removed due to the player's inventory being cleared.
         */
        INVENTORY_CLEARED
    }
}
