package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;

public class MarrowRootsSpawnOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.CRIT, x, y, z, 4, 0, 0.1, 0, 0.1);
		if (Math.random() < 0.25) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.mangrove_roots.break")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.95, 1));
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.mangrove_roots.break")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.95, 1), false);
				}
			}
		}
		TheDeepVoidMod.queueServerWork(60, () -> {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("spawn", true);
		});
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("spawn", false) == true) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("spawn", false);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.mangrove_roots.place")), SoundSource.HOSTILE, 1,
							(float) Mth.nextDouble(RandomSource.create(), 0.8, 0.85));
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.mangrove_roots.place")), SoundSource.HOSTILE, 1,
							(float) Mth.nextDouble(RandomSource.create(), 0.8, 0.85), false);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.MARROW_ROOTS.get().spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (!entity.level().isClientSide())
				entity.discard();
		}
	}
}
