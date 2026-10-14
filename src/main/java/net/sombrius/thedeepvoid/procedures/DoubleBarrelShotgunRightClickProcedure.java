package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

import net.sombrius.thedeepvoid.item.DoubleBarrelShotgunItem;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEnchantments;
import net.sombrius.thedeepvoid.entity.ShotgunPelletEntity;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

public class DoubleBarrelShotgunRightClickProcedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()) {
			if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("shot") > 0 && (entity instanceof Player _plrCldRem6 ? _plrCldRem6.getCooldowns().getCooldownPercent(itemstack.getItem(), 0f) * 100 : 0) <= 0) {
				{
					final String _tagName = "shot";
					final double _tagValue = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("shot") - 1);
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
				}
				if (entity instanceof Player _player)
					_player.getCooldowns().addCooldown(itemstack.getItem(), (int) (EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.MARKSMAN.get(), itemstack) != 0 ? 30 : 10));
				if (itemstack.getItem() instanceof DoubleBarrelShotgunItem)
					{
						final String _tagName = "geckoAnim";
						final String _tagValue = "animation.strife_shootAll";
						CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
					}
				for (int index0 = 0; index0 < 10; index0++) {
					{
						Entity _shootFrom = entity;
						Level projectileLevel = _shootFrom.level();
						if (!projectileLevel.isClientSide()) {
							Projectile _entityToSpawn = new Object() {
								public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
									AbstractArrow entityToSpawn = new ShotgunPelletEntity(TheDeepVoidModEntities.SHOTGUN_PELLET.get(), level);
									entityToSpawn.setOwner(shooter);
									entityToSpawn.setBaseDamage(damage);
									entityToSpawn.setKnockback(knockback);
									entityToSpawn.setSilent(true);
									return entityToSpawn;
								}
							}.getArrow(projectileLevel, entity, (float) (double) DeepVoidConfigConfiguration.DOUBLEBARRELSHOTGUN.get(), 0);
							_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
							_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 60);
							projectileLevel.addFreshEntity(_entityToSpawn);
						}
					}
				}
				for (int index1 = 0; index1 < 2; index1++) {
					{
						Entity _shootFrom = entity;
						Level projectileLevel = _shootFrom.level();
						if (!projectileLevel.isClientSide()) {
							Projectile _entityToSpawn = new Object() {
								public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
									AbstractArrow entityToSpawn = new ShotgunPelletEntity(TheDeepVoidModEntities.SHOTGUN_PELLET.get(), level);
									entityToSpawn.setOwner(shooter);
									entityToSpawn.setBaseDamage(damage);
									entityToSpawn.setKnockback(knockback);
									entityToSpawn.setSilent(true);
									return entityToSpawn;
								}
							}.getArrow(projectileLevel, entity, (float) (double) DeepVoidConfigConfiguration.DOUBLEBARRELSHOTGUN.get(), 0);
							_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
							_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 35);
							projectileLevel.addFreshEntity(_entityToSpawn);
						}
					}
				}
				for (int index2 = 0; index2 < 2; index2++) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:double_barrel_trigger")), SoundSource.PLAYERS, 1,
									Mth.nextInt(RandomSource.create(), (int) 0.97, (int) 1.03));
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:double_barrel_trigger")), SoundSource.PLAYERS, 1,
									Mth.nextInt(RandomSource.create(), (int) 0.97, (int) 1.03), false);
						}
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:soul_unleash")), SoundSource.PLAYERS, 1, (float) 1.4);
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:soul_unleash")), SoundSource.PLAYERS, 1, (float) 1.4, false);
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:double_barrel_shoot")), SoundSource.PLAYERS, 1,
								Mth.nextInt(RandomSource.create(), (int) 0.99, (int) 1.01));
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:double_barrel_shoot")), SoundSource.PLAYERS, 1,
								Mth.nextInt(RandomSource.create(), (int) 0.99, (int) 1.01), false);
					}
				}
				if (!(EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.MARKSMAN.get(), itemstack) != 0)) {
					entity.setDeltaMovement(new Vec3((Math.sin(Math.toRadians(entity.getYRot() + 180)) * (-1)), (Math.sin(Math.toRadians(0 - entity.getXRot())) * (-0.5)), (Math.cos(Math.toRadians(entity.getYRot())) * (-1))));
				}
			} else if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("shot") <= 0) {
				if (entity instanceof Player _player)
					_player.getCooldowns().addCooldown(itemstack.getItem(), 50);
				{
					final String _tagName = "shot";
					final double _tagValue = 1;
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
				}
				if (itemstack.getItem() instanceof DoubleBarrelShotgunItem)
					{
						final String _tagName = "geckoAnim";
						final String _tagValue = "animation.strife_reload";
						CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
					}
				TheDeepVoidMod.queueServerWork(5, () -> {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:double_barrel_release")), SoundSource.PLAYERS, 1, 1);
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:double_barrel_release")), SoundSource.PLAYERS, 1, 1, false);
						}
					}
				});
				TheDeepVoidMod.queueServerWork(7, () -> {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:double_barrel_open")), SoundSource.PLAYERS, 1, 1);
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:double_barrel_open")), SoundSource.PLAYERS, 1, 1, false);
						}
					}
				});
				TheDeepVoidMod.queueServerWork(27, () -> {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.phantom.bite")), SoundSource.PLAYERS, 1, (float) 1.4);
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.phantom.bite")), SoundSource.PLAYERS, 1, (float) 1.4, false);
						}
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:flesh")), SoundSource.PLAYERS, 1, (float) 1.4);
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:flesh")), SoundSource.PLAYERS, 1, (float) 1.4, false);
						}
					}
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_sickness")))), (float) 4.5);
				});
				TheDeepVoidMod.queueServerWork(40, () -> {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:double_barrel_open")), SoundSource.PLAYERS, 1,
									(float) 0.98);
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:double_barrel_open")), SoundSource.PLAYERS, 1, (float) 0.98, false);
						}
					}
				});
			}
		}
	}
}
