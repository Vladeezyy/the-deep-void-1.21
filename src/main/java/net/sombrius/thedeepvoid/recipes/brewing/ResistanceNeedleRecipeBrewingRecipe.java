
package net.sombrius.thedeepvoid.recipes.brewing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

@EventBusSubscriber
public class ResistanceNeedleRecipeBrewingRecipe implements IBrewingRecipe {
	@SubscribeEvent
	public static void init(RegisterBrewingRecipesEvent event) {
		event.getBuilder().addRecipe(new ResistanceNeedleRecipeBrewingRecipe());
	}

	@Override
	public boolean isInput(ItemStack input) {
		return Ingredient.of(new ItemStack(TheDeepVoidModItems.BONE_NEEDLE.get())).test(input);
	}

	@Override
	public boolean isIngredient(ItemStack ingredient) {
		return Ingredient.of(new ItemStack(TheDeepVoidModItems.DARK_STEEL_SCRAP.get())).test(ingredient);
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		if (isInput(input) && isIngredient(ingredient)) {
			return new ItemStack(TheDeepVoidModItems.RESISTANCE_NEEDLE.get());
		}
		return ItemStack.EMPTY;
	}
}
