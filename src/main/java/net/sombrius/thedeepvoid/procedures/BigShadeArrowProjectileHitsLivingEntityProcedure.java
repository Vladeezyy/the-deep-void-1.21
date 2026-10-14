package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class BigShadeArrowProjectileHitsLivingEntityProcedure {
	public static void execute(Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SHADESTORM.get()
				&& EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAME, (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)) != 0) {
			entity.igniteForSeconds(5);
		} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SHADESTORM.get()
				&& EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAME, (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)) != 0) {
			entity.igniteForSeconds(5);
		}
	}
}
