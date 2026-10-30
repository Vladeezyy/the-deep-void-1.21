package net.sombrius.thedeepvoid.mixins;

import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Mixin;

import net.sombrius.thedeepvoid.port.PortEntities;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.ArrayList;

/** The level's list of {@link PortEntities} (touched only on the level's own thread). */
@Mixin(Level.class)
public abstract class LevelMixin implements PortEntities.Holder {
	@Unique
	private final List<Entity> deepvoid$tracked = new ArrayList<>();

	@Override
	public List<Entity> deepvoid$tracked() {
		return this.deepvoid$tracked;
	}
}
