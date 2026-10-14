package net.sombrius.thedeepvoid.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.entity.WeaverOfSoulsBossEntity;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class PlayMusicDarkmareProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		{
			TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
			_vars.MusicReplace = false;
			_vars.syncPlayerVariables(entity);
		}
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicTick == 1) {
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.getServer() != null) {
					_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound the_deep_void:darkmare record @s");
				}
			}
		} else if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicTick == 3300) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.MusicTick = 0;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (!(!world.getEntitiesOfClass(WeaverOfSoulsBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 200, 200, 200), e -> true).isEmpty())) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.MusicWait = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicWait + 1;
				_vars.syncPlayerVariables(entity);
			}
			if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicWait > 200) {
				{
					Entity _ent = entity;
					if (!_ent.level().isClientSide() && _ent.getServer() != null) {
						_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "stopsound @s record the_deep_void:darkmare");
					}
				}
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.MusicWait = 0;
					_vars.syncPlayerVariables(entity);
				}
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.MusicTick = 0;
					_vars.syncPlayerVariables(entity);
				}
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.MusicPlay = "";
					_vars.syncPlayerVariables(entity);
				}
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.MusicLock = false;
					_vars.syncPlayerVariables(entity);
				}
			}
		} else {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.MusicWait = 0;
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}
