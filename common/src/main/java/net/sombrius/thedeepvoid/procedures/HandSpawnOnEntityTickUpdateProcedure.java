package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.entity.ShadowHandEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;

public class HandSpawnOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!(!world.getEntitiesOfClass(ShadowHandEntity.class, AABB.ofSize(new Vec3(x, (y + 0.5), z), 0.5, 0.5, 0.5), e -> true).isEmpty())) {
			if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("spawn") >= 60) {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("spawn", 0);
				if (world instanceof ServerLevel _serverLevel) {
					Entity entityinstance = TheDeepVoidModEntities.SHADOW_HAND.get().create(_serverLevel, null, BlockPos.containing(x, y + 0.5, z), MobSpawnType.MOB_SUMMONED, false, false);
					if (entityinstance != null) {
						entityinstance.setYRot(world.getRandom().nextFloat() * 360.0F);
						if (entityinstance instanceof ShadowHandEntity) {
							((ShadowHandEntity) entityinstance).setAnimation("animation.shadowHand_exit");
						}
						if (entityinstance instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 99, false, false));
						_serverLevel.addFreshEntity(entityinstance);
					}
				}
				if (!entity.level().isClientSide())
					entity.discard();
			} else {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("spawn", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("spawn") + 1));
			}
		}
	}
}
