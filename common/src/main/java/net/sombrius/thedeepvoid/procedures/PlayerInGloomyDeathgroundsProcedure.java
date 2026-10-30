package net.sombrius.thedeepvoid.procedures;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.sombrius.thedeepvoid.port.PortEntities;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.FalseHydraEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class PlayerInGloomyDeathgroundsProcedure {
	private static final Identifier BIOME_CRAWLER_NEST = Identifier.parse("the_deep_void:crawler_nest");
	private static final Identifier BIOME_DRIFTING_MONOLITHS = Identifier.parse("the_deep_void:drifting_monoliths");
	private static final Identifier BIOME_GAOL_OF_HERETICS = Identifier.parse("the_deep_void:gaol_of_heretics");
	private static final Identifier BIOME_GATHERING_GROUNDS = Identifier.parse("the_deep_void:gathering_grounds");
	private static final Identifier BIOME_MISTED_REMNANTS = Identifier.parse("the_deep_void:misted_remnants");
	private static final Identifier BIOME_THE_GAOL = Identifier.parse("the_deep_void:the_gaol");

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
		if (world.getBiome(BlockPos.containing(x, y, z)).is(BIOME_GATHERING_GROUNDS)) {
			if (TheDeepVoidModVariables.of(entity).gatheringAmbience >= 400) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.gatheringAmbience = 0;
					_vars.syncPlayerVariables(entity);
				}
				if (Math.random() < 0.4) {
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
							_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound the_deep_void:soul_passing_by ambient @s ~ ~ ~ 10 1 0.5");
						}
					}
				}
			} else {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.gatheringAmbience = TheDeepVoidModVariables.of(entity).gatheringAmbience + 1;
					_vars.syncPlayerVariables(entity);
				}
			}
		} else if (world.getBiome(BlockPos.containing(x, y, z)).is(BIOME_MISTED_REMNANTS)) {
			if (TheDeepVoidModVariables.of(entity).mistedRemnantAmbience >= 2000) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.mistedRemnantAmbience = 0;
					_vars.syncPlayerVariables(entity);
				}
				if (Math.random() < 0.1) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x + 10, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound((x + 10), y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
					TheDeepVoidMod.queueServerWork(20, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x + 8, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound((x + 8), y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
					});
					TheDeepVoidMod.queueServerWork(40, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x + 6, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound((x + 6), y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
					});
					TheDeepVoidMod.queueServerWork(60, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x + 4, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound((x + 4), y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
					});
				} else if (Math.random() < 0.1) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z + 14), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, (z + 14), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
					TheDeepVoidMod.queueServerWork(8, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z + 12), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, (z + 12), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
					});
					TheDeepVoidMod.queueServerWork(18, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z + 10), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, (z + 10), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
					});
					TheDeepVoidMod.queueServerWork(28, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z + 8), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, (z + 8), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
					});
					TheDeepVoidMod.queueServerWork(38, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z + 6), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, (z + 6), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
					});
					TheDeepVoidMod.queueServerWork(48, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z + 4), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, (z + 4), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.bone_block.step")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
					});
				} else if (Math.random() < 0.1) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x + Mth.nextInt(RandomSource.create(), -32, 32), y, z + Mth.nextInt(RandomSource.create(), -32, 32)),
									BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:false_hydra_song_far")), SoundSource.HOSTILE, 2, 1);
						} else {
							_level.playLocalSound((x + Mth.nextInt(RandomSource.create(), -32, 32)), y, (z + Mth.nextInt(RandomSource.create(), -32, 32)), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:false_hydra_song_far")),
									SoundSource.HOSTILE, 2, 1, false);
						}
					}
				}
			} else {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.mistedRemnantAmbience = TheDeepVoidModVariables.of(entity).mistedRemnantAmbience + 1;
					_vars.syncPlayerVariables(entity);
				}
			}
			if (!(entity instanceof LivingEntity _livEnt24 && _livEnt24.hasEffect(TheDeepVoidModMobEffects.BRAIN_FOG.holder())) && !(new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()).getGameMode() == GameType.CREATIVE;
					}
					return false;
				}
			}.checkGamemode(entity)) && !(new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()).getGameMode() == GameType.SPECTATOR;
					}
					return false;
				}
			}.checkGamemode(entity)) && !(!PortEntities.find(world, FalseHydraEntity.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64)).isEmpty()) && TheDeepVoidModVariables.MapVariables.get(world).hydraKilled == false
					&& DeepVoidConfigConfiguration.DOBRAINFOG.get() == true) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.BRAIN_FOG.holder(), (int) (double) DeepVoidConfigConfiguration.BRAINFOGDURATION.get(), 0, false, false));
			}
		} else if (world.getBiome(BlockPos.containing(x, y, z)).is(BIOME_DRIFTING_MONOLITHS)) {
			if (TheDeepVoidModVariables.of(entity).monolithAmbience >= 700) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.monolithAmbience = 0;
					_vars.syncPlayerVariables(entity);
				}
				if (Math.random() < 0.5) {
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
							_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound the_deep_void:stone_dragging ambient @s ~ ~ ~ 2 1 0.5");
						}
					}
				} else if (Math.random() < 0.5) {
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
							_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound the_deep_void:stone_impact ambient @s ~ ~ ~ 2 1 0.5");
						}
					}
				}
			} else {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.monolithAmbience = TheDeepVoidModVariables.of(entity).monolithAmbience + 1;
					_vars.syncPlayerVariables(entity);
				}
			}
		} else if (world.getBiome(BlockPos.containing(x, y, z)).is(BIOME_CRAWLER_NEST)) {
			if (TheDeepVoidModVariables.of(entity).nestAmbience >= 300) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.nestAmbience = 0;
					_vars.syncPlayerVariables(entity);
				}
				if (Math.random() < 0.5) {
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
							_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound the_deep_void:crawler_nest_additions ambient @s ~ ~ ~ 2 1 0.5");
						}
					}
				} else if (Math.random() < 0.5) {
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
							_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound entity.silverfish.ambient ambient @s ~ ~ ~ 2 1 0.5");
						}
					}
				}
			} else {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.nestAmbience = TheDeepVoidModVariables.of(entity).nestAmbience + 1;
					_vars.syncPlayerVariables(entity);
				}
			}
		} else if (world.getBiome(BlockPos.containing(x, y, z)).is(BIOME_GAOL_OF_HERETICS) || world.getBiome(BlockPos.containing(x, y, z)).is(BIOME_THE_GAOL)) {
			if (TheDeepVoidModVariables.of(entity).gaol_ambience >= 500) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.gaol_ambience = 0;
					_vars.syncPlayerVariables(entity);
				}
				{
					Entity _ent = entity;
					if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
						_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER,
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound the_deep_void:swinging_cage ambient @s ~ ~ ~ 4 1 0.5");
					}
				}
			} else {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.gaol_ambience = TheDeepVoidModVariables.of(entity).gaol_ambience + 1;
					_vars.syncPlayerVariables(entity);
				}
			}
		}
	}
}
