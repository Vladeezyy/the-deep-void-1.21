package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.entity.HarbingerOfCataclysmEntity;

public class HarbingerOfCataclysmPlayerCollidesWithThisEntityProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double rnd = 0;
		if ((entity instanceof HarbingerOfCataclysmEntity _datEntL0 && _datEntL0.getEntityData().get(HarbingerOfCataclysmEntity.DATA_piercing)) == true && !((sourceentity instanceof LivingEntity _livEnt ? _livEnt.hurtTime : 0) != 0)) {
			if ((sourceentity instanceof LivingEntity _entUseItem2 ? _entUseItem2.getUseItem() : ItemStack.EMPTY).getItem() instanceof ShieldItem) {
				if (sourceentity instanceof Player _player)
					_player.getCooldowns().addCooldown((sourceentity instanceof LivingEntity _entUseItem4 ? _entUseItem4.getUseItem() : ItemStack.EMPTY), 100);
				{
					ItemStack _ist = (sourceentity instanceof LivingEntity _entUseItem6 ? _entUseItem6.getUseItem() : ItemStack.EMPTY);
					PortUtil.hurtAndBreak(_ist, 6, null);
				}
				if (sourceentity instanceof LivingEntity _entity)
					_entity.stopUsingItem();
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.shield.break")), SoundSource.PLAYERS, 1, 1);
					} else {
						_level.playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.shield.break")), SoundSource.PLAYERS, 1, 1, false);
					}
				}
			} else {
				sourceentity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.parse("the_deep_void:boss_attack"))), entity), 12);
			}
		}
	}
}
