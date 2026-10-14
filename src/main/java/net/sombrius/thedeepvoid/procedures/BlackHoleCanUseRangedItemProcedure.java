package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEnchantments;

public class BlackHoleCanUseRangedItemProcedure {
	public static boolean execute(ItemStack itemstack) {
		if (EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.DIMENSIONAL_BURST.get(), itemstack) != 0) {
			if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("ammo") >= (double) DeepVoidConfigConfiguration.BLACKHOLESTORELIMIT.get()) {
				return false;
			}
		}
		return true;
	}
}
