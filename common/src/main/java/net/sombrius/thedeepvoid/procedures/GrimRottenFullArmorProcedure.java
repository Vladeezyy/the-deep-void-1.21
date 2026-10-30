package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class GrimRottenFullArmorProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_HELMET.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_CHESTPLATE.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_LEGGINGS.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_BOOTS.get()) {
			if (world.getMaxLocalRawBrightness(BlockPos.containing(x, y, z)) < 3 || entity instanceof LivingEntity _livEnt9 && _livEnt9.hasEffect(TheDeepVoidModMobEffects.ROTTEN_HEART.holder())) {
				if (TheDeepVoidModVariables.of(entity).darkness_absorption < 200) {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
						_vars.darkness_absorption = TheDeepVoidModVariables.of(entity).darkness_absorption + 1;
						_vars.syncPlayerVariables(entity);
					}
				}
				if (TheDeepVoidModVariables.of(entity).darkness_absorption >= 100) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 5, 0));
				} else if (TheDeepVoidModVariables.of(entity).darkness_absorption >= 200) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 5, 1));
				}
			} else {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.darkness_absorption = 0;
					_vars.syncPlayerVariables(entity);
				}
			}
		}
	}
}
