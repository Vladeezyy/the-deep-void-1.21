package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

/** An entity is about to change dimension (server); canceling it keeps it where it is */
public class EntityTravelToDimensionEvent extends EntityEvent implements ICancellableEvent {
	private final ResourceKey<Level> dimension;

	public EntityTravelToDimensionEvent(Entity entity, ResourceKey<Level> dimension) {
		super(entity);
		this.dimension = dimension;
	}

	public ResourceKey<Level> getDimension() {
		return this.dimension;
	}
}
