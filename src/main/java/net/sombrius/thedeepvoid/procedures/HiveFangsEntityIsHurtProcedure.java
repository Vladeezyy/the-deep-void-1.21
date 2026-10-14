package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;
import net.sombrius.thedeepvoid.entity.HiveFangsEntity;
import net.sombrius.thedeepvoid.entity.SummonedHiveFangsEntity;

@EventBusSubscriber
public class HiveFangsEntityIsHurtProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity());
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof HiveFangsEntity || entity instanceof SummonedHiveFangsEntity) {
			if (event instanceof ICancellableEvent _cancellable) {
				_cancellable.setCanceled(true);
			}
		}
	}
}
