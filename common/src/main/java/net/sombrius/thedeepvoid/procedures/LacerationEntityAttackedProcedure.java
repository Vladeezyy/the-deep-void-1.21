package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.ICancellableEvent;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.LivingIncomingDamageEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class LacerationEntityAttackedProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getSource(), event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		execute(null, world, damagesource, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		if (damagesource == null || entity == null || sourceentity == null)
			return;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.LACERATION.holder()) && sourceentity instanceof LivingEntity
				&& ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.FLESH_GLOVE.get()
						|| (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.EVERHUNGRY_FLESH_GLOVE.get()
						|| (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ADAPTIVE_CLAW.get())) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:laceration", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:laceration", 0)
					+ (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.LACERATION.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.LACERATION.holder()).getAmplifier() : 0) + 1));
			if (!damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.parse("the_deep_void:void_energy")))) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
				entity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.parse("the_deep_void:void_energy"))), sourceentity),
						(float) (amount + net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:laceration", 0) / 2));
				if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:laceration", 0) >= 20
						+ (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.LACERATION.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.LACERATION.holder()).getAmplifier() : 0)) {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:laceration", 0);
				}
			}
		}
	}
}
