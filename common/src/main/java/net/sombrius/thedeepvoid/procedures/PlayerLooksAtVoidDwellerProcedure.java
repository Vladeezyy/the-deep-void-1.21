package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortEntities;
import net.minecraft.world.level.Level;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.VoidDwellerEntity;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class PlayerLooksAtVoidDwellerProcedure {
	private static final ResourceKey<Level> DIM_DEEP_VOID = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_deep_void:deep_void"));

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
		if (!PortEntities.find(world, VoidDwellerEntity.class, AABB.ofSize(new Vec3(x, y, z), 50, 50, 50)).isEmpty()) {
			distance = 1;
			for (int index0 = 0; index0 < 20; index0++) {
				{
					final Vec3 _center = new Vec3((entity.getX() + entity.getLookAngle().x * distance), (entity.getY() + entity.getLookAngle().y * distance), (entity.getZ() + entity.getLookAngle().z * distance));
					List<? extends Entity> _entfound = PortEntities.find(world, VoidDwellerEntity.class, new AABB(_center, _center).inflate(1 / 2d)).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator instanceof VoidDwellerEntity) {
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 99, false, false));
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 5, 0, false, false));
						}
					}
				}
				distance = distance + 1;
			}
		}
		if (TheDeepVoidModVariables.of(entity).StalkerCount > 0 && !((entity.level().dimension()) == DIM_DEEP_VOID)
				&& DeepVoidConfigConfiguration.STALKED.get() == false) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.StalkerCount = 0;
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}
