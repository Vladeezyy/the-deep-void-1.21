package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.entity.SwarmerEntity;
import net.sombrius.thedeepvoid.entity.StalkerEntity;
import net.sombrius.thedeepvoid.entity.SmallFleshCubeEntity;
import net.sombrius.thedeepvoid.entity.RottenCorpseEntity;
import net.sombrius.thedeepvoid.entity.RoamerEntity;
import net.sombrius.thedeepvoid.entity.MotherBoneCrawlerEntity;
import net.sombrius.thedeepvoid.entity.LickerEntity;
import net.sombrius.thedeepvoid.entity.GravekeeperEntity;
import net.sombrius.thedeepvoid.entity.GooSpitterEntity;
import net.sombrius.thedeepvoid.entity.FleshCubeEntity;
import net.sombrius.thedeepvoid.entity.DeathMawEntity;
import net.sombrius.thedeepvoid.entity.CentigazeEntity;
import net.sombrius.thedeepvoid.entity.BoneCrawlerEntity;
import net.sombrius.thedeepvoid.entity.AlphaBoneCrawlerEntity;
import net.sombrius.thedeepvoid.entity.AbductorEntity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Comparator;

@EventBusSubscriber
public class PlayerAttackedByStalkerProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		execute(null, world, x, y, z, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		//When the player is attacked by some entities
		if (sourceentity instanceof StalkerEntity) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.DECOMPOSITION, 160, 0));
		} else if (sourceentity instanceof BoneCrawlerEntity && Math.random() < 0.4) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROT, 180, 0));
		} else if (sourceentity instanceof AlphaBoneCrawlerEntity && Math.random() < 0.4) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROT, 160, 1));
		} else if (sourceentity instanceof GooSpitterEntity && Math.random() < 0.5 && !(entity instanceof LivingEntity _livEnt7 && _livEnt7.hasEffect(TheDeepVoidModMobEffects.STICKY))) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.STICKY, 100, 0));
		} else if (sourceentity instanceof MotherBoneCrawlerEntity && Math.random() < 0.4) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROT, 140, 2));
		} else if (sourceentity instanceof GravekeeperEntity && Math.random() < 0.6) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROT, 100, 0));
		} else if (sourceentity instanceof RottenCorpseEntity && Math.random() < 0.4) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROT, 100, 0));
		} else if (sourceentity instanceof AbductorEntity && Math.random() < 0.2) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROT, 60, 0));
		} else if (sourceentity instanceof CentigazeEntity && Math.random() < 0.5) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROT, 60, 0));
		} else if (sourceentity instanceof LickerEntity && (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > 5) {
			if (sourceentity instanceof LivingEntity _entity)
				_entity.setHealth((float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + 4));
		} else if (sourceentity instanceof FleshCubeEntity || sourceentity instanceof SmallFleshCubeEntity) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.slime.attack")), SoundSource.HOSTILE, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.slime.attack")), SoundSource.HOSTILE, 1, 1, false);
				}
			}
			if (sourceentity instanceof LivingEntity _entity)
				_entity.setHealth((float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + 1));
		} else if (sourceentity instanceof DeathMawEntity) {
			if ((entity.getVehicle()) == sourceentity) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) 0.6);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) 0.6, false);
					}
				}
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROT, 25, 0, false, false));
			}
		} else if (sourceentity instanceof SwarmerEntity) {
			sourceentity.setDeltaMovement(new Vec3(0, 0.02, 0));
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(30 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof SwarmerEntity) {
						if (entityiterator instanceof Mob _entity && entity instanceof LivingEntity _ent)
							_entity.setTarget(_ent);
					}
				}
			}
		} else if (sourceentity instanceof RoamerEntity && !(entity instanceof LivingEntity _livEnt39 && _livEnt39.hasEffect(TheDeepVoidModMobEffects.ROT))) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROT, 40, 1));
		}
	}
}
