package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class CreepOMeterItemInHandTickProcedure {
	public static void execute(double y, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (y >= 1) {
			if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("deep_void:secondFunction") == true) {
				if (TheDeepVoidModVariables.of(entity).StalkerStalkCount < (double) DeepVoidConfigConfiguration.STALKERSPAWNTIMER.get() / 2) {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal(("" + TheDeepVoidModVariables.of(entity).stalkWatcherCount)), true);
				} else if (TheDeepVoidModVariables.of(entity).StalkerStalkCount >= (double) DeepVoidConfigConfiguration.STALKERSPAWNTIMER.get() / 2) {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal(("" + TheDeepVoidModVariables.of(entity).StalkerStalkCount)), true);
				}
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(("" + TheDeepVoidModVariables.of(entity).StalkerCount)), true);
			}
		}
	}
}
