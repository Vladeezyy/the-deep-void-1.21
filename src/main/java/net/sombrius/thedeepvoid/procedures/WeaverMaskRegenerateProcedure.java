package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class WeaverMaskRegenerateProcedure {
	@SubscribeEvent
	public static void onEntityHealed(LivingHealEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.WEAVER_MASK_HELMET.get()
				|| (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HARVESTER_MASK_HELMET.get()) {
			if (!(entity instanceof LivingEntity _livEnt4 && _livEnt4.hasEffect(TheDeepVoidModMobEffects.GOLDEN_BLOOD)) && !(entity instanceof LivingEntity _livEnt5 && _livEnt5.hasEffect(MobEffects.HEAL))
					&& !(entity instanceof LivingEntity _livEnt6 && _livEnt6.hasEffect(MobEffects.REGENERATION))) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
			}
		} else if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_HELMET.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_CHESTPLATE.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_LEGGINGS.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_BOOTS.get()) {
			if (!(entity instanceof LivingEntity _livEnt15 && _livEnt15.hasEffect(TheDeepVoidModMobEffects.GOLDEN_BLOOD)) && !(entity instanceof LivingEntity _livEnt16 && _livEnt16.hasEffect(MobEffects.HEAL))
					&& !(entity instanceof LivingEntity _livEnt17 && _livEnt17.hasEffect(MobEffects.REGENERATION))) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
			}
		}
	}
}
