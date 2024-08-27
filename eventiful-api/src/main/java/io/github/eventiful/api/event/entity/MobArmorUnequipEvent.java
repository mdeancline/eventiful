package io.github.eventiful.api.event.entity;

import io.github.eventiful.api.event.armor.ArmorEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when a {@link LivingEntity} unequips an item formerly used as armor.
 *
 * @since 1.0.0
 */
public class MobArmorUnequipEvent extends ArmorEvent<Mob> implements Cancellable {
    private final Cause cause;
    private boolean cancel;

    @ApiStatus.Internal
    public MobArmorUnequipEvent(final Mob who, final EquipmentSlot slot, final Cause cause, final ItemStack armorItem) {
        super(who, slot);
        this.cause = cause;
        this.armorItem = armorItem;
    }

    /**
     * Gets the cause of the armor unequip event.
     *
     * @return The cause of the unequip event.
     */
    public Cause getCause() {
        return cause;
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
         * Indicates that the armor was replaced with a difference piece of armor.
         */
        REPLACED
    }
}
