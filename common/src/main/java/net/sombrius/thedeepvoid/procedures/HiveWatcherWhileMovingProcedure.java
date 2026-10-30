package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.entity.WatcherBlockadeEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;

public class HiveWatcherWhileMovingProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("speedBuildUp", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("speedBuildUp", 0) + 0.025));
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 2.5
				&& (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 4) {
			if (!(!world.getEntitiesOfClass(WatcherBlockadeEntity.class, AABB.ofSize(new Vec3(x, y, z), 1, 1, 1), e -> true).isEmpty())) {
				if (world instanceof ServerLevel _serverLevel) {
					Entity entityinstance = TheDeepVoidModEntities.WATCHER_BLOCKADE.get().create(_serverLevel, null, BlockPos.containing(x, y, z), EntitySpawnReason.MOB_SUMMONED, false, false);
					if (entityinstance != null) {
						entityinstance.setYRot(world.getRandom().nextFloat() * 360.0F);
						if (entityinstance instanceof WatcherBlockadeEntity) {
							((WatcherBlockadeEntity) entityinstance).setAnimation("animation.boneblockade_spawn");
						}
						_serverLevel.addFreshEntity(entityinstance);
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.goat.ram_impact")), SoundSource.HOSTILE, 1, (float) 0.9);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.goat.ram_impact")), SoundSource.HOSTILE, 1, (float) 0.9, false);
					}
				}
			}
		}
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("moveInX", false) == true) {
			if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("positive", false) == true) {
				entity.setDeltaMovement(new Vec3((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("speedBuildUp", 0)), (-1), 0));
			} else {
				entity.setDeltaMovement(new Vec3((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("speedBuildUp", 0) * (-1)), (-1), 0));
			}
		} else if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("moveInZ", false) == true) {
			if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("positive", false) == true) {
				entity.setDeltaMovement(new Vec3(0, (-1), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("speedBuildUp", 0))));
			} else {
				entity.setDeltaMovement(new Vec3(0, (-1), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("speedBuildUp", 0) * (-1))));
			}
		}
	}
}
