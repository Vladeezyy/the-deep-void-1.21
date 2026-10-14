package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortUtil;

import java.util.Comparator;
import java.util.List;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.ApostleBossEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class ApostlePierceHeartProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof ApostleBossEntity _datEntSetL)
			_datEntSetL.getEntityData().set(ApostleBossEntity.DATA_doingAttack, true);
		if (entity instanceof ApostleBossEntity) {
			((ApostleBossEntity) entity).setAnimation("empty");
		}
		if (entity instanceof ApostleBossEntity) {
			((ApostleBossEntity) entity).setAnimation("animation.apostle_pierce");
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:apostle_laugh")), SoundSource.HOSTILE, 4, (float) 0.8);
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:apostle_laugh")), SoundSource.HOSTILE, 4, (float) 0.8, false);
			}
		}
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 99, false, false));
		TheDeepVoidMod.queueServerWork(18, () -> {
			if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
				entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getX()), ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getY() + 1.6),
						((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getZ())));
			}
			entity.setDeltaMovement(new Vec3((Math.sin(Math.toRadians(entity.getYRot() + 180))
					* ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / (double) DeepVoidConfigConfiguration.APOSTLESECONDPHASE.get() ? 1.4 : 1.15)), 0.1,
					(Math.cos(Math.toRadians(entity.getYRot()))
							* ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / (double) DeepVoidConfigConfiguration.APOSTLESECONDPHASE.get()
									? 1.4
									: 1.15))));
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.witch.throw")), SoundSource.HOSTILE, 1, (float) 0.6);
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.witch.throw")), SoundSource.HOSTILE, 1, (float) 0.6, false);
				}
			}
		});
		TheDeepVoidMod.queueServerWork(20, () -> {
			{
				final Vec3 _center = new Vec3((entity.getX()), (entity.getY()), (entity.getZ()));
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof Player) {
						if ((entityiterator instanceof LivingEntity _entUseItem32 ? _entUseItem32.getUseItem() : ItemStack.EMPTY).getItem() instanceof ShieldItem) {
							if (entityiterator instanceof Player _player)
								_player.getCooldowns().addCooldown((entityiterator instanceof LivingEntity _entUseItem34 ? _entUseItem34.getUseItem() : ItemStack.EMPTY).getItem(), 100);
							{
								ItemStack _ist = (entityiterator instanceof LivingEntity _entUseItem36 ? _entUseItem36.getUseItem() : ItemStack.EMPTY);
								PortUtil.hurtAndBreak(_ist, 15, null);
							}
							if (entityiterator instanceof LivingEntity _entity)
								_entity.stopUsingItem();
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.shield.break")), SoundSource.PLAYERS,
											(float) 0.4, 1);
								} else {
									_level.playLocalSound((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.shield.break")), SoundSource.PLAYERS, (float) 0.4, 1,
											false);
								}
							}
						} else {
							entityiterator.getPersistentData().putBoolean("piercedByApostle", true);
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.BROKEN_ARMOR, 400, 0));
							if (entity instanceof ApostleBossEntity _datEntSetL)
								_datEntSetL.getEntityData().set(ApostleBossEntity.DATA_piercedPlayer, true);
						}
					}
				}
			}
		});
		TheDeepVoidMod.queueServerWork(22, () -> {
			if ((entity instanceof ApostleBossEntity _datEntL48 && _datEntL48.getEntityData().get(ApostleBossEntity.DATA_piercedPlayer)) == false
					&& (entity instanceof ApostleBossEntity _datEntL49 && _datEntL49.getEntityData().get(ApostleBossEntity.DATA_doingAttack)) == true) {
				if (entity instanceof ApostleBossEntity) {
					((ApostleBossEntity) entity).setAnimation("empty");
				}
				if (entity instanceof ApostleBossEntity) {
					((ApostleBossEntity) entity).setAnimation("animation.apostle_missPierce");
				}
				if (entity instanceof ApostleBossEntity _datEntSetL)
					_datEntSetL.getEntityData().set(ApostleBossEntity.DATA_doingAttack, false);
				if (entity instanceof LivingEntity _entity)
					_entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
			}
		});
		TheDeepVoidMod.queueServerWork(55, () -> {
			if ((entity instanceof ApostleBossEntity _datEntL55 && _datEntL55.getEntityData().get(ApostleBossEntity.DATA_piercedPlayer)) == true) {
				{
					final Vec3 _center = new Vec3((entity.getX()), (entity.getY()), (entity.getZ()));
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(16 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator.getPersistentData().getBoolean("piercedByApostle") == true) {
							entityiterator.getPersistentData().putBoolean("piercedByApostle", false);
							entityiterator.setDeltaMovement(new Vec3((Math.sin(Math.toRadians(entity.getYRot() + 180)) * 1.2), 0.4, (Math.cos(Math.toRadians(entity.getYRot())) * 1.2)));
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));
						}
					}
				}
			}
		});
		TheDeepVoidMod.queueServerWork(60, () -> {
			if ((entity instanceof ApostleBossEntity _datEntL68 && _datEntL68.getEntityData().get(ApostleBossEntity.DATA_piercedPlayer)) == true) {
				if (entity instanceof ApostleBossEntity _datEntSetL)
					_datEntSetL.getEntityData().set(ApostleBossEntity.DATA_doingAttack, false);
				if (entity instanceof ApostleBossEntity _datEntSetL)
					_datEntSetL.getEntityData().set(ApostleBossEntity.DATA_piercedPlayer, false);
			}
		});
	}
}
