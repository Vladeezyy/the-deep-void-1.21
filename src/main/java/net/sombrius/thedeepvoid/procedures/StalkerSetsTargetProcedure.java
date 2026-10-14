package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.sombrius.thedeepvoid.entity.StalkerEntity;

@EventBusSubscriber
public class StalkerSetsTargetProcedure {
	@SubscribeEvent
	public static void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
		execute(event, event.getOriginalTarget(), event.getEntity());
	}

	public static void execute(Entity entity, Entity sourceentity) {
		execute(null, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (sourceentity instanceof StalkerEntity && (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("the_deep_void:stalker_cannot_target"))) || entity == sourceentity)) {
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
