package net.sombrius.thedeepvoid.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class AnxietySubtleProcedureProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return false;
		if ((entity.level().dimension()) == ResourceKey.create(Registries.DIMENSION, Identifier.parse("the_deep_void:deep_void")) && !(entity instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(TheDeepVoidModMobEffects.VOID_BLESSING.holder()))
				&& !(entity instanceof LivingEntity _livEnt4 && _livEnt4.hasEffect(TheDeepVoidModMobEffects.HALLUCINATE.holder())) && !(entity instanceof LivingEntity _livEnt5 && _livEnt5.hasEffect(TheDeepVoidModMobEffects.WEAVER_CURSE.holder())) && !(new Object() {
					public boolean checkGamemode(Entity _ent) {
						if (_ent instanceof ServerPlayer _serverPlayer) {
							return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
						} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
							return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()) != null
									&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()).getGameMode() == GameType.SPECTATOR;
						}
						return false;
					}
				}.checkGamemode(entity)) && !(new Object() {
					public boolean checkGamemode(Entity _ent) {
						if (_ent instanceof ServerPlayer _serverPlayer) {
							return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
						} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
							return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()) != null
									&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()).getGameMode() == GameType.CREATIVE;
						}
						return false;
					}
				}.checkGamemode(entity)) && !world.getBiome(BlockPos.containing(x, y, z)).is(TagKey.create(Registries.BIOME, Identifier.parse("the_deep_void:stalker_safe")))) {
			if ((world.getBlockState(BlockPos.containing(x, y - 0.5, z))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:light_cannot_reach")))) {
				if (world.getMaxLocalRawBrightness(BlockPos.containing(x, y + 1, z)) <= 2 && world.getMaxLocalRawBrightness(BlockPos.containing(x, y + 1, z)) > 1) {
					return true;
				}
			} else if (world.getMaxLocalRawBrightness(BlockPos.containing(x, y, z)) <= 2 && world.getMaxLocalRawBrightness(BlockPos.containing(x, y, z)) > 1) {
				return true;
			}
		}
		return false;
	}
}
