package net.sombrius.thedeepvoid.fabric;

import net.minecraft.world.item.ItemStack;

import net.sombrius.thedeepvoid.port.neo.IBrewingRecipe;

import java.util.ArrayList;
import java.util.List;

/** The mod's brewing recipes in code (RegisterBrewingRecipesEvent); the brewing mixins ask them after vanilla's mixes */
public final class FabricBrewing {
	public static final List<IBrewingRecipe> RECIPES = new ArrayList<>();

	private FabricBrewing() {
	}

	public static boolean isInput(ItemStack stack) {
		for (IBrewingRecipe recipe : RECIPES)
			if (recipe.isInput(stack))
				return true;
		return false;
	}

	public static boolean isIngredient(ItemStack stack) {
		for (IBrewingRecipe recipe : RECIPES)
			if (recipe.isIngredient(stack))
				return true;
		return false;
	}

	public static ItemStack output(ItemStack input, ItemStack ingredient) {
		if (input.isEmpty() || ingredient.isEmpty())
			return ItemStack.EMPTY;
		for (IBrewingRecipe recipe : RECIPES) {
			ItemStack output = recipe.getOutput(input, ingredient);
			if (!output.isEmpty())
				return output;
		}
		return ItemStack.EMPTY;
	}
}
