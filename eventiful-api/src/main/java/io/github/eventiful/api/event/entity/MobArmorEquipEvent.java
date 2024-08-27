package io.github.eventiful.api.event.entity;

import org.bukkit.block.Dispenser;
import org.bukkit.entity.Mob;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when a {@link Mob} equips an item to be used as armor.
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
     * Retrieves the cause of this event being triggered.
     *
     * @return The cause of the armor equip event.
     */
    public Cause getCause() {
        return cause;
    }

    /**
     * Represents the various reasons for a {@code Mob} equipping a piece of armor.
     *
     * @since 1.0.0
     */
    public enum Cause {

        /**
         * Indicates that a {@code Mob} equipped armor due to being in range of a {@link Dispenser} that
         * dispensed the armor onto the entity.
         *
         * @apiNote This requires a server implementation supporting
         * <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/block/BlockDispenseArmorEvent.html">BlockDispenseArmorEvent</a>.
         */
        DISPENSER,

        /**
         * Indicates that a {@code Mob} equipped armor after picking it up as loot.
         */
        LOOT_PICKUP,

        /**
         * Indicates that a {@code Mob} equipped armor through external means, such as a command or plugin.
         */
        EXTERNAL
    }
}
