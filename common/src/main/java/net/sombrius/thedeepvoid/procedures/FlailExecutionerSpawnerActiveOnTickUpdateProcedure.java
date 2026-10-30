package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.entity.FlailExecutionerEntity;

public class FlailExecutionerSpawnerActiveOnTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (!(!world.getEntitiesOfClass(FlailExecutionerEntity.class, AABB.ofSize(new Vec3(x, y, z), 200, 200, 200), e -> true).isEmpty())) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.respawn_anchor.deplete")), SoundSource.BLOCKS, 2, (float) 0.8);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.respawn_anchor.deplete")), SoundSource.BLOCKS, 2, (float) 0.8, false);
				}
			}
			world.destroyBlock(BlockPos.containing(x, y, z), false);
			world.destroyBlock(BlockPos.containing(x, y - 1, z), false);
		}
	}
}
