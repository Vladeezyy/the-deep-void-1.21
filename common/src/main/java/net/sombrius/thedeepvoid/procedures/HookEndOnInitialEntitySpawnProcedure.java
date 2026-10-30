package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.Identifier;
import net.minecraft.core.BlockPos;

public class HookEndOnInitialEntitySpawnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("despawn", 0) >= 600) {
			if (!entity.level().isClientSide())
				entity.discard();
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.fishing_bobber.retrieve")), SoundSource.PLAYERS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.fishing_bobber.retrieve")), SoundSource.PLAYERS, 1, 1, false);
				}
			}
		} else {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("despawn", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("despawn", 0) + 1));
		}
	}
}
