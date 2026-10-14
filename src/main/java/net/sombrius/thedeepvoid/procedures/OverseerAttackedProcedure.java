package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;
import net.sombrius.thedeepvoid.entity.BigOverseerEntity;
import net.sombrius.thedeepvoid.entity.BuiltBigOverseerPillarEntity;
import net.sombrius.thedeepvoid.entity.BuiltOverseerPillarEntity;
import net.sombrius.thedeepvoid.entity.GhostlyNightmareEntity;
import net.sombrius.thedeepvoid.entity.OverseerBigPillarEntity;
import net.sombrius.thedeepvoid.entity.OverseerEntity;
import net.sombrius.thedeepvoid.entity.OverseerPillarEntity;

@EventBusSubscriber
public class OverseerAttackedProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(Entity entity, Entity sourceentity) {
		execute(null, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (sourceentity instanceof LivingEntity) {
			if (entity instanceof OverseerEntity || entity instanceof OverseerPillarEntity || entity instanceof BigOverseerEntity || entity instanceof OverseerBigPillarEntity || entity instanceof BuiltOverseerPillarEntity
					|| entity instanceof BuiltBigOverseerPillarEntity) {
				if (!((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof PickaxeItem)) {
					if (event instanceof ICancellableEvent _cancellable) {
						_cancellable.setCanceled(true);
					}
				}
			}
		}
		if (entity instanceof GhostlyNightmareEntity) {
			if (event instanceof ICancellableEvent _cancellable) {
				_cancellable.setCanceled(true);
			}
		}
	}
}
