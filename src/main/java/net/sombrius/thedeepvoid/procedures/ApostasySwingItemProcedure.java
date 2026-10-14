package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

import net.sombrius.thedeepvoid.item.ApostasyItem;

import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

public class ApostasySwingItemProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("zoom") == false) {
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 1);
			{
				final String _tagName = "zoom";
				final boolean _tagValue = true;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.spyglass.use")), SoundSource.PLAYERS, (float) 0.1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.spyglass.use")), SoundSource.PLAYERS, (float) 0.1, 1, false);
				}
			}
		} else if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("zoom") == true) {
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 1);
			{
				final String _tagName = "zoom";
				final boolean _tagValue = false;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
			}
			{
				final String _tagName = "zoomAnim";
				final boolean _tagValue = false;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
			}
			if (itemstack.getItem() instanceof ApostasyItem)
				{
					final String _tagName = "geckoAnim";
					final String _tagValue = "empty";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.spyglass.stop_using")), SoundSource.PLAYERS, (float) 0.1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.spyglass.stop_using")), SoundSource.PLAYERS, (float) 0.1, 1, false);
				}
			}
		}
	}
}
