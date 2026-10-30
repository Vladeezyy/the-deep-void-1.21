package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.entity.WeaverOfSoulsBossEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;

public class WeaverSpawnHandWallProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 2) {
			if ((entity instanceof WeaverOfSoulsBossEntity _datEntL2 && _datEntL2.getEntityData().get(WeaverOfSoulsBossEntity.DATA_stunned)) == false) {
				if ((entity instanceof WeaverOfSoulsBossEntity _datEntI ? _datEntI.getEntityData().get(WeaverOfSoulsBossEntity.DATA_handWall) : 0) >= 400) {
					if (entity instanceof WeaverOfSoulsBossEntity _datEntSetI)
						_datEntSetI.getEntityData().set(WeaverOfSoulsBossEntity.DATA_handWall, 0);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.sculk_shrieker.shriek")), SoundSource.HOSTILE, 4, (float) 0.8);
						} else {
							_level.playLocalSound((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.sculk_shrieker.shriek")), SoundSource.HOSTILE, 4, (float) 0.8, false);
						}
					}
					if (entity instanceof WeaverOfSoulsBossEntity _datEntSetI)
						_datEntSetI.getEntityData().set(WeaverOfSoulsBossEntity.DATA_randomNumber, Mth.nextInt(RandomSource.create(), 1, 4));
					if ((entity instanceof WeaverOfSoulsBossEntity _datEntI ? _datEntI.getEntityData().get(WeaverOfSoulsBossEntity.DATA_randomNumber) : 0) == 1) {
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 2, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 4, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 6, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 8, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 10, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 12, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 14, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 16, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 18, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) + 20, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
					}
					if ((entity instanceof WeaverOfSoulsBossEntity _datEntI ? _datEntI.getEntityData().get(WeaverOfSoulsBossEntity.DATA_randomNumber) : 0) == 2) {
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) - 2, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) - 4, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) - 6, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) - 8, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) - 10, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) - 12, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) - 14, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) - 16, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) - 18, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0) - 20, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
					}
					if ((entity instanceof WeaverOfSoulsBossEntity _datEntI ? _datEntI.getEntityData().get(WeaverOfSoulsBossEntity.DATA_randomNumber) : 0) == 3) {
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 2),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 4),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 6),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 8),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 10),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 12),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 14),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 16),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 18),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) + 20),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
					}
					if ((entity instanceof WeaverOfSoulsBossEntity _datEntI ? _datEntI.getEntityData().get(WeaverOfSoulsBossEntity.DATA_randomNumber) : 0) == 4) {
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0)),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 2),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 4),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 6),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 8),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 10),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 12),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 14),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 16),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 18),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.SHADOW_HAND.get().spawn(_level,
									BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:startingZ", 0) - 20),
									EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
					}
				} else {
					if (entity instanceof WeaverOfSoulsBossEntity _datEntSetI)
						_datEntSetI.getEntityData().set(WeaverOfSoulsBossEntity.DATA_handWall, (int) ((entity instanceof WeaverOfSoulsBossEntity _datEntI ? _datEntI.getEntityData().get(WeaverOfSoulsBossEntity.DATA_handWall) : 0) + 1));
				}
			}
		}
	}
}
