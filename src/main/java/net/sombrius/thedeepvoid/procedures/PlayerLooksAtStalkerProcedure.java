package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortEntities;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.sombrius.thedeepvoid.entity.StalkerEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class PlayerLooksAtStalkerProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double distance = 0;
		if (!PortEntities.find(world, StalkerEntity.class, AABB.ofSize(new Vec3(x, y, z), 250, 250, 250)).isEmpty()) {
			distance = 1;
			for (int index0 = 0; index0 < 20; index0++) {
				{
					final Vec3 _center = new Vec3((entity.getX() + entity.getLookAngle().x * distance), (entity.getY() + entity.getLookAngle().y * distance), (entity.getZ() + entity.getLookAngle().z * distance));
					List<? extends Entity> _entfound = PortEntities.find(world, StalkerEntity.class, new AABB(_center, _center).inflate(1 / 2d)).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator instanceof StalkerEntity
								&& !world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ())), 28, 28, 28), e -> true).isEmpty() == false) {
							if (!(entity instanceof LivingEntity _livEnt12 && _livEnt12.hasEffect(MobEffects.BLINDNESS)) && !(entity instanceof LivingEntity _livEnt13 && _livEnt13.hasEffect(MobEffects.DARKNESS))) {
								if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 99, false, false));
								if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.STARE, 5, 0, false, false));
							}
						}
					}
				}
				distance = distance + 1;
			}
		}
	}
}
