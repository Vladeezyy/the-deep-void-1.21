package net.sombrius.thedeepvoid.procedures;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class ParanoiaOnEffectActiveTickProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (TheDeepVoidModVariables.of(entity).seek_light == false && DeepVoidConfigConfiguration.SEEKLIGHTWARNING.get() == true) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.seek_light = true;
				_vars.syncPlayerVariables(entity);
			}
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
					_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent),
							("/title @s title {\"bold\":true,\"color\":\"red\",\"text\":\"" + "" + Component.translatable("text.the_deep_void.seek_light").getString() + "\"}"));
				}
			}
		}
	}
}
