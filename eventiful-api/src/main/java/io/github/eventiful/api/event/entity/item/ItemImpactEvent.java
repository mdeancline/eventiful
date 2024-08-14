package io.github.eventiful.api.event.entity.item;

import io.github.eventiful.api.event.entity.SpecificEntityEvent;
import org.bukkit.entity.Item;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents an event triggered when an item impacts a target. It is used to handle the behavior of an item when it
 * collides with an entity, block, or other object after being launched or dropped.
 *
 * @since 1.0.0
 */
public abstract class ItemImpactEvent extends SpecificEntityEvent<Item> {
    private final ProjectileSource source;

    @ApiStatus.Internal
    public ItemImpactEvent(final Item what, final ProjectileSource source) {
        super(what);
        this.source = source;
    }

    /**
     * Retrieves the source that caused the item to be propelled.
     *
     * @return The source of the projectile.
     */
    public ProjectileSource getSource() {
        return source;
    }
}
