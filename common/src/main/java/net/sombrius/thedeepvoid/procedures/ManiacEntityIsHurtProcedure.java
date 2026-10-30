package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.entity.DeathVultureEntity;
import net.sombrius.thedeepvoid.entity.FoolEaterEntity;

public class ManiacEntityIsHurtProcedure {
	public static void execute(Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (sourceentity instanceof DeathVultureEntity || sourceentity instanceof FoolEaterEntity) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 80, 39, false, false));
		}
	}
}
