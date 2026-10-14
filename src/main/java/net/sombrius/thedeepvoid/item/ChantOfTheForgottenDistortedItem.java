
package net.sombrius.thedeepvoid.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.core.registries.BuiltInRegistries;

public class ChantOfTheForgottenDistortedItem extends RecordItem {
	public ChantOfTheForgottenDistortedItem() {
		super(15, () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:false_hydra_sing_distorted")), new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 820);
	}
}
