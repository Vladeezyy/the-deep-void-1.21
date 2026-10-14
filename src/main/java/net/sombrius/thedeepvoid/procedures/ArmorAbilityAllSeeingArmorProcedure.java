package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class ArmorAbilityAllSeeingArmorProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).allseeingArmor <= 0) {
			if (entity.onGround()) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.allseeingArmor = 12;
					_vars.syncPlayerVariables(entity);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.witch.throw")), SoundSource.PLAYERS, 1, (float) 0.8);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.witch.throw")), SoundSource.PLAYERS, 1, (float) 0.8, false);
					}
				}
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.POOF, x, y, z, 8, 0.8, 0.8, 0.8, 0.1);
				entity.setDeltaMovement(new Vec3((Math.sin(Math.toRadians(entity.getYRot() + 180)) * 1.8), (Math.sin(Math.toRadians(0 - entity.getXRot())) * 1.8), (Math.cos(Math.toRadians(entity.getYRot())) * 1.8)));
			}
		}
	}
}
