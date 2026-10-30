package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class TheHarvestRightclickedProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity, ItemStack itemstack) {
		if (entity == null || sourceentity == null)
			return;
		double repeat = 0;
		double x = 0;
		double y = 0;
		double z = 0;
		double randomX = 0;
		double randomY = 0;
		double randomZ = 0;
		double randomXX = 0;
		double randomYY = 0;
		double randomZZ = 0;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.HEXED.holder()) && itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("slash") < 5) {
			{
				final String _tagName = "slash";
				final double _tagValue = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("slash") + 1);
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
			}
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:soul_seeker_slash")), SoundSource.PLAYERS, (float) 1.8, (float) 0.9);
			} else {
				_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:soul_seeker_slash")), SoundSource.PLAYERS, (float) 1.8, (float) 0.9, false);
			}
		}
		{
			final Vec3 _center = new Vec3((entity.getX()), (entity.getY()), (entity.getZ()));
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator instanceof LivingEntity && !(entityiterator == sourceentity)) {
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy"))), sourceentity),
							(float) (double) DeepVoidConfigConfiguration.THEHARVEST.get());
					if ((entityiterator instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.HEXED.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.HEXED.holder()).getAmplifier() : 0) < 6) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.HEXED.holder(),
									(int) ((entityiterator instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.HEXED.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.HEXED.holder()).getDuration() : 0) + 20),
									(int) ((entityiterator instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.HEXED.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.HEXED.holder()).getAmplifier() : 0) + 2)));
					} else {
						if (entityiterator instanceof LivingEntity _entity)
							_entity.removeEffect(TheDeepVoidModMobEffects.HEXED.holder());
						entityiterator.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy"))), sourceentity),
								(float) Math.ceil(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("deep_void:hexedBuildUp") / 14));
					}
				}
			}
		}
		repeat = 0;
		randomXX = Mth.nextInt(RandomSource.create(), -6, 6);
		randomYY = Mth.nextInt(RandomSource.create(), 2, 4);
		randomZZ = Mth.nextInt(RandomSource.create(), -6, 6);
		for (int index0 = 0; index0 < 12; index0++) {
			repeat = repeat + 1;
			x = entity.getX() - (entity.getX() - (entity.getX() + randomXX)) * (repeat / 8);
			y = (entity.getY() + 0.8) - ((entity.getY() + 0.8) - (entity.getY() + randomYY)) * (repeat / 8);
			z = entity.getZ() - (entity.getZ() - (entity.getZ() + randomZZ)) * (repeat / 8);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 6, 0, 0, 0, 0);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.SCULK_SOUL, x, y, z, 6, 0.1, 0.1, 0.1, 0);
		}
	}
}
