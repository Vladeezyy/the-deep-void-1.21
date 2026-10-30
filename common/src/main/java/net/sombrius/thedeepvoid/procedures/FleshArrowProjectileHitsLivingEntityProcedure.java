package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class FleshArrowProjectileHitsLivingEntityProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.arrow.hit")), SoundSource.PLAYERS, 1, (float) Mth.nextDouble(RandomSource.create(), 12 / 11, 4 / 3));
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.arrow.hit")), SoundSource.PLAYERS, 1, (float) Mth.nextDouble(RandomSource.create(), 12 / 11, 4 / 3), false);
			}
		}
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.NERVES_OF_STEEL.get()
				&& PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), Enchantments.FLAME) != 0) {
			entity.igniteForSeconds(5);
		} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.NERVES_OF_STEEL.get()
				&& PortUtil.enchantmentLevel((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY), Enchantments.FLAME) != 0) {
			entity.igniteForSeconds(5);
		}
	}
}
