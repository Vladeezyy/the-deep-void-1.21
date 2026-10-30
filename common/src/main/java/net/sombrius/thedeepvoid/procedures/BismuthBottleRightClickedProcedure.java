package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class BismuthBottleRightClickedProcedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("xp", 0) == 0) {
			if ((entity instanceof Player _plr ? _plr.experienceLevel : 0) >= 15) {
				if (entity instanceof Player _player)
					_player.getCooldowns().addCooldown(itemstack, 20);
				{
					final String _tagName = "xp";
					final double _tagValue = 15;
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
				}
				if (entity instanceof Player _player)
					_player.giveExperienceLevels(-(15));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.bottle.fill_dragonbreath")), SoundSource.PLAYERS, (float) 0.4,
								(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.bottle.fill_dragonbreath")), SoundSource.PLAYERS, (float) 0.4,
								(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
					}
				}
			} else {
				if ((entity instanceof Player _plr ? _plr.experienceLevel : 0) > 0) {
					if (entity instanceof Player _player)
						_player.getCooldowns().addCooldown(itemstack, 20);
					{
						final String _tagName = "xp";
						final double _tagValue = (entity instanceof Player _plr ? _plr.experienceLevel : 0);
						CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
					}
					if (entity instanceof Player _player)
						_player.giveExperienceLevels(-(entity instanceof Player _plr ? _plr.experienceLevel : 0));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.bottle.fill_dragonbreath")), SoundSource.PLAYERS, (float) 0.4,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.bottle.fill_dragonbreath")), SoundSource.PLAYERS, (float) 0.4,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
						}
					}
				}
			}
		} else {
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack, 20);
			if (entity instanceof Player _player)
				_player.giveExperienceLevels((int) itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("xp", 0));
			{
				final String _tagName = "xp";
				final double _tagValue = 0;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.player.levelup")), SoundSource.PLAYERS, (float) 0.4,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.player.levelup")), SoundSource.PLAYERS, (float) 0.4,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
		}
	}
}
