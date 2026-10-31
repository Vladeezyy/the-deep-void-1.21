package net.sombrius.thedeepvoid.procedures;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class BookOfAncientKnowledgeRightclickedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (entity instanceof Player _player)
			_player.getCooldowns().addCooldown(itemstack, 200);
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.book.page_turn")), SoundSource.PLAYERS, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.book.page_turn")), SoundSource.PLAYERS, 1, 1, false);
			}
		}
		{
			Entity _ent = entity;
			if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
				_ent.level().getServer().getCommands()
						.performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER, _ent.level().getServer(), _ent),
								"/tellraw @s {\"text\":\"\u00A75\u00A7nThe Deep Void Wiki/GitHub\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://github.com/TroupeMaster/TheDeepVoid/wiki\"}}");
			}
		}
		{
			Entity _ent = entity;
			if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
				_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER, _ent.level().getServer(), _ent),
						"/tellraw @s {\"text\":\"\u00A79\u00A7nThe Deep Void Discord server\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://discord.gg/ddBuyXGSQp\"}}");
			}
		}
	}
}
