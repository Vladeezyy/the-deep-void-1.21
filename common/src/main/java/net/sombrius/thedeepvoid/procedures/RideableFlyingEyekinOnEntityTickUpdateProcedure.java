package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class RideableFlyingEyekinOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("wingFlap") >= 25) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("wingFlap", 0);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:eyekin_fly")), SoundSource.HOSTILE, 3,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1));
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:eyekin_fly")), SoundSource.HOSTILE, 3,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1), false);
				}
			}
		} else {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("wingFlap", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("wingFlap") + 1));
		}
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("verticalMovement") == 1) {
			entity.setDeltaMovement(new Vec3((entity.getDeltaMovement().x()), 0.2, (entity.getDeltaMovement().z())));
		}
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("verticalMovement") == -1) {
			entity.setDeltaMovement(new Vec3((entity.getDeltaMovement().x()), (-0.2), (entity.getDeltaMovement().z())));
		}
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("horizontalMovement") == 1) {
			entity.setDeltaMovement(new Vec3((Math.sin(Math.toRadians(entity.getYRot() + 180)) * 0.8), (entity.getDeltaMovement().y()), (Math.cos(Math.toRadians(entity.getYRot())) * 0.8)));
		}
	}
}
