package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.FalseHydraEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class FalseHydraApplyDebuffProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double rnd = 0;
		if (entity instanceof FalseHydraEntity) {
			((FalseHydraEntity) entity).setAnimation("empty");
		}
		if (entity instanceof FalseHydraEntity) {
			((FalseHydraEntity) entity).setAnimation("animation.falseHydra_scream");
		}
		if (entity instanceof FalseHydraEntity _datEntSetI)
			_datEntSetI.getEntityData().set(FalseHydraEntity.DATA_rndEffect, Mth.nextInt(RandomSource.create(), 1, 4));
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:false_hydra_scream")), SoundSource.HOSTILE, 4, (float) 0.9);
			} else {
				_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:false_hydra_scream")), SoundSource.HOSTILE, 4, (float) 0.9, false);
			}
		}
		TheDeepVoidMod.queueServerWork(10, () -> {
			{
				final Vec3 _center = new Vec3((entity.getX()), (entity.getY()), (entity.getZ()));
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(64 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof Player) {
						if (!(entityiterator instanceof LivingEntity _livEnt12 && _livEnt12.hasEffect(TheDeepVoidModMobEffects.ADAPTIVE)) && !(entityiterator instanceof LivingEntity _livEnt13 && _livEnt13.hasEffect(TheDeepVoidModMobEffects.TERROR))
								&& !(entityiterator instanceof LivingEntity _livEnt14 && _livEnt14.hasEffect(TheDeepVoidModMobEffects.GLUTTONY))
								&& !(entityiterator instanceof LivingEntity _livEnt15 && _livEnt15.hasEffect(TheDeepVoidModMobEffects.DOMINATE))
								|| (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 2.1) {
							if ((entity instanceof FalseHydraEntity _datEntI ? _datEntI.getEntityData().get(FalseHydraEntity.DATA_rndEffect) : 0) == 1) {
								if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ADAPTIVE, (int) (double) DeepVoidConfigConfiguration.HYDRADEBUFFDURATION.get(), 0, true, true));
							} else if ((entity instanceof FalseHydraEntity _datEntI ? _datEntI.getEntityData().get(FalseHydraEntity.DATA_rndEffect) : 0) == 2) {
								if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.DOMINATE, (int) (double) DeepVoidConfigConfiguration.HYDRADEBUFFDURATION.get(), 0, true, true));
							} else if ((entity instanceof FalseHydraEntity _datEntI ? _datEntI.getEntityData().get(FalseHydraEntity.DATA_rndEffect) : 0) == 3) {
								if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.GLUTTONY, (int) (double) DeepVoidConfigConfiguration.HYDRADEBUFFDURATION.get(), 0, true, true));
							} else if ((entity instanceof FalseHydraEntity _datEntI ? _datEntI.getEntityData().get(FalseHydraEntity.DATA_rndEffect) : 0) == 4) {
								if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.TERROR, (int) (double) DeepVoidConfigConfiguration.HYDRADEBUFFDURATION.get(), 0, true, true));
							}
						}
					}
				}
			}
		});
	}
}
