package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.PrimordialBoneCrawlerEntity;

public class PrimordialBoneCrawlerPlayerCollidesProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if ((entity instanceof PrimordialBoneCrawlerEntity _datEntL0 && _datEntL0.getEntityData().get(PrimordialBoneCrawlerEntity.DATA_dashing)) == true) {
			if ((sourceentity instanceof LivingEntity _entUseItem1 ? _entUseItem1.getUseItem() : ItemStack.EMPTY).getItem() instanceof ShieldItem) {
				if (sourceentity instanceof Player _player)
					_player.getCooldowns().addCooldown((sourceentity instanceof LivingEntity _entUseItem3 ? _entUseItem3.getUseItem() : ItemStack.EMPTY), 140);
				{
					ItemStack _ist = (sourceentity instanceof LivingEntity _entUseItem5 ? _entUseItem5.getUseItem() : ItemStack.EMPTY);
					PortUtil.hurtAndBreak(_ist, 20, null);
				}
				if (sourceentity instanceof LivingEntity _entity)
					_entity.stopUsingItem();
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.shield.break")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.shield.break")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
			} else {
				if (world instanceof Level)
					((Level) world).playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.wither.break_block")), SoundSource.HOSTILE, 1, 1, false);
				sourceentity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.MOB_ATTACK), entity), (float) (double) DeepVoidConfigConfiguration.PRIMORDIALCRAWLERDASH.get());
				if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 20, 1, false, false));
			}
		}
	}
}
