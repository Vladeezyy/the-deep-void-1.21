package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortUtil;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.ICancellableEvent;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.neo.Capabilities;
import net.sombrius.thedeepvoid.port.bus.LivingIncomingDamageEvent;
import net.sombrius.thedeepvoid.port.neo.IItemHandlerModifiable;
import net.sombrius.thedeepvoid.entity.BloodSpitEntity;
import net.sombrius.thedeepvoid.entity.PoisonKnifeEntity;
import net.sombrius.thedeepvoid.entity.RotKnifeEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class AdaptiveClawToolInHandTickProcedure {
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
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ADAPTIVE_CLAW.get()
				&& !damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.parse("the_deep_void:void_energy"))) && immediatesourceentity == sourceentity) {
			if (event instanceof ICancellableEvent _cancellable) {
				_cancellable.setCanceled(true);
			}
			{
				final String _tagName = "damage";
				final double _tagValue = 0;
				CustomData.update(DataComponents.CUSTOM_DATA, (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
			}
			{
				final String _tagName = "claw";
				final String _tagValue = "";
				CustomData.update(DataComponents.CUSTOM_DATA, (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putString(_tagName, _tagValue));
			}
			if (net.sombrius.thedeepvoid.port.neo.PortCaps.entity(sourceentity, null) instanceof IItemHandlerModifiable _modHandler) {
				for (int _idx = 0; _idx < _modHandler.getSlots(); _idx++) {
					ItemStack itemstackiterator = _modHandler.getStackInSlot(_idx).copy();
					if (itemstackiterator.is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("the_deep_void:claws"))) && !(itemstackiterator.getItem() == TheDeepVoidModItems.ADAPTIVE_CLAW.get())) {
						{
							final String _tagName = "damage";
							final double _tagValue = ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("damage", 0) + 0.65);
							CustomData.update(DataComponents.CUSTOM_DATA, (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
						}
						if (Math.random() < 0.25 && ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("claw", "")).isEmpty()) {
							{
								final String _tagName = "claw";
								final String _tagValue = (BuiltInRegistries.ITEM.getKey(itemstackiterator.getItem()).toString());
								CustomData.update(DataComponents.CUSTOM_DATA, (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putString(_tagName, _tagValue));
							}
						}
					}
				}
			}
			entity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.parse("the_deep_void:void_energy"))), sourceentity), (float) (amount
					+ Math.round(Math.sqrt((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("damage", 0)) * 1.55)));
			if ((sourceentity instanceof Player _plr ? _plr.getAttackStrengthScale(0) : 0) >= 0.848) {
				if (BuiltInRegistries.ITEM
						.getValue(Identifier.parse((((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("claw", "")))
								.toLowerCase(java.util.Locale.ENGLISH))) == TheDeepVoidModItems.STALKER_SCYTHE_CLAW.get()) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.DECOMPOSITION.holder(), 200, 0));
				} else if (BuiltInRegistries.ITEM
						.getValue(Identifier.parse((((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("claw", "")))
								.toLowerCase(java.util.Locale.ENGLISH))) == TheDeepVoidModItems.FLESH_GLOVE.get()) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.LACERATION.holder(), 40, 0));
				} else if (BuiltInRegistries.ITEM
						.getValue(Identifier.parse((((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("claw", "")))
								.toLowerCase(java.util.Locale.ENGLISH))) == TheDeepVoidModItems.PLAGUE_DOCTOR_GLOVE.get()) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.PLAGUE.holder(), 100, 0));
				} else if (BuiltInRegistries.ITEM
						.getValue(Identifier.parse((((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("claw", "")))
								.toLowerCase(java.util.Locale.ENGLISH))) == TheDeepVoidModItems.RUSTY_CLAWS.get()) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.BROKEN_ARMOR.holder(), 30, 0));
				} else if (BuiltInRegistries.ITEM
						.getValue(Identifier.parse((((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("claw", "")))
								.toLowerCase(java.util.Locale.ENGLISH))) == TheDeepVoidModItems.HUNTER_CLAWS.get()) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROT.holder(), 50, 1));
				} else if (BuiltInRegistries.ITEM
						.getValue(Identifier.parse((((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("claw", "")))
								.toLowerCase(java.util.Locale.ENGLISH))) == TheDeepVoidModItems.BONE_CRAWLER_CLAW.get()) {
					if (entity instanceof LivingEntity _livEnt57 && _livEnt57.hasEffect(TheDeepVoidModMobEffects.ROT.holder())) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(
									new MobEffectInstance(TheDeepVoidModMobEffects.ROT.holder(), entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.ROT.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.ROT.holder()).getDuration() : 0,
											(int) ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.ROT.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.ROT.holder()).getAmplifier() : 0) + 1)));
					}
				} else if (BuiltInRegistries.ITEM
						.getValue(Identifier.parse((((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("claw", "")))
								.toLowerCase(java.util.Locale.ENGLISH))) == TheDeepVoidModItems.KNIVES_GLOVE.get()) {
					{
						Entity _shootFrom = sourceentity;
						Level projectileLevel = _shootFrom.level();
						if (!projectileLevel.isClientSide()) {
							Projectile _entityToSpawn = new Object() {
								public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
									AbstractArrow entityToSpawn = new PoisonKnifeEntity(TheDeepVoidModEntities.POISON_KNIFE.get(), level);
									entityToSpawn.setOwner(shooter);
									entityToSpawn.setBaseDamage(damage);
									PortUtil.setKnockback(entityToSpawn, knockback);
									entityToSpawn.setSilent(true);
									return entityToSpawn;
								}
							}.getArrow(projectileLevel, sourceentity, 2, (int) 0.1);
							_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
							_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, (float) 1.4, 5);
							projectileLevel.addFreshEntity(_entityToSpawn);
						}
					}
					{
						Entity _shootFrom = sourceentity;
						Level projectileLevel = _shootFrom.level();
						if (!projectileLevel.isClientSide()) {
							Projectile _entityToSpawn = new Object() {
								public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
									AbstractArrow entityToSpawn = new RotKnifeEntity(TheDeepVoidModEntities.ROT_KNIFE.get(), level);
									entityToSpawn.setOwner(shooter);
									entityToSpawn.setBaseDamage(damage);
									PortUtil.setKnockback(entityToSpawn, knockback);
									entityToSpawn.setSilent(true);
									return entityToSpawn;
								}
							}.getArrow(projectileLevel, sourceentity, 2, (int) 0.1);
							_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
							_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, (float) 1.4, 5);
							projectileLevel.addFreshEntity(_entityToSpawn);
						}
					}
				} else if (BuiltInRegistries.ITEM
						.getValue(Identifier.parse((((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("claw", "")))
								.toLowerCase(java.util.Locale.ENGLISH))) == TheDeepVoidModItems.BLOODTHIRSTY_CLAWS.get()) {
					{
						Entity _shootFrom = sourceentity;
						Level projectileLevel = _shootFrom.level();
						if (!projectileLevel.isClientSide()) {
							Projectile _entityToSpawn = new Object() {
								public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
									AbstractArrow entityToSpawn = new BloodSpitEntity(TheDeepVoidModEntities.BLOOD_SPIT.get(), level);
									entityToSpawn.setOwner(shooter);
									entityToSpawn.setBaseDamage(damage);
									PortUtil.setKnockback(entityToSpawn, knockback);
									entityToSpawn.setSilent(true);
									return entityToSpawn;
								}
							}.getArrow(projectileLevel, sourceentity, 2, (int) 0.1);
							_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
							_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, (float) 1.6, 5);
							projectileLevel.addFreshEntity(_entityToSpawn);
						}
					}
				} else if (BuiltInRegistries.ITEM
						.getValue(Identifier.parse((((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("claw", "")))
								.toLowerCase(java.util.Locale.ENGLISH))) == TheDeepVoidModItems.CHAIN_GAUNTLET.get()) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.CHAINED.holder(), 30, 0));
				}
				if (!sourceentity.onGround()) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.player.attack.crit")), SoundSource.PLAYERS, 1, 1);
						} else {
							_level.playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.player.attack.crit")), SoundSource.PLAYERS, 1, 1, false);
						}
					}
				} else {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:claws_slash")), SoundSource.PLAYERS, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
						} else {
							_level.playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:claws_slash")), SoundSource.PLAYERS, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
						}
					}
				}
			}
		}
	}
}
