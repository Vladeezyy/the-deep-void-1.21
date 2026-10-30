package net.sombrius.thedeepvoid.procedures;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class ArmorToggleOnKeyPressedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (TheDeepVoidModVariables.of(entity).armorToggle == false) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.armorToggle = true;
				_vars.syncPlayerVariables(entity);
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.sendOverlayMessage(Component.literal((Component.translatable("key.the_deep_void.armor_toggle.msg_inactive").getString())));
		} else if (TheDeepVoidModVariables.of(entity).armorToggle == true) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.armorToggle = false;
				_vars.syncPlayerVariables(entity);
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.sendOverlayMessage(Component.literal((Component.translatable("key.the_deep_void.armor_toggle.msg_active").getString())));
		}
	}
}
