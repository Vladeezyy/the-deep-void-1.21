package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class BloodstoneArmorHelmetTickEventProcedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (itemstack.getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_HELMET.get()) {
			if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("bloodstoneEquipHelmet", false) == false) {
				entity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.GENERIC)), (float) 0.1);
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("bloodstoneEquipHelmet", true);
			}
		} else if (itemstack.getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_CHESTPLATE.get()) {
			if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("bloodstoneEquipChest", false) == false) {
				entity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.GENERIC)), (float) 0.1);
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("bloodstoneEquipChest", true);
			}
		} else if (itemstack.getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_LEGGINGS.get()) {
			if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("bloodstoneEquipLeggings", false) == false) {
				entity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.GENERIC)), (float) 0.1);
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("bloodstoneEquipLeggings", true);
			}
		} else if (itemstack.getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_BOOTS.get()) {
			if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("bloodstoneEquipBoots", false) == false) {
				entity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.GENERIC)), (float) 0.1);
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("bloodstoneEquipBoots", true);
			}
		}
	}
}
