package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class MimicEntityIsHurtProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return false;
		if (sourceentity instanceof LivingEntity) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof ShieldItem) {
				if (Math.random() < 0.2 && !(entity instanceof Player _plrCldCheck4 && _plrCldCheck4.getCooldowns().isOnCooldown((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)))) {
					if (entity instanceof Player _player)
						_player.getCooldowns().addCooldown((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), 30);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 15, 1, false, false));
					if (entity instanceof LivingEntity _entity)
						_entity.swing(InteractionHand.MAIN_HAND, net.minecraft.world.item.component.SwingAnimation.DEFAULT, true);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.shield.block")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.shield.block")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
						}
					}
					return false;
				}
			} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() instanceof ShieldItem) {
				if (Math.random() < 0.2 && !(entity instanceof Player _plrCldCheck14 && _plrCldCheck14.getCooldowns().isOnCooldown((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)))) {
					if (entity instanceof Player _player)
						_player.getCooldowns().addCooldown((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY), 30);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 15, 1, false, false));
					if (entity instanceof LivingEntity _entity)
						_entity.swing(InteractionHand.OFF_HAND, net.minecraft.world.item.component.SwingAnimation.DEFAULT, true);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.shield.block")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.shield.block")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
						}
					}
					return false;
				}
			}
		}
		return true;
	}
}
