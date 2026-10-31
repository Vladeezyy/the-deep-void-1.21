package net.sombrius.thedeepvoid.procedures;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class PlayerCageEntityDiesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if ((entity.level().dimension()) == ResourceKey.create(Registries.DIMENSION, Identifier.parse("the_deep_void:the_pit"))) {
			TheDeepVoidMod.queueServerWork(48, () -> {
				{
					boolean _setval = true;
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(sourceentity);
					_vars.playMusicPit = _setval;
					_vars.syncPlayerVariables(sourceentity);
				}
				{
					double _setval = 0;
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(sourceentity);
					_vars.MusicWait = _setval;
					_vars.syncPlayerVariables(sourceentity);
				}
				{
					double _setval = -1;
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(sourceentity);
					_vars.MusicTick = _setval;
					_vars.syncPlayerVariables(sourceentity);
				}
				{
					String _setval = "";
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(sourceentity);
					_vars.MusicPlay = _setval;
					_vars.syncPlayerVariables(sourceentity);
				}
				{
					boolean _setval = false;
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(sourceentity);
					_vars.MusicLock = _setval;
					_vars.syncPlayerVariables(sourceentity);
				}
				{
					boolean _setval = false;
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(sourceentity);
					_vars.MusicReplace = _setval;
					_vars.syncPlayerVariables(sourceentity);
				}
				if (TheDeepVoidModVariables.of(sourceentity).pitShowText == false) {
					{
						boolean _setval = true;
						TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(sourceentity);
						_vars.pitShowText = _setval;
						_vars.syncPlayerVariables(sourceentity);
					}
					{
						Entity _ent = sourceentity;
						if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
							_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER, _ent.level().getServer(), _ent), "/title @s times 40 50 80");
						}
					}
					{
						Entity _ent = sourceentity;
						if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
							_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER, _ent.level().getServer(), _ent), "/title @s subtitle {\"text\":\"-----------------------------------\",\"color\":\"dark_red\"}");
						}
					}
					{
						Entity _ent = sourceentity;
						if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
							_ent.level().getServer().getCommands().performPrefixedCommand(
									new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER, _ent.level().getServer(), _ent),
									"/title @s title [\"\",{\"text\":\"Gaol\",\"obfuscated\":true,\"color\":\"dark_red\"},{\"text\":\" The Pit \",\"color\":\"dark_red\"},{\"text\":\"Gaol\",\"obfuscated\":true,\"color\":\"dark_red\"}]");
						}
					}
				}
			});
		}
		if (!entity.level().isClientSide())
			entity.discard();
		world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(TheDeepVoidModBlocks.CAGE.get().defaultBlockState()));
	}
}
