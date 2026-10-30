package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class BloodyRibCageChestplateTickEventProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(MobEffects.POISON)) {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.POISON);
		}
		if (entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(MobEffects.NAUSEA)) {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.NAUSEA);
		}
	}
}
