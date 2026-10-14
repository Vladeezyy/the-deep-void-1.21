package net.sombrius.thedeepvoid.procedures;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class ArmorToggleOnKeyPressedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).armorToggle == false) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.armorToggle = true;
				_vars.syncPlayerVariables(entity);
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("key.the_deep_void.armor_toggle.msg_inactive").getString())), true);
		} else if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).armorToggle == true) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.armorToggle = false;
				_vars.syncPlayerVariables(entity);
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("key.the_deep_void.armor_toggle.msg_active").getString())), true);
		}
	}
}
