package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import javax.annotation.Nullable;

import java.util.UUID;

@EventBusSubscriber
public class EntityAttackedRottenHeartProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		execute(null, world, x, y, z, damagesource, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		if (damagesource == null || entity == null || sourceentity == null)
			return;
		double lvl = 0;
		if (sourceentity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.ROTTEN_HEART)) {
			if (event != null && event.isCancelable()) {
				event.setCanceled(true);
			}
			if (sourceentity instanceof LivingEntity _entity)
				_entity.removeEffect(TheDeepVoidModMobEffects.ROTTEN_HEART);
			if (sourceentity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.INVISIBILITY);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 0));
			if (!damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:rotten")))) {
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:rotten"))), sourceentity),
						(float) (amount * 1.5));
			}
		} else if (sourceentity instanceof LivingEntity _livEnt7 && _livEnt7.hasEffect(TheDeepVoidModMobEffects.VANISH)) {
			if (sourceentity instanceof LivingEntity _entity)
				_entity.removeEffect(TheDeepVoidModMobEffects.VANISH);
			if (sourceentity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.INVISIBILITY);
		}
		if (entity instanceof LivingEntity _livEnt10 && _livEnt10.hasEffect(TheDeepVoidModMobEffects.SUNDER)) {
			lvl = entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.SUNDER) ? _livEnt.getEffect(TheDeepVoidModMobEffects.SUNDER).getAmplifier() : 0;
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(TheDeepVoidModMobEffects.SUNDER);
			if (world instanceof Level _level) {
				if (_level.isClientSide()) {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:soul_unleash")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
			for (int index0 = 0; index0 < (int) lvl; index0++) {
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_sickness"))), sourceentity),
						(float) (0.2 + amount * 0.01));
			}
			if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).getModifier(UUID.fromString("7b240ee9-8869-4278-818f-375fab73153e")) != null) {
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).removeModifier(UUID.fromString("7b240ee9-8869-4278-818f-375fab73153e"));
				if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).hasModifier((new AttributeModifier(UUID.fromString("7b240ee9-8869-4278-818f-375fab73153e"), "sunderHealth",
						(entity.getPersistentData().getDouble("sunderMaxHealth") + Math.floor(lvl * 0.1) * (-1)), AttributeModifier.Operation.ADD_VALUE)))))
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).addTransientModifier((new AttributeModifier(UUID.fromString("7b240ee9-8869-4278-818f-375fab73153e"), "sunderHealth",
							(entity.getPersistentData().getDouble("sunderMaxHealth") + Math.floor(lvl * 0.1) * (-1)), AttributeModifier.Operation.ADD_VALUE)));
				entity.getPersistentData().putDouble("sunderMaxHealth", (entity.getPersistentData().getDouble("sunderMaxHealth") + Math.floor(lvl * 0.1) * (-1)));
			} else {
				if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).hasModifier((new AttributeModifier(UUID.fromString("7b240ee9-8869-4278-818f-375fab73153e"), "sunderHealth",
						(entity.getPersistentData().getDouble("sunderMaxHealth") + Math.floor(lvl * 0.1) * (-1)), AttributeModifier.Operation.ADD_VALUE)))))
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).addTransientModifier((new AttributeModifier(UUID.fromString("7b240ee9-8869-4278-818f-375fab73153e"), "sunderHealth",
							(entity.getPersistentData().getDouble("sunderMaxHealth") + Math.floor(lvl * 0.1) * (-1)), AttributeModifier.Operation.ADD_VALUE)));
				entity.getPersistentData().putDouble("sunderMaxHealth", (entity.getPersistentData().getDouble("sunderMaxHealth") + Math.floor(lvl * 0.1) * (-1)));
			}
		}
	}
}
