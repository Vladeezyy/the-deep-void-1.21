package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModParticleTypes;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class FallSoundWhenEnteringProcedure {
	@SubscribeEvent
	public static void onEntityFall(LivingFallEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity.level().dimension()) == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_deep_void:deep_void"))) {
			if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).FallSound == true) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.FallSound = false;
					_vars.syncPlayerVariables(entity);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.big_fall")), SoundSource.PLAYERS, (float) 1.2, 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.big_fall")), SoundSource.PLAYERS, (float) 1.2, 1, false);
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:bone_crack")), SoundSource.PLAYERS, (float) 1.2, (float) 0.8);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:bone_crack")), SoundSource.PLAYERS, (float) 1.2, (float) 0.8, false);
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.small_fall")), SoundSource.PLAYERS, (float) 1.2, (float) 0.8);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.small_fall")), SoundSource.PLAYERS, (float) 1.2, (float) 0.8, false);
					}
				}
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (TheDeepVoidModParticleTypes.BROKEN_BONE.get()), x, y, z, 12, 0.1, 0.1, 0.1, 0.1);
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.CRIT, x, y, z, 12, 0.1, 0.1, 0.1, 0.1);
			}
		}
	}
}
