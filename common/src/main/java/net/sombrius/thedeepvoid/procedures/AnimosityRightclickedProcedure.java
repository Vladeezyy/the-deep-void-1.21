package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class AnimosityRightclickedProcedure {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		ItemStack data = ItemStack.EMPTY;
		if (entity instanceof Player _player)
			_player.getCooldowns().addCooldown(new ItemStack(TheDeepVoidModItems.ANIMOSITY.get()), 40);
		if (entity instanceof Player _player)
			_player.getCooldowns().addCooldown(new ItemStack(TheDeepVoidModItems.ANIMOSITY_HIDDEN.get()), 40);
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()) {
			data = (itemstack.copy());
			if (entity instanceof LivingEntity _entity) {
				ItemStack _setstack = new ItemStack(TheDeepVoidModItems.ANIMOSITY_HIDDEN.get()).copy();
				_setstack.setCount(1);
				_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
				if (_entity instanceof Player _player)
					_player.getInventory().setChanged();
			}
			(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).applyComponents(data.getComponents());
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()) {
			data = (itemstack.copy());
			if (entity instanceof LivingEntity _entity) {
				ItemStack _setstack = new ItemStack(TheDeepVoidModItems.ANIMOSITY_HIDDEN.get()).copy();
				_setstack.setCount(1);
				_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
				if (_entity instanceof Player _player)
					_player.getInventory().setChanged();
			}
			(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).applyComponents(data.getComponents());
		}
	}
}
