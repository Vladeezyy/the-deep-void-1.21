
package net.sombrius.thedeepvoid.world.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.PlayerEvent;
import net.sombrius.thedeepvoid.procedures.ThePitPlayerEntersDimensionProcedure;

public class ThePitDimension {
	@SubscribeEvent
	public static void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
		Entity entity = event.getEntity();
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		if (event.getTo() == ResourceKey.create(Registries.DIMENSION, Identifier.parse("the_deep_void:the_pit"))) {
			ThePitPlayerEntersDimensionProcedure.execute(world, entity);
		}
	}
}
