package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.LivingIncomingDamageEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class AllSeeingSwordKillProcedure {
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
		if (entity instanceof LivingEntity && sourceentity instanceof LivingEntity) {
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ALL_SEEING_SWORD.get()) {
				if (Math.random() < 0.6) {
					if ((sourceentity instanceof Player _plr ? _plr.getAttackStrengthScale(0) : 0) >= 0.848) {
						{
							TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(sourceentity);
							_vars.hasteAllSeeing = sourceentity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.HASTE) ? _livEnt.getEffect(MobEffects.HASTE).getAmplifier() : 0;
							_vars.syncPlayerVariables(sourceentity);
						}
						if (sourceentity instanceof LivingEntity _entity)
							_entity.removeEffect(MobEffects.HASTE);
						if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.HASTE, 40, (int) (TheDeepVoidModVariables.of(sourceentity).hasteAllSeeing + 1), false, false));
					}
				}
			}
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SOUL_CLEAVER.get()) {
				if (Math.random() < 0.8) {
					if ((sourceentity instanceof Player _plr ? _plr.getAttackStrengthScale(0) : 0) >= 0.848) {
						{
							TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(sourceentity);
							_vars.hasteAllSeeing = sourceentity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.HASTE) ? _livEnt.getEffect(MobEffects.HASTE).getAmplifier() : 0;
							_vars.syncPlayerVariables(sourceentity);
						}
						if (sourceentity instanceof LivingEntity _entity)
							_entity.removeEffect(MobEffects.HASTE);
						if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.HASTE, 55, (int) (TheDeepVoidModVariables.of(sourceentity).hasteAllSeeing + 1), false, false));
					}
				}
			}
		}
	}
}
