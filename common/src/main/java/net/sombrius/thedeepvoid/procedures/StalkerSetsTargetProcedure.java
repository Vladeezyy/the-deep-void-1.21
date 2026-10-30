package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.LivingChangeTargetEvent;
import net.sombrius.thedeepvoid.entity.StalkerEntity;

@EventBusSubscriber
public class StalkerSetsTargetProcedure {
	@SubscribeEvent
	public static void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
		execute(event, event.getOriginalAboutToBeSetTarget(), event.getEntity());
	}

	public static void execute(Entity entity, Entity sourceentity) {
		execute(null, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (sourceentity instanceof StalkerEntity && (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, Identifier.parse("the_deep_void:stalker_cannot_target"))) || entity == sourceentity)) {
			if (sourceentity instanceof Mob) {
				try {
					((Mob) sourceentity).setTarget(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
