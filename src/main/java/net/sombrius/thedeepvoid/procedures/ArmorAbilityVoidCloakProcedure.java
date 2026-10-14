package net.sombrius.thedeepvoid.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class ArmorAbilityVoidCloakProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).VoidCloakAbility == 0) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.VoidCloakAbility = 460;
				_vars.syncPlayerVariables(entity);
			}
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.playerX = x;
				_vars.syncPlayerVariables(entity);
			}
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.playerY = y;
				_vars.syncPlayerVariables(entity);
			}
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.playerZ = z;
				_vars.syncPlayerVariables(entity);
			}
			if (new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
					}
					return false;
				}
			}.checkGamemode(entity)) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.InCreative = true;
					_vars.syncPlayerVariables(entity);
				}
			}
			if (new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.ADVENTURE;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.ADVENTURE;
					}
					return false;
				}
			}.checkGamemode(entity)) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.InAdventure = true;
					_vars.syncPlayerVariables(entity);
				}
			}
			if (new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.SURVIVAL;
					}
					return false;
				}
			}.checkGamemode(entity)) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.InSurvival = true;
					_vars.syncPlayerVariables(entity);
				}
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("particle.soul_escape")), SoundSource.PLAYERS, 2, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("particle.soul_escape")), SoundSource.PLAYERS, 2, 1, false);
				}
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.SMOKE, x, y, z, 25, 1, 1, 1, 0.1);
			if (entity instanceof ServerPlayer _player)
				_player.setGameMode(GameType.SPECTATOR);
			TheDeepVoidMod.queueServerWork(140, () -> {
				{
					Entity _ent = entity;
					_ent.teleportTo(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).playerX, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).playerY, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).playerZ);
					if (_ent instanceof ServerPlayer _serverPlayer)
						_serverPlayer.connection.teleport(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).playerX, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).playerY,
								entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).playerZ, _ent.getYRot(), _ent.getXRot());
				}
				if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).InCreative == true) {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
						_vars.InCreative = false;
						_vars.syncPlayerVariables(entity);
					}
					if (entity instanceof ServerPlayer _player)
						_player.setGameMode(GameType.CREATIVE);
				} else if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).InAdventure == true) {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
						_vars.InAdventure = false;
						_vars.syncPlayerVariables(entity);
					}
					if (entity instanceof ServerPlayer _player)
						_player.setGameMode(GameType.ADVENTURE);
				} else if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).InSurvival == true) {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
						_vars.InSurvival = false;
						_vars.syncPlayerVariables(entity);
					}
					if (entity instanceof ServerPlayer _player)
						_player.setGameMode(GameType.SURVIVAL);
				}
			});
		}
	}
}
