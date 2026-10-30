package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class GoldenBloodOnEffectActiveTickProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.POISON);
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.NAUSEA);
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.WITHER);
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(TheDeepVoidModMobEffects.ROT.holder());
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(TheDeepVoidModMobEffects.DECOMPOSITION.holder());
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(TheDeepVoidModMobEffects.DEAD_INSIDE.holder());
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.HUNGER);
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.MINING_FATIGUE);
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.WEAKNESS);
	}
}
