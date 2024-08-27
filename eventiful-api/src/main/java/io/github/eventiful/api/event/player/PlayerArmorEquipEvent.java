package io.github.eventiful.api.event.player;

import io.github.eventiful.api.event.armor.ArmorEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a {@code Player} equips an item to be used as armor.
 *
 * @since 1.0.0
 */
public class PlayerArmorEquipEvent extends ArmorEvent<Player> implements Cancellable {
    private final Cause cause;
    private boolean cancelled;

    @ApiStatus.Internal
    public PlayerArmorEquipEvent(final Player who, final EquipmentSlot slot, final Cause cause) {
        super(who, slot);
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
     * Sets the item stack representing the armor involved in the event.
     *
     * @param armorItem the new armor item stack
     */
    public void setArmorItem(@NotNull final ItemStack armorItem) {
        this.armorItem = armorItem;
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

        DISPENSER,

        DRAG,

        /**
         * Indicates that the player equips the armor by holding it and then right-clicking.
         */
        RIGHT_CLICK,

        SHIFT_CLICK
    }
}
