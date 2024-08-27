package io.github.eventiful.api.event.entity;

import io.github.eventiful.api.event.armor.ArmorEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when a {@link LivingEntity} equips an item to be used as armor.
 *
 * @since 1.0.0
 */
public class MobArmorEquipEvent extends ArmorEvent<Mob> implements Cancellable {
    private final Cause cause;
    private boolean cancelled;

    @ApiStatus.Internal
    public MobArmorEquipEvent(final Mob who, final EquipmentSlot slot, final Cause cause) {
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
     * Retrieves the natural cause of this event being triggered.
     *
     * @return The natural cause of the armor equip event.
     */
    public Cause cause() {
        return cause;
    }

    public void setArmorItem(final ItemStack armorItem) {
        this.armorItem = armorItem;
    }

    /**
     * Represents the various reasons for a {@code Mob} equipping a piece of armor.
     *
     * @since 1.0.0
     */
    public enum Cause {

        /**
         * Indicates that a {@code LivingEntity} equipped armor due to an outside source applying it for them.
         */
        FROM_APPLICATOR,

        /**
         * Indicates that a {@code LivingEntity} equipped armor after picking it up as loot.
         */
        ITEM_PICKUP
    }
}
