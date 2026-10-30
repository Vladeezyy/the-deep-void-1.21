package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortUtil;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.entity.HarbingerOfCataclysmEntity;

public class HarbingerTeleportSlashProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof HarbingerOfCataclysmEntity) {
			((HarbingerOfCataclysmEntity) entity).setAnimation("empty");
		}
		if (entity instanceof HarbingerOfCataclysmEntity) {
			((HarbingerOfCataclysmEntity) entity).setAnimation("animation.harbinger_teleportSlash");
		}
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 16, 99, false, false));
		TheDeepVoidMod.queueServerWork(9, () -> {
			{
				final Vec3 _center = new Vec3(
						(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(1)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getX()),
						(entity.getY()),
						(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(1)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ()));
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof LivingEntity && !(entityiterator == entity) && !entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, Identifier.parse("the_deep_void:boss")))) {
						if ((entityiterator instanceof LivingEntity _entUseItem9 ? _entUseItem9.getUseItem() : ItemStack.EMPTY).getItem() instanceof ShieldItem) {
							if (entityiterator instanceof Player _player)
								_player.getCooldowns().addCooldown((entityiterator instanceof LivingEntity _entUseItem11 ? _entUseItem11.getUseItem() : ItemStack.EMPTY), 100);
							{
								ItemStack _ist = (entityiterator instanceof LivingEntity _entUseItem13 ? _entUseItem13.getUseItem() : ItemStack.EMPTY);
								PortUtil.hurtAndBreak(_ist, 6, null);
							}
							if (entityiterator instanceof LivingEntity _entity)
								_entity.stopUsingItem();
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.shield.break")),
											SoundSource.PLAYERS, 1, 1);
								} else {
									_level.playLocalSound((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.shield.break")), SoundSource.PLAYERS, 1, 1, false);
								}
							}
						} else {
							entityiterator.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.parse("the_deep_void:boss_attack"))), entity),
									12);
						}
					}
				}
			}
		});
		TheDeepVoidMod.queueServerWork(16, () -> {
			if (Math.random() < 0.2 && (entity instanceof HarbingerOfCataclysmEntity _datEntI ? _datEntI.getEntityData().get(HarbingerOfCataclysmEntity.DATA_repeat) : 0) < 4) {
				if (entity instanceof HarbingerOfCataclysmEntity _datEntSetI)
					_datEntSetI.getEntityData().set(HarbingerOfCataclysmEntity.DATA_repeat, (int) ((entity instanceof HarbingerOfCataclysmEntity _datEntI ? _datEntI.getEntityData().get(HarbingerOfCataclysmEntity.DATA_repeat) : 0) + 1));
				HarbingerTeleportProcedure.execute(world, entity);
			} else {
				if (entity instanceof HarbingerOfCataclysmEntity _datEntSetL)
					_datEntSetL.getEntityData().set(HarbingerOfCataclysmEntity.DATA_attacking, false);
			}
		});
	}
}
