package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class ArmorAbilityHardFleshArmorProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (TheDeepVoidModVariables.of(entity).flesh_roar <= 0) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.flesh_roar = 340;
				_vars.syncPlayerVariables(entity);
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.ravager.roar")), SoundSource.PLAYERS, 1, 1);
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.ravager.roar")), SoundSource.PLAYERS, 1, 1, false);
				}
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.POOF, (entity.getX()), (entity.getY() + 1), (entity.getZ()), 12, 0.4, 0.4, 0.4, 0.1);
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof LivingEntity && !(entityiterator == entity)) {
						entityiterator.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.PLAYER_ATTACK), entity),
								(float) Math.floor(((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.SLOWNESS) ? _livEnt.getEffect(MobEffects.SLOWNESS).getAmplifier() : 0)
										+ (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.RESISTANCE) ? _livEnt.getEffect(MobEffects.RESISTANCE).getAmplifier() : 0)) / 2));
						entityiterator.setDeltaMovement(new Vec3((Math.sin(Math.toRadians(entityiterator.getYRot() + 180)) * (-1)), 0.3, (Math.cos(Math.toRadians(entityiterator.getYRot())) * (-1))));
					}
				}
			}
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.SLOWNESS);
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.RESISTANCE);
		}
	}
}
