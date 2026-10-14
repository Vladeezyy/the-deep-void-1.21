package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

public class ChantOfTheForgottenDistortedItem extends Item {
	public ChantOfTheForgottenDistortedItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.parse("the_deep_void:chant_of_the_forgotten_distorted"))));
	}
}
