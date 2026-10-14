package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortUtil;

import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.BigShadeArrowEntity;
import net.sombrius.thedeepvoid.entity.BloodSpikeEntity;
import net.sombrius.thedeepvoid.entity.BloodthirstyClawsBloodSpitEntity;
import net.sombrius.thedeepvoid.entity.FleshArrowEntity;
import net.sombrius.thedeepvoid.entity.GoldenRotArrowEntity;
import net.sombrius.thedeepvoid.entity.IchorSpitEntity;
import net.sombrius.thedeepvoid.entity.PerilBulletEntity;
import net.sombrius.thedeepvoid.entity.ShadeArrowEntity;
import net.sombrius.thedeepvoid.entity.ShadowArrowEntity;
import net.sombrius.thedeepvoid.entity.SpiteArrowEntity;
import net.sombrius.thedeepvoid.entity.VoidArrowEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class BigShadeArrowHitProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getSource(), event.getEntity(), event.getSource().getDirectEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, DamageSource damagesource, Entity entity, Entity immediatesourceentity, Entity sourceentity, double amount) {
		execute(null, world, damagesource, entity, immediatesourceentity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, DamageSource damagesource, Entity entity, Entity immediatesourceentity, Entity sourceentity, double amount) {
		if (damagesource == null || entity == null || immediatesourceentity == null || sourceentity == null)
			return;
		double vx = 0;
		double vy = 0;
		double vz = 0;
		double distance = 0;
		double z1 = 0;
		double y1 = 0;
		double z2 = 0;
		double x1 = 0;
		double y2 = 0;
		double x2 = 0;
		if (immediatesourceentity instanceof BigShadeArrowEntity) {
			if (entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(TheDeepVoidModMobEffects.COMBO)) {
				if (!((entity instanceof LivingEntity _entUseItem2 ? _entUseItem2.getUseItem() : ItemStack.EMPTY).getItem() instanceof ShieldItem)) {
					if (event instanceof ICancellableEvent _cancellable) {
						_cancellable.setCanceled(true);
					}
					if (entity instanceof LivingEntity _entity)
						_entity.removeEffect(TheDeepVoidModMobEffects.COMBO);
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy"))), sourceentity),
							(float) (amount + (double) DeepVoidConfigConfiguration.SHADESTORMBIGARROW.get() + PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), Enchantments.POWER) + 1));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROT, 120, 0));
					if (sourceentity instanceof LivingEntity _entity)
						_entity.setHealth((float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + 0.25));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(immediatesourceentity.getX(), immediatesourceentity.getY(), immediatesourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.ender_dragon.hurt")),
									SoundSource.PLAYERS, 1, (float) 0.8);
						} else {
							_level.playLocalSound((immediatesourceentity.getX()), (immediatesourceentity.getY()), (immediatesourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.ender_dragon.hurt")),
									SoundSource.PLAYERS, 1, (float) 0.8, false);
						}
					}
					{
						final Vec3 _center = new Vec3((immediatesourceentity.getX()), (immediatesourceentity.getY()), (immediatesourceentity.getZ()));
						List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
						for (Entity entityiterator : _entfound) {
							if (entityiterator instanceof LivingEntity && !(entityiterator == sourceentity) && !(entityiterator == entity)) {
								entityiterator.hurt(
										new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy"))), sourceentity),
										(float) (amount + (double) DeepVoidConfigConfiguration.SHADESTORMBIGARROW.get()));
								if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROT, 120, 0));
								if (sourceentity instanceof LivingEntity _entity)
									_entity.setHealth((float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + 0.1));
							}
						}
					}
					if (!immediatesourceentity.level().isClientSide())
						immediatesourceentity.discard();
				}
			}
		}
		if (immediatesourceentity instanceof FleshArrowEntity && sourceentity instanceof LivingEntity _livEnt32 && _livEnt32.hasEffect(TheDeepVoidModMobEffects.POWERFUL_SHOT)) {
			if (!((entity instanceof LivingEntity _entUseItem33 ? _entUseItem33.getUseItem() : ItemStack.EMPTY).getItem() instanceof ShieldItem)) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
				if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.NERVES_OF_STEEL.get()
						&& PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), Enchantments.POWER) != 0) {
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK), sourceentity),
							(float) ((double) DeepVoidConfigConfiguration.NERVESOFSTEELCRIT.get() + PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), Enchantments.POWER) + 1));
				} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.NERVES_OF_STEEL.get()
						&& PortUtil.enchantmentLevel((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY), Enchantments.POWER) != 0) {
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK), sourceentity),
							(float) ((double) DeepVoidConfigConfiguration.NERVESOFSTEELCRIT.get() + PortUtil.enchantmentLevel((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY), Enchantments.POWER) + 1));
				} else {
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK), sourceentity), (float) (double) DeepVoidConfigConfiguration.NERVESOFSTEELCRIT.get());
				}
				if (sourceentity instanceof LivingEntity _entity)
					_entity.removeEffect(TheDeepVoidModMobEffects.POWERFUL_SHOT);
				if (!immediatesourceentity.level().isClientSide())
					immediatesourceentity.discard();
			}
		}
		if (immediatesourceentity instanceof GoldenRotArrowEntity && !(sourceentity == null) && !damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy")))) {
			if (!((entity instanceof LivingEntity _entUseItem61 ? _entUseItem61.getUseItem() : ItemStack.EMPTY).getItem() instanceof ShieldItem)) {
				x1 = sourceentity.getX();
				y1 = sourceentity.getY();
				z1 = sourceentity.getZ();
				x2 = entity.getX();
				y2 = entity.getY();
				z2 = entity.getZ();
				vx = x2 - x1;
				vy = y2 - y1;
				vz = z2 - z1;
				distance = Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2) + Math.pow(vz, 2));
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
				if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SHARPSHOOTER.get()
						&& PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), Enchantments.POWER) != 0) {
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy"))), sourceentity),
							(float) (amount * (distance * (double) DeepVoidConfigConfiguration.SHARPSHOOTER.get()
									+ PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), Enchantments.POWER) * 0.04)));
				} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SHARPSHOOTER.get()
						&& PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY), Enchantments.POWER) != 0) {
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy"))), sourceentity),
							(float) (amount
									* (distance * (double) DeepVoidConfigConfiguration.SHARPSHOOTER.get() + PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY), Enchantments.POWER) * 0.04)));
				} else {
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy"))), sourceentity),
							(float) (amount * distance * (double) DeepVoidConfigConfiguration.SHARPSHOOTER.get()));
				}
				if (!immediatesourceentity.level().isClientSide())
					immediatesourceentity.discard();
			}
		}
		if (immediatesourceentity instanceof BloodSpikeEntity && !(sourceentity == null) && !damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy")))) {
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOOD_BOW.get()
					&& PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), Enchantments.POWER) != 0) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy"))), sourceentity),
						(float) (amount + PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), Enchantments.POWER) + 1));
			} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOOD_BOW.get()
					&& PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY), Enchantments.POWER) != 0) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy"))), sourceentity),
						(float) (amount + PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY), Enchantments.POWER) + 1));
			}
		}
		if (immediatesourceentity instanceof VoidArrowEntity && !(sourceentity == null) && !damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy")))) {
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOID_BOW.get()
					&& PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), Enchantments.POWER) != 0) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy"))), sourceentity),
						(float) (amount + PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), Enchantments.POWER) + 1));
			} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOID_BOW.get()
					&& PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY), Enchantments.POWER) != 0) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy"))), sourceentity),
						(float) (amount + PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY), Enchantments.POWER) + 1));
			}
		}
		if (entity instanceof LivingEntity && immediatesourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("the_deep_void:nocooldown_bullet")))
				&& !entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("the_deep_void:nocooldown_bullet"))) && !damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:gun")))
				&& !((entity instanceof LivingEntity _entUseItem133 ? _entUseItem133.getUseItem() : ItemStack.EMPTY).getItem() instanceof ShieldItem)) {
			if (event instanceof ICancellableEvent _cancellable) {
				_cancellable.setCanceled(true);
			}
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:gun"))), sourceentity),
					(float) ((sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GUNSLINGER_HELMET.get()
							&& (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GUNSLINGER_CHESTPLATE.get()
							&& (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GUNSLINGER_LEGGINGS.get()
							&& (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GUNSLINGER_BOOTS.get()
							&& !(immediatesourceentity instanceof BloodthirstyClawsBloodSpitEntity) && !(immediatesourceentity instanceof BloodSpikeEntity) ? amount * 1.2 : amount));
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
		}
		if (immediatesourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("the_deep_void:bullet")))
				&& !immediatesourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("the_deep_void:nocooldown_bullet")))
				&& !((entity instanceof LivingEntity _entUseItem150 ? _entUseItem150.getUseItem() : ItemStack.EMPTY).getItem() instanceof ShieldItem)
				&& (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GUNSLINGER_HELMET.get()
				&& (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GUNSLINGER_CHESTPLATE.get()
				&& (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GUNSLINGER_LEGGINGS.get()
				&& (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GUNSLINGER_BOOTS.get()
				&& !damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy")))) {
			if (event instanceof ICancellableEvent _cancellable) {
				_cancellable.setCanceled(true);
			}
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy"))), sourceentity),
					(float) (amount * 1.2));
			if (!(immediatesourceentity instanceof PerilBulletEntity) && !(immediatesourceentity instanceof IchorSpitEntity)) {
				if (!immediatesourceentity.level().isClientSide())
					immediatesourceentity.discard();
			}
		}
		if (!(immediatesourceentity == sourceentity) && !immediatesourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("the_deep_void:bullet")))
				&& !immediatesourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("the_deep_void:nocooldown_bullet")))
				&& !((entity instanceof LivingEntity _entUseItem169 ? _entUseItem169.getUseItem() : ItemStack.EMPTY).getItem() instanceof ShieldItem)
				&& (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VULTURE_HELMET.get()
				&& (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VULTURE_CHESTPLATE.get()
				&& (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VULTURE_LEGGINGS.get()
				&& (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VULTURE_BOOTS.get()
				&& (sourceentity instanceof Player _plrCldRem180
						? _plrCldRem180.getCooldowns().getCooldownPercent((sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem(), 0f) * 100
						: 0) <= 0) {
			if (event instanceof ICancellableEvent _cancellable) {
				_cancellable.setCanceled(true);
			}
			if (sourceentity instanceof Player _player)
				_player.getCooldowns().addCooldown((sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem(), 5);
			entity.hurt(damagesource, (float) (amount * 1.2));
			if ((immediatesourceentity instanceof AbstractArrow _arrowContext ? _arrowContext.getPierceLevel() : 0) <= 0) {
				if (!immediatesourceentity.level().isClientSide())
					immediatesourceentity.discard();
			}
			if (immediatesourceentity instanceof Arrow || immediatesourceentity instanceof SpectralArrow || immediatesourceentity instanceof BigShadeArrowEntity || immediatesourceentity instanceof FleshArrowEntity
					|| immediatesourceentity instanceof GoldenRotArrowEntity || immediatesourceentity instanceof ShadeArrowEntity || immediatesourceentity instanceof ShadowArrowEntity || immediatesourceentity instanceof SpiteArrowEntity
					|| immediatesourceentity instanceof VoidArrowEntity) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.arrow.hit")), SoundSource.NEUTRAL, 1,
								(float) Mth.nextDouble(RandomSource.create(), 12 / 11, 4 / 3));
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.arrow.hit")), SoundSource.NEUTRAL, 1,
								(float) Mth.nextDouble(RandomSource.create(), 12 / 11, 4 / 3), false);
					}
				}
				if (entity instanceof Player) {
					if (entity instanceof Player _plr202)
						_plr202.setArrowCount((int) ((entity instanceof Player _plr201 ? _plr201.getArrowCount() : 0) + 1));
				}
			}
		}
	}
}
