package io.github.eventiful.api.event.entity;

import io.github.eventiful.api.event.NonOperableHandlerList;
import io.github.eventiful.api.listener.EventListener;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * An abstract event class that is specifically tied to a certain type of {@link Entity}.
 * It extends {@link EntityEvent} and provides a type-safe mechanism for handling
 * events related to a specific entity type without worrying about type casting.
 *
 * @apiNote If you need an {@link EventListener} to handle all entity-related events, consider registering it with
 * {@code EntityEvent.class}.
 *
 * @param <T> the type of {@link Entity} associated with this event
 * @since 1.0.0
 */
public abstract class TypedEntityEvent<T extends Entity> extends EntityEvent {
    private final T entity;

    /**
     * Creates a new {@code TypedEntityEvent} for the specified entity.
     *
     * @param entity the entity involved in this event
     */
    protected TypedEntityEvent(@NotNull final T entity) {
        super(entity);
        this.entity = entity;
    }

    @NotNull
    @ApiStatus.Internal
    @Override
    public final HandlerList getHandlers() {
        return NonOperableHandlerList.getInstance();
    }

    @NotNull
    @Override
    public T getEntity() {
        return entity;
    }
}
