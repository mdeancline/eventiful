package io.github.eventiful.api.event.player.armor;

import io.github.eventiful.api.event.entity.armor.ArmorEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Called when a {@code Player} equips an item to be used as armor.
 *
 * @since 1.0.0
 */
public class PlayerArmorEquipEvent extends ArmorEvent<Player> implements Cancellable {
    private final Cause cause;
    private boolean cancelled;

    /**
     * Constructs a new {@code PlayerArmorEquipEvent}.
     *
     * @param who   the player equipping the armor.
     * @param type  the type of armor being equipped.
     * @param cause the cause or method by which the armor is being equipped.
     */
    public PlayerArmorEquipEvent(final Player who, final Type type, final Cause cause) {
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
     * Retrieves the cause or method by which the armor is being equipped.
     *
     * @return the cause of the armor equip event.
     */
    public Cause getCause() {
        return cause;
    }

    /**
     * Represents various situations for a {@code Player} equipping a piece of armor.
     */
    public enum Cause {

        /**
         * The player equips the armor directly from the hotbar.
         */
        HOTBAR,

        /**
         * The player swaps the item from the hotbar with an existing armor piece.
         */
        HOTBAR_SWAP,

        /**
         * The player drags the armor item from the inventory to the armor slot.
         */
        INVENTORY_DRAG,

        /**
         * The player uses the shift-click method to equip the armor from the inventory.
         */
        INVENTORY_SHIFT_CLICK
    }
}
