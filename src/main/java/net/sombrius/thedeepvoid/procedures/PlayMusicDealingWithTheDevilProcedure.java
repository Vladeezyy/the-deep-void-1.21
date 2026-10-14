package net.sombrius.thedeepvoid.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.entity.HarbingerOfCataclysmEntity;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class PlayMusicDealingWithTheDevilProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
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
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound the_deep_void:dealing_with_the_devil record @s");
				}
			}
		} else if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicTick == 1966) {
			{
				double _setval = 0;
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.MusicTick = _setval;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (!(!world.getEntitiesOfClass(HarbingerOfCataclysmEntity.class, AABB.ofSize(new Vec3(x, y, z), 200, 200, 200), e -> true).isEmpty())) {
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
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "stopsound @s record the_deep_void:dealing_with_the_devil");
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
