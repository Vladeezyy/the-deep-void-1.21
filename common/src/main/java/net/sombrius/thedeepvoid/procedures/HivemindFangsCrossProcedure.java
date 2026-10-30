package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.entity.MisanthropicHivemindEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;

public class HivemindFangsCrossProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof MisanthropicHivemindEntity) {
			((MisanthropicHivemindEntity) entity).setAnimation("animation.hivemind_scream");
		}
		TheDeepVoidMod.queueServerWork(10, () -> {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX() + 2, entity.getY(), entity.getZ()), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX() - 2, entity.getY(), entity.getZ()), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() + 2), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() - 2), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
		});
		TheDeepVoidMod.queueServerWork(20, () -> {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX() + 6, entity.getY(), entity.getZ()), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX() - 6, entity.getY(), entity.getZ()), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() + 6), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() - 6), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
		});
		TheDeepVoidMod.queueServerWork(30, () -> {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX() + 10, entity.getY(), entity.getZ()), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX() - 10, entity.getY(), entity.getZ()), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() + 10), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() - 10), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
		});
		TheDeepVoidMod.queueServerWork(40, () -> {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX() + 14, entity.getY(), entity.getZ()), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX() - 14, entity.getY(), entity.getZ()), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() + 14), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() - 14), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
		});
		TheDeepVoidMod.queueServerWork(50, () -> {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX() + 18, entity.getY(), entity.getZ()), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX() - 18, entity.getY(), entity.getZ()), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() + 18), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.FLESH_FANGS.get().spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() - 18), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
		});
	}
}
