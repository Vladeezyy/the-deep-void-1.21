package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** The part of NeoForge's helper the mod uses */
public final class ItemHandlerHelper {
	private ItemHandlerHelper() {
	}

	/** Into the player's inventory, the rest dropped at their feet (server) */
	public static void giveItemToPlayer(Player player, ItemStack stack) {
		if (stack.isEmpty() || player.level().isClientSide())
			return;
		boolean added = player.getInventory().add(stack);
		if (added)
			player.level().playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F,
					((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
		if (!stack.isEmpty())
			player.drop(stack, false, net.minecraft.util.Prediction.SERVER_ONLY);
	}

	public static void giveItemToPlayer(Player player, ItemStack stack, int preferredSlot) {
		giveItemToPlayer(player, stack);
	}
}
