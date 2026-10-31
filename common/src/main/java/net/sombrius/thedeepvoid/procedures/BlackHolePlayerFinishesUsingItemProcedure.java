package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

import net.minecraft.world.level.GameType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.client.Minecraft;

public class BlackHolePlayerFinishesUsingItemProcedure {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (!(new Object() {
			public boolean checkGamemode(Entity _ent) {
				if (_ent instanceof ServerPlayer _serverPlayer) {
					return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
				} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
					return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()).getGameMode() == GameType.CREATIVE;
				}
				return false;
			}
		}.checkGamemode(entity))) {
			if (entity instanceof Player _player) {
				ItemStack _stktoremove = new ItemStack(Items.ARROW);
				_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), false, 1, _player.inventoryMenu.getCraftSlots());
			}
		}
		{
			final String _tagName = "ammo";
			final double _tagValue = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ammo", 0) + 1);
			CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
		}
		{
			final String _tagName = "loading";
			final boolean _tagValue = false;
			CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
		}
		{
			final String _tagName = "pulling";
			final double _tagValue = 0;
			CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
		}
	}
}
