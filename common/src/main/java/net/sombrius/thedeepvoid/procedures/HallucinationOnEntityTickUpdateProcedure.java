package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class HallucinationOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 5, 5, 5), e -> true).isEmpty()) {
			if (!entity.level().isClientSide())
				entity.discard();
			if (Math.random() < 0.35) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator instanceof Player) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:hallucination_attack")),
											SoundSource.NEUTRAL, 2, (float) 0.9);
								} else {
									_level.playLocalSound((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:hallucination_attack")), SoundSource.NEUTRAL,
											2, (float) 0.9, false);
								}
							}
							if ((entityiterator instanceof LivingEntity _livEnt7 && _livEnt7.hasEffect(MobEffects.DARKNESS)) == false) {
								if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 120, 0));
							}
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 0));
						}
					}
				}
			}
		}
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.SMOKE, x, (y + 0.5), z, 7, 0.5, 0.5, 0.5, 0.1);
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.DEAD_INSIDE.holder(), 5, 0, false, false));
		if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 100, 100, 100), e -> true).isEmpty() == false) {
			if (!entity.level().isClientSide())
				entity.discard();
		}
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("deep_void:agressive", false) == true) {
			if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 8, 8, 8), e -> true).isEmpty()) {
				if ((entity instanceof LivingEntity _livEnt17 && _livEnt17.hasEffect(TheDeepVoidModMobEffects.LOOKING.holder())) == true) {
					if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("deep_void:running", false) == true) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.SPEED, 5, 2, false, false));
						entity.setSprinting(true);
					}
					if (Math.random() < 0.005) {
						net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("deep_void:running", true);
					}
				} else {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 5, 99, false, false));
				}
			} else {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.SPEED, 5, 2, false, false));
			}
		}
		if (entity.isInWall()) {
			if (!((world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Blocks.AIR)) {
				if (!entity.level().isClientSide())
					entity.discard();
			}
		}
	}
}
