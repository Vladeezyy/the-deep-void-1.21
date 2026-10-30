package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortUtil;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEnchantments;

public class BlackHoleCanUseRangedItemProcedure {
	public static boolean execute(ItemStack itemstack) {
		if (PortUtil.enchantmentLevel(itemstack, TheDeepVoidModEnchantments.DIMENSIONAL_BURST) != 0) {
			if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ammo", 0) >= (double) DeepVoidConfigConfiguration.BLACKHOLESTORELIMIT.get()) {
				return false;
			}
		}
		return true;
	}
}
