package net.sombrius.thedeepvoid.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class PlayMusicUnderTheAsylumProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			boolean _setval = false;
			TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
			_vars.MusicReplace = _setval;
			_vars.syncPlayerVariables(entity);
		}
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicTick == 1) {
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.getServer() != null) {
					_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound the_deep_void:under_the_asylum music @s");
				}
			}
		} else if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicTick == 4080) {
			{
				double _setval = 0;
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.MusicTick = _setval;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (!((entity.level().dimension()) == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_deep_void:the_pit")))) {
			{
				double _setval = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicWait + 1;
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.MusicWait = _setval;
				_vars.syncPlayerVariables(entity);
			}
			if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicWait > 200) {
				{
					Entity _ent = entity;
					if (!_ent.level().isClientSide() && _ent.getServer() != null) {
						_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "stopsound @s music the_deep_void:under_the_asylum");
					}
				}
				{
					double _setval = 0;
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.MusicWait = _setval;
					_vars.syncPlayerVariables(entity);
				}
				{
					double _setval = 0;
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.MusicTick = _setval;
					_vars.syncPlayerVariables(entity);
				}
				{
					String _setval = "";
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.MusicPlay = _setval;
					_vars.syncPlayerVariables(entity);
				}
				{
					boolean _setval = false;
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.MusicLock = _setval;
					_vars.syncPlayerVariables(entity);
				}
			}
		} else {
			{
				double _setval = 0;
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.MusicWait = _setval;
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}
