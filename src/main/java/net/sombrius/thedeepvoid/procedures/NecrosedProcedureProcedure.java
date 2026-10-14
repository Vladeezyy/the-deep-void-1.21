package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortUtil;

import javax.annotation.Nullable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEnchantments;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class NecrosedProcedureProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.ROT)) {
			if (PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY), TheDeepVoidModEnchantments.NECROSED) != 0) {
				if (PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY), TheDeepVoidModEnchantments.NECROSED) == 1) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.WITHER,
								(int) ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.ROT) ? _livEnt.getEffect(TheDeepVoidModMobEffects.ROT).getDuration() : 0) * 1.25), 0));
					if (entity instanceof LivingEntity _entity)
						_entity.removeEffect(TheDeepVoidModMobEffects.ROT);
				}
				if (PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY), TheDeepVoidModEnchantments.NECROSED) == 2) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.WITHER, entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.ROT) ? _livEnt.getEffect(TheDeepVoidModMobEffects.ROT).getDuration() : 0, 0));
					if (entity instanceof LivingEntity _entity)
						_entity.removeEffect(TheDeepVoidModMobEffects.ROT);
				}
				if (PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY), TheDeepVoidModEnchantments.NECROSED) == 3) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.WITHER,
								(int) ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.ROT) ? _livEnt.getEffect(TheDeepVoidModMobEffects.ROT).getDuration() : 0) / 1.4), 0));
					if (entity instanceof LivingEntity _entity)
						_entity.removeEffect(TheDeepVoidModMobEffects.ROT);
				}
			}
		}
	}
}
