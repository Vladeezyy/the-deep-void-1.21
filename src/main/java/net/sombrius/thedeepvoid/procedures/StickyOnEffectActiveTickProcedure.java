package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class StickyOnEffectActiveTickProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!entity.isPassenger()) {
			entity.setDeltaMovement(
					new Vec3((entity.getDeltaMovement().x() * (0.1 - (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.STICKY) ? _livEnt.getEffect(TheDeepVoidModMobEffects.STICKY).getAmplifier() : 0) / 60)),
							(entity.getDeltaMovement().y() * (0.1 - (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.STICKY) ? _livEnt.getEffect(TheDeepVoidModMobEffects.STICKY).getAmplifier() : 0) / 60)),
							(entity.getDeltaMovement().z() * (0.1 - (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.STICKY) ? _livEnt.getEffect(TheDeepVoidModMobEffects.STICKY).getAmplifier() : 0) / 60))));
		}
	}
}
