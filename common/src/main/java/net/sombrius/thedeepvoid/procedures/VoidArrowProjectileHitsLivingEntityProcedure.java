package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModParticleTypes;

public class VoidArrowProjectileHitsLivingEntityProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		if (entity == null || immediatesourceentity == null || sourceentity == null)
			return;
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOID_BOW.get()
				&& PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), Enchantments.FLAME) != 0) {
			entity.igniteForSeconds(5);
		} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOID_BOW.get()
				&& PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY), Enchantments.FLAME) != 0) {
			entity.igniteForSeconds(5);
		}
		if (!immediatesourceentity.level().isClientSide())
			immediatesourceentity.discard();
		TheDeepVoidMod.queueServerWork(20, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.phantom.bite")), SoundSource.HOSTILE, 1, (float) 0.8);
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.phantom.bite")), SoundSource.HOSTILE, 1, (float) 0.8, false);
				}
			}
		});
		TheDeepVoidMod.queueServerWork(28, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:fleshy_explosion")), SoundSource.HOSTILE, 1, (float) 0.8);
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:fleshy_explosion")), SoundSource.HOSTILE, 1, (float) 0.8, false);
				}
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (TheDeepVoidModParticleTypes.DARK_TEAR.get()), (entity.getX()), (entity.getY() + 1), (entity.getZ()), 5, 1, 0.1, 1, 0.5);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.SQUID_INK, (entity.getX()), (entity.getY() + 1), (entity.getZ()), 5, 1, 0.1, 1, 0.5);
			entity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.parse("the_deep_void:void_sickness"))), sourceentity),
					(float) (double) DeepVoidConfigConfiguration.VOIDBOW.get());
		});
	}
}
