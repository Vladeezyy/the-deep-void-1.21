package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.component.CustomData;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.LivingIncomingDamageEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class RotUpgradedWeaponAttackProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(Entity entity, Entity sourceentity) {
		execute(null, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.SWORDS)
				|| (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.PICKAXES)
				|| (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.AXES)
				|| (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.SHOVELS)
				|| (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.HOES)
				|| (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof TridentItem) {
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("modification", false) == true) {
				if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("rotLevel", 0) > 0
						&& (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("rotDuration", 0) > 0) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROT.holder(),
								(int) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("rotDuration", 0)),
								(int) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("rotLevel", 0) - 1)));
				}
			}
		}
	}
}
