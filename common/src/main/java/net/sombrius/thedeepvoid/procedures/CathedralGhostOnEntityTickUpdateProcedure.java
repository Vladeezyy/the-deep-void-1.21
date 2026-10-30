package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class CathedralGhostOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:ring", 0) >= 200) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:ring", 0);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bell.resonate")), SoundSource.NEUTRAL, 4, (float) 0.8);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bell.resonate")), SoundSource.NEUTRAL, 4, (float) 0.8, false);
				}
			}
		} else {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:ring", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:ring", 0) + 1));
		}
	}
}
