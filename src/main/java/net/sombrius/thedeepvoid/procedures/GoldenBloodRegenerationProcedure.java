package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.sombrius.thedeepvoid.entity.FalseHydraArmEntity;
import net.sombrius.thedeepvoid.entity.FalseHydraEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class GoldenBloodRegenerationProcedure {
	@SubscribeEvent
	public static void onEntityHealed(LivingHealEvent event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getAmount());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double amount) {
		execute(null, world, x, y, z, entity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, double amount) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.GOLDEN_BLOOD)) {
			if (event instanceof ICancellableEvent _cancellable) {
				_cancellable.setCanceled(true);
			}
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)
						+ amount * ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.GOLDEN_BLOOD) ? _livEnt.getEffect(TheDeepVoidModMobEffects.GOLDEN_BLOOD).getAmplifier() : 0) + 0.5)));
		}
		if (entity instanceof LivingEntity _livEnt4 && _livEnt4.hasEffect(TheDeepVoidModMobEffects.DEBILITATED)) {
			if (Math.random() < 0.4) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
			}
		}
		if (entity instanceof LivingEntity _livEnt5 && _livEnt5.hasEffect(TheDeepVoidModMobEffects.GLUTTONY) && !world.getEntitiesOfClass(FalseHydraEntity.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> true).isEmpty()) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(64 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof FalseHydraEntity || entityiterator instanceof FalseHydraArmEntity) {
						if ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > 0) {
							if (entityiterator instanceof LivingEntity _entity)
								_entity.setHealth((float) ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + amount * 5));
						}
					}
				}
			}
		}
	}
}
