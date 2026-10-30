package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.FalseHydraEntity;

public class FalseHydraPlayerCollidesWithThisEntityProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if ((entity instanceof FalseHydraEntity _datEntL0 && _datEntL0.getEntityData().get(FalseHydraEntity.DATA_volley)) == true) {
			if ((sourceentity instanceof LivingEntity _entUseItem1 ? _entUseItem1.getUseItem() : ItemStack.EMPTY).getItem() instanceof ShieldItem) {
				if (sourceentity instanceof Player _player)
					_player.getCooldowns().addCooldown((sourceentity instanceof LivingEntity _entUseItem3 ? _entUseItem3.getUseItem() : ItemStack.EMPTY), 100);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.shield.break")), SoundSource.PLAYERS, 1, 1);
					} else {
						_level.playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.shield.break")), SoundSource.PLAYERS, 1, 1, false);
					}
				}
				if (sourceentity instanceof LivingEntity _entity)
					_entity.stopUsingItem();
			} else {
				sourceentity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.MOB_ATTACK), entity),
						(float) ((double) DeepVoidConfigConfiguration.HYDRASLIDEDAMAGE.get() + (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.STRENGTH) ? _livEnt.getEffect(MobEffects.STRENGTH).getAmplifier() : 0)));
			}
		}
	}
}
