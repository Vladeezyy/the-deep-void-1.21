package net.sombrius.thedeepvoid.procedures;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.entity.FleshWormEntity;
import net.sombrius.thedeepvoid.entity.MisanthropicHivemindEntity;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class PlayMusicPitchBlackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		{
			TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
			_vars.MusicReplace = false;
			_vars.syncPlayerVariables(entity);
		}
		if (TheDeepVoidModVariables.of(entity).MusicTick == 1) {
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
					_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound the_deep_void:pitch_black record @s");
				}
			}
		} else if (TheDeepVoidModVariables.of(entity).MusicTick == 1700) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.MusicTick = 0;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (!(!world.getEntitiesOfClass(MisanthropicHivemindEntity.class, AABB.ofSize(new Vec3(x, y, z), 200, 200, 200), e -> true).isEmpty())
				&& !(!world.getEntitiesOfClass(FleshWormEntity.class, AABB.ofSize(new Vec3(x, y, z), 200, 200, 200), e -> true).isEmpty())) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.MusicWait = TheDeepVoidModVariables.of(entity).MusicWait + 1;
				_vars.syncPlayerVariables(entity);
			}
			if (TheDeepVoidModVariables.of(entity).MusicWait > 200) {
				{
					Entity _ent = entity;
					if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
						_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER,
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "stopsound @s record the_deep_void:pitch_black");
					}
				}
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.MusicWait = 0;
					_vars.syncPlayerVariables(entity);
				}
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.MusicTick = 0;
					_vars.syncPlayerVariables(entity);
				}
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.MusicPlay = "";
					_vars.syncPlayerVariables(entity);
				}
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.MusicLock = false;
					_vars.syncPlayerVariables(entity);
				}
			}
		} else {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.MusicWait = 0;
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}
