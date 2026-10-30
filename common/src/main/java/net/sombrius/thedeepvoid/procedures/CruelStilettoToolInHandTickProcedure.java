package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class CruelStilettoToolInHandTickProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("voidMatterStilettoHit", 0) <= 0 && !(entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(TheDeepVoidModMobEffects.VANISH.holder()))) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.VANISH.holder(), (int) Double.POSITIVE_INFINITY, 0, false, false));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, (int) Double.POSITIVE_INFINITY, 0, false, false));
		} else if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("voidMatterStilettoHit", 0) > 0) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("voidMatterStilettoHit", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("voidMatterStilettoHit", 0) - 1));
		}
	}
}
