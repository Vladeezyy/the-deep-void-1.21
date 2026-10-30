package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class ArmorAbilityFlyWingsCloseProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		ItemStack components = ItemStack.EMPTY;
		components = ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).copy());
		{
			Entity _entity = entity;
			if (_entity instanceof Player _player) {
				_player.getInventory().armor.set(2, new ItemStack(TheDeepVoidModItems.FLY_WINGS_CHESTPLATE.get()));
				_player.getInventory().setChanged();
			} else if (_entity instanceof LivingEntity _living) {
				_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(TheDeepVoidModItems.FLY_WINGS_CHESTPLATE.get()));
			}
		}
		(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).applyComponents(components.getComponents());
		if (entity instanceof Player _player)
			_player.getCooldowns().addCooldown((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem(), 140);
		{
			final String _tagName = "flying";
			final boolean _tagValue = false;
			CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
		}
		{
			final String _tagName = "time";
			final double _tagValue = 0;
			CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
		}
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("flyWingsTouchGround", false);
	}
}
