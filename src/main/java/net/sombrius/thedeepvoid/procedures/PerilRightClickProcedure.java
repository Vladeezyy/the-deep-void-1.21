package net.sombrius.thedeepvoid.procedures;

import net.minecraft.advancements.AdvancementHolder;

import net.sombrius.thedeepvoid.port.PortUtil;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

import net.sombrius.thedeepvoid.item.PerilItem;
import net.sombrius.thedeepvoid.init.TheDeepVoidModParticleTypes;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEnchantments;
import net.sombrius.thedeepvoid.entity.PerilBulletEntity;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

public class PerilRightClickProcedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()) {
			if (!(PortUtil.enchantmentLevel(itemstack, TheDeepVoidModEnchantments.AKIMBO) != 0)) {
				if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("shot") > 0 && (entity instanceof Player _plrCldRem8 ? _plrCldRem8.getCooldowns().getCooldownPercent(itemstack.getItem(), 0f) * 100 : 0) <= 0) {
					if (entity.isShiftKeyDown()) {
						if (entity instanceof Player _player)
							_player.getCooldowns().addCooldown(itemstack.getItem(), 60);
						if (Math.random() < 1 / itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("selfShot")) {
							{
								final String _tagName = "shootSelf";
								final boolean _tagValue = true;
								CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
							}
						}
						if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("shootSelf") == true) {
							if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()) {
								if (itemstack.getItem() instanceof PerilItem)
									{
										final String _tagName = "geckoAnim";
										final String _tagValue = "animation.peril_shootSelfLeft";
										CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
									}
							} else {
								if (itemstack.getItem() instanceof PerilItem)
									{
										final String _tagName = "geckoAnim";
										final String _tagValue = "animation.peril_shootSelf";
										CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
									}
							}
							if (world instanceof Level)
								((Level) world).playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_cylinder")), SoundSource.PLAYERS, 1, 1, false);
						} else {
							if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()) {
								if (itemstack.getItem() instanceof PerilItem)
									{
										final String _tagName = "geckoAnim";
										final String _tagValue = "animation.peril_shootSelfFailLeft";
										CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
									}
							} else {
								if (itemstack.getItem() instanceof PerilItem)
									{
										final String _tagName = "geckoAnim";
										final String _tagValue = "animation.peril_shootSelfFail";
										CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
									}
							}
							if (world instanceof Level)
								((Level) world).playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_cylinder")), SoundSource.PLAYERS, 1, 1, false);
							{
								final String _tagName = "selfShot";
								final double _tagValue = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("selfShot") - 1);
								CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
							}
						}
						TheDeepVoidMod.queueServerWork(45, () -> {
							if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("shootSelf") == true) {
								{
									final String _tagName = "shootSelf";
									final boolean _tagValue = false;
									CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
								}
								{
									final String _tagName = "adrenaline";
									final double _tagValue = 0;
									CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
								}
								{
									final String _tagName = "selfShot";
									final double _tagValue = 6;
									CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
								}
								entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:suicide")))),
										(float) (double) DeepVoidConfigConfiguration.PERILSELFDAMAGE.get());
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_hammer_hit")), SoundSource.PLAYERS, 1,
												1);
									} else {
										_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_hammer_hit")), SoundSource.PLAYERS, 1, 1, false);
									}
								}
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_shoot_new")), SoundSource.PLAYERS, 1, 1);
									} else {
										_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_shoot_new")), SoundSource.PLAYERS, 1, 1, false);
									}
								}
								{
									final String _tagName = "shot";
									final double _tagValue = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("shot") - 1);
									CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
								}
								if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= 0) {
									if (world instanceof Level _level) {
										if (!_level.isClientSide()) {
											_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:fleshy_explosion")), SoundSource.PLAYERS,
													1, 1);
										} else {
											_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:fleshy_explosion")), SoundSource.PLAYERS, 1, 1, false);
										}
									}
									if (world instanceof ServerLevel _level)
										_level.sendParticles((SimpleParticleType) (TheDeepVoidModParticleTypes.LASTING_BLOOD.get()), (entity.getX()), (entity.getY() + entity.getEyeHeight()), (entity.getZ()), 24, 0.5, 0.5, 0.5, 0.25);
									if (world instanceof ServerLevel _level)
										_level.sendParticles((SimpleParticleType) (TheDeepVoidModParticleTypes.BRAIN_PIECE.get()), (entity.getX()), (entity.getY() + entity.getEyeHeight()), (entity.getZ()), 24, 0.5, 0.1, 0.5, 0.25);
								}
							} else {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_hammer_hit")), SoundSource.PLAYERS, 1,
												1);
									} else {
										_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_hammer_hit")), SoundSource.PLAYERS, 1, 1, false);
									}
								}
								{
									final String _tagName = "adrenaline";
									final double _tagValue = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("adrenaline") + 1);
									CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
								}
								if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("adrenaline") >= 5 && !(entity instanceof ServerPlayer _plr92 && _plr92.level() instanceof ServerLevel
										&& _plr92.getAdvancements().getOrStartProgress(_plr92.server.getAdvancements().getAdvancement(ResourceLocation.parse("the_deep_void:i_cant_stop_winning"))).isDone())) {
									if (entity instanceof ServerPlayer _player) {
										AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("the_deep_void:i_cant_stop_winning"));
										AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
										if (!_ap.isDone()) {
											for (String criteria : _ap.getRemainingCriteria())
												_player.getAdvancements().award(_adv, criteria);
										}
									}
								}
							}
						});
					} else {
						if (entity instanceof Player _player)
							_player.getCooldowns().addCooldown(itemstack.getItem(), 25);
						{
							final String _tagName = "shot";
							final double _tagValue = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("shot") - 1);
							CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
						}
						if (itemstack.getItem() instanceof PerilItem)
							{
								final String _tagName = "geckoAnim";
								final String _tagValue = "animation.peril_shoot";
								CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
							}
						{
							Entity _shootFrom = entity;
							Level projectileLevel = _shootFrom.level();
							if (!projectileLevel.isClientSide()) {
								Projectile _entityToSpawn = new Object() {
									public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
										AbstractArrow entityToSpawn = new PerilBulletEntity(TheDeepVoidModEntities.PERIL_BULLET.get(), level);
										entityToSpawn.setOwner(shooter);
										entityToSpawn.setBaseDamage(damage);
										PortUtil.setKnockback(entityToSpawn, knockback);
										entityToSpawn.setSilent(true);
										entityToSpawn.setPierceLevel(piercing);
										return entityToSpawn;
									}
								}.getArrow(projectileLevel, entity,
										(float) ((double) DeepVoidConfigConfiguration.PERIL.get() * (1 + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("adrenaline") * (double) DeepVoidConfigConfiguration.PERILDAMAGEMULTIPLIER.get())), 0, (byte) 5);
								_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
								_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 0);
								projectileLevel.addFreshEntity(_entityToSpawn);
							}
						}
						{
							final String _tagName = "adrenaline";
							final double _tagValue = 0;
							CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
						}
						{
							final String _tagName = "selfShot";
							final double _tagValue = 6;
							CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
						}
						if (world instanceof Level)
							((Level) world).playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_hammer_hit")), SoundSource.PLAYERS, 1, 1, false);
						if (world instanceof Level)
							((Level) world).playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_shoot_new")), SoundSource.PLAYERS, 1, 1, false);
						TheDeepVoidMod.queueServerWork(17, () -> {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_cocking")), SoundSource.PLAYERS, 1, 1);
								} else {
									_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_cocking")), SoundSource.PLAYERS, 1, 1, false);
								}
							}
						});
					}
				} else if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("shot") <= 0) {
					if (entity instanceof Player _player)
						_player.getCooldowns().addCooldown(itemstack.getItem(), 50);
					{
						final String _tagName = "shot";
						final double _tagValue = 6;
						CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
					}
					{
						final String _tagName = "selfShot";
						final double _tagValue = 6;
						CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
					}
					{
						final String _tagName = "adrenaline";
						final double _tagValue = 0;
						CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
					}
					{
						final String _tagName = "shootSelf";
						final boolean _tagValue = false;
						CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
					}
					if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()) {
						if (itemstack.getItem() instanceof PerilItem)
							{
								final String _tagName = "geckoAnim";
								final String _tagValue = "animation.peril_reloadLeft";
								CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
							}
					} else {
						if (itemstack.getItem() instanceof PerilItem)
							{
								final String _tagName = "geckoAnim";
								final String _tagValue = "animation.peril_reload";
								CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
							}
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 4, false, false));
					TheDeepVoidMod.queueServerWork(10, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_cylinder")), SoundSource.PLAYERS, 1,
										Mth.nextInt(RandomSource.create(), 1, (int) 1.1));
							} else {
								_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_cylinder")), SoundSource.PLAYERS, 1,
										Mth.nextInt(RandomSource.create(), 1, (int) 1.1), false);
							}
						}
					});
					TheDeepVoidMod.queueServerWork(25, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.fox.bite")), SoundSource.PLAYERS, 1,
										(float) Mth.nextDouble(RandomSource.create(), 0.8, 0.9));
							} else {
								_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.fox.bite")), SoundSource.PLAYERS, 1,
										(float) Mth.nextDouble(RandomSource.create(), 0.8, 0.9), false);
							}
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:flesh")), SoundSource.PLAYERS, 1,
										(float) Mth.nextDouble(RandomSource.create(), 0.9, 1));
							} else {
								_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:flesh")), SoundSource.PLAYERS, 1,
										(float) Mth.nextDouble(RandomSource.create(), 0.9, 1), false);
							}
						}
						entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC)),
								(float) ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GUNSLINGER_HELMET.get()
										&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GUNSLINGER_CHESTPLATE.get()
										&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GUNSLINGER_LEGGINGS.get()
										&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GUNSLINGER_BOOTS.get() ? 2 : 4));
					});
					TheDeepVoidMod.queueServerWork(38, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_cylinder")), SoundSource.PLAYERS, 1,
										Mth.nextInt(RandomSource.create(), (int) 0.9, 1));
							} else {
								_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_cylinder")), SoundSource.PLAYERS, 1,
										Mth.nextInt(RandomSource.create(), (int) 0.9, 1), false);
							}
						}
					});
					TheDeepVoidMod.queueServerWork(45, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_cocking")), SoundSource.PLAYERS, 1, 1);
							} else {
								_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:peril_cocking")), SoundSource.PLAYERS, 1, 1, false);
							}
						}
					});
				}
			}
		}
	}
}
