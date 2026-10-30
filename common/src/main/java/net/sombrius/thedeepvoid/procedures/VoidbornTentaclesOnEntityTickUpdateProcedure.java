package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.entity.LightEntity;
import net.sombrius.thedeepvoid.entity.VoidbornTentaclesEntity;

public class VoidbornTentaclesOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		entity.setDeltaMovement(new Vec3(0, 0, 0));
		if ((entity instanceof VoidbornTentaclesEntity _datEntI ? _datEntI.getEntityData().get(VoidbornTentaclesEntity.DATA_count) : 0) == 200 && (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > 0) {
			if (entity instanceof VoidbornTentaclesEntity) {
				((VoidbornTentaclesEntity) entity).setAnimation("animation.voidbornTentacles_removeLight");
			}
			TheDeepVoidMod.queueServerWork(20, () -> {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(1 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator instanceof LightEntity) {
							entityiterator.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.MOB_ATTACK), entity), 1);
						}
					}
				}
				if (!entity.level().isClientSide())
					entity.discard();
			});
		} else {
			if (entity instanceof VoidbornTentaclesEntity _datEntSetI)
				_datEntSetI.getEntityData().set(VoidbornTentaclesEntity.DATA_count, (int) ((entity instanceof VoidbornTentaclesEntity _datEntI ? _datEntI.getEntityData().get(VoidbornTentaclesEntity.DATA_count) : 0) + 1));
		}
	}
}
