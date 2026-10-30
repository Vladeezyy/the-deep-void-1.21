package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.entity.BabyBoneCrawlerEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;

public class AncientPotPlantDestroyedByPlayerProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (Math.random() < 0.05) {
			for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 1, 4); index0++) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = TheDeepVoidModEntities.BABY_BONE_CRAWLER.get().spawn(_level, BlockPos.containing(x, y, z), EntitySpawnReason.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.silverfish.ambient")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 1, 1.2));
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.silverfish.ambient")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 1, 1.2), false);
					}
				}
			}
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(2 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof BabyBoneCrawlerEntity) {
						entityiterator.setDeltaMovement(new Vec3((Mth.nextDouble(RandomSource.create(), -0.5, 0.5)), 0.12, (Mth.nextDouble(RandomSource.create(), -0.5, 0.5))));
					}
				}
			}
		}
	}
}
