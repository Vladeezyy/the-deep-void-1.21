package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.entity.MaskedHunterEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class BoneSpikesEntityCollidesInTheBlockProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity && !(entity instanceof MaskedHunterEntity)) {
			entity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.GENERIC)),
					(float) (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, Identifier.parse("the_deep_void:trap_vulnerable"))) ? 10 : 2));
			if (Math.random() < 0.02) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROT.holder(), 80, (int) (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, Identifier.parse("the_deep_void:trap_vulnerable"))) ? 1 : 0)));
			}
		}
	}
}
