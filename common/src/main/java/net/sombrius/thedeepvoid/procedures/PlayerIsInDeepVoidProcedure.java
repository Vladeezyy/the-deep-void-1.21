package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.level.biome.Biome;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;

import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

@EventBusSubscriber
public class PlayerIsInDeepVoidProcedure {
	private static final TagKey<Biome> BIOME_TAG_FALLING_CORPSE_SPAWN = TagKey.create(Registries.BIOME, Identifier.parse("the_deep_void:falling_corpse_spawn"));
	private static final ResourceKey<Level> DIM_DEEP_VOID = ResourceKey.create(Registries.DIMENSION, Identifier.parse("the_deep_void:deep_void"));
	private static final ResourceKey<Level> DIM_THE_PIT = ResourceKey.create(Registries.DIMENSION, Identifier.parse("the_deep_void:the_pit"));

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity.level().dimension()) == DIM_DEEP_VOID) {
			if (DeepVoidConfigConfiguration.PLAYERHALLUCINATES.get() == true) {
				if (entity.getY() < 1 && (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.HALLUCINATE.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.HALLUCINATE.holder()).getDuration() : 0) <= 8) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.HALLUCINATE.holder(), 140, 0, false, false));
				}
			}
			if (y <= 20) {
				if (TheDeepVoidModVariables.of(entity).cave_tremble >= 10000) {
					{
						double _setval = 0;
						TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
						_vars.cave_tremble = _setval;
						_vars.syncPlayerVariables(entity);
					}
					if (Math.random() < 0.4) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.TREMBLE.holder(), 200, 0, false, false));
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:deep_rumble")), SoundSource.AMBIENT, 15, 1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:deep_rumble")), SoundSource.AMBIENT, 15, 1, false);
							}
						}
					}
				} else {
					{
						double _setval = TheDeepVoidModVariables.of(entity).cave_tremble + 1;
						TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
						_vars.cave_tremble = _setval;
						_vars.syncPlayerVariables(entity);
					}
				}
			}
		}
		if (((entity.level().dimension()) == DIM_DEEP_VOID) == false
				&& TheDeepVoidModVariables.of(entity).StalkerSpawn == true) {
			{
				boolean _setval = false;
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.StalkerSpawn = _setval;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (world.getBiome(BlockPos.containing(x, y, z)).is(BIOME_TAG_FALLING_CORPSE_SPAWN) && y >= 40) {
			if (DeepVoidConfigConfiguration.SPAWNFALLINGCORPSE.get() == true) {
				if ((entity instanceof LivingEntity _livEnt14 && _livEnt14.hasEffect(TheDeepVoidModMobEffects.LURKER_HEAD_NEAR.holder())) == false) {
					if (Math.random() < 0.005) {
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.FALLING_CORPSE.get().spawn(_level,
									BlockPos.containing(entity.getX() + Mth.nextDouble(RandomSource.create(), -20, 20), entity.getY() + 25, entity.getZ() + Mth.nextDouble(RandomSource.create(), -20, 20)), EntitySpawnReason.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
					}
				}
			}
		}
		if (TheDeepVoidModVariables.MapVariables.get(world).StalkerDespawned == true) {
			TheDeepVoidModVariables.MapVariables.get(world).StalkerDespawned = false;
			TheDeepVoidModVariables.MapVariables.get(world).syncData(world);
			{
				boolean _setval = false;
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.StalkerSpawn = _setval;
				_vars.syncPlayerVariables(entity);
			}
			{
				boolean _setval = false;
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.StalkerCountFinish = _setval;
				_vars.syncPlayerVariables(entity);
			}
			{
				double _setval = 0;
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.StalkerCount = _setval;
				_vars.syncPlayerVariables(entity);
			}
		}
		if ((entity.level().dimension()) == DIM_THE_PIT) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.WEAVER_CURSE.holder(), 20, 0, false, false));
		}
	}
}
