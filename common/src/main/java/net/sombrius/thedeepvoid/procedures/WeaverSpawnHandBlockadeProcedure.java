package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.entity.WeaverOfSoulsBossEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;

public class WeaverSpawnHandBlockadeProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 2) {
			if ((entity instanceof WeaverOfSoulsBossEntity _datEntL2 && _datEntL2.getEntityData().get(WeaverOfSoulsBossEntity.DATA_blockade)) == false) {
				if (entity instanceof WeaverOfSoulsBossEntity _datEntSetL)
					_datEntSetL.getEntityData().set(WeaverOfSoulsBossEntity.DATA_blockade, true);
				if (Math.random() < 0.25) {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.LASTING_SHADOW_HAND.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 6, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
								EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.LASTING_SHADOW_HAND.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 6, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 1),
								EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.LASTING_SHADOW_HAND.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 6, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 1),
								EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
				} else if (Math.random() < 0.25) {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.LASTING_SHADOW_HAND.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) - 6, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
								EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.LASTING_SHADOW_HAND.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) - 6, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 1),
								EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.LASTING_SHADOW_HAND.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) - 6, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 1),
								EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
				} else if (Math.random() < 0.3) {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.LASTING_SHADOW_HAND.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 6),
								EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.LASTING_SHADOW_HAND.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 1, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 6),
								EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.LASTING_SHADOW_HAND.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) - 1, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 6),
								EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
				} else if (Math.random() < 0.3) {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.LASTING_SHADOW_HAND.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 6),
								EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.LASTING_SHADOW_HAND.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 1, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 6),
								EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.LASTING_SHADOW_HAND.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) - 1, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 6),
								EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
				} else {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.LASTING_SHADOW_HAND.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 6, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
								EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.LASTING_SHADOW_HAND.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 6, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 1),
								EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.LASTING_SHADOW_HAND.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 6, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 1),
								EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
				}
			}
		}
	}
}
