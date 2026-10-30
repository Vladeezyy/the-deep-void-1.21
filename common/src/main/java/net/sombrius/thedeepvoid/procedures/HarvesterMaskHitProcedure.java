package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.LivingIncomingDamageEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class HarvesterMaskHitProcedure {
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
		double amplifier = 0;
		if ((sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HARVESTER_MASK_HELMET.get()) {
			if (entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(TheDeepVoidModMobEffects.HEXED.holder())
					&& (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) >= (sourceentity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.ABSORPTION) ? _livEnt.getEffect(MobEffects.ABSORPTION).getAmplifier() : 0) * 2
							+ (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)) {
				if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem() || sourceentity.isShiftKeyDown()) {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:hexedBuildUp", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:hexedBuildUp", 0) + Math.ceil((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:hexedBuildUp", 0) * 60) / 100)));
					amplifier = sourceentity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.ABSORPTION) ? _livEnt.getEffect(MobEffects.ABSORPTION).getAmplifier() : 0;
					if (sourceentity instanceof LivingEntity _entity)
						_entity.removeEffect(MobEffects.ABSORPTION);
					if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, (int) (amplifier + Math.floor(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:hexedBuildUp", 0) / 45)), false, false));
					if (entity instanceof LivingEntity _entity)
						_entity.removeEffect(TheDeepVoidModMobEffects.HEXED.holder());
				}
			}
		}
	}
}
