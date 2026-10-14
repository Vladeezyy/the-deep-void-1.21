
package net.sombrius.thedeepvoid.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;

public class SinsAndSinnersItem extends RecordItem {
	public SinsAndSinnersItem() {
		super(7, () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:sins_and_sinners")), new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 3300);
	}
}
