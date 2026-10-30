package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.world.item.ItemStack;

/** A brewing stand recipe in code (NeoForge's interface; NeoForge and Fabric both run the mod's list) */
public interface IBrewingRecipe {
	boolean isInput(ItemStack input);

	boolean isIngredient(ItemStack ingredient);

	ItemStack getOutput(ItemStack input, ItemStack ingredient);
}
