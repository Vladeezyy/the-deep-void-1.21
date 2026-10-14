
package net.sombrius.thedeepvoid.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;

public class TheHauntItem extends RecordItem {
	public TheHauntItem() {
		super(8, () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:the_haunt")), new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 5020);
	}
}
