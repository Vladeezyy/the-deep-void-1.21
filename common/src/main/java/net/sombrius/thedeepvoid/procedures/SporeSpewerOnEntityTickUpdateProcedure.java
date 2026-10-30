package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.entity.SporeSpewerEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.init.TheDeepVoidModParticleTypes;

public class SporeSpewerOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 8, 8, 8), e -> true).isEmpty() && !((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
			if ((entity instanceof SporeSpewerEntity _datEntI ? _datEntI.getEntityData().get(SporeSpewerEntity.DATA_spew) : 0) >= 40) {
				if (entity instanceof SporeSpewerEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SporeSpewerEntity.DATA_spew, 0);
				if (entity instanceof SporeSpewerEntity) {
					((SporeSpewerEntity) entity).setAnimation("animation.sporeSpewer_spew");
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.shulker_bullet.hit")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.shulker_bullet.hit")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
				TheDeepVoidMod.queueServerWork(10, () -> {
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (TheDeepVoidModParticleTypes.PUS_SPORE.get()), x, (y + 0.8), z, 40, 4, 0.5, 4, 0.1);
					{
						final Vec3 _center = new Vec3(x, y, z);
						List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
						for (Entity entityiterator : _entfound) {
							if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, Identifier.parse("the_deep_void:fleshkin")))) {
								if (!(entityiterator instanceof LivingEntity _livEnt9 && _livEnt9.hasEffect(MobEffects.NAUSEA))) {
									if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
										_entity.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 240, 3));
									if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
										_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.BLOSSOM.holder(), 240, 0));
								}
							}
						}
					}
				});
			} else {
				if (entity instanceof SporeSpewerEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SporeSpewerEntity.DATA_spew, (int) ((entity instanceof SporeSpewerEntity _datEntI ? _datEntI.getEntityData().get(SporeSpewerEntity.DATA_spew) : 0) + 1));
			}
		}
	}
}
