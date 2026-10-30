
package net.sombrius.thedeepvoid.recipes.brewing;

import net.minecraft.world.item.alchemy.PotionContents;
import net.sombrius.thedeepvoid.port.bus.RegisterBrewingRecipesEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.sombrius.thedeepvoid.port.neo.IBrewingRecipe;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.FMLCommonSetupEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModPotions;

@EventBusSubscriber
public class BadLuckRecipeBrewingRecipe implements IBrewingRecipe {
	@SubscribeEvent
	public static void init(RegisterBrewingRecipesEvent event) {
		event.getBuilder().addRecipe(new BadLuckRecipeBrewingRecipe());
	}

	@Override
	public boolean isInput(ItemStack input) {
		return Ingredient.of(new ItemStack(TheDeepVoidModItems.FISHY_POTION.get())).test(input);
	}

	@Override
	public boolean isIngredient(ItemStack ingredient) {
		return Ingredient.of(new ItemStack(TheDeepVoidModItems.CURSED_PEARL.get())).test(ingredient);
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		if (isInput(input) && isIngredient(ingredient)) {
			return PotionContents.createItemStack(Items.POTION, TheDeepVoidModPotions.BAD_LUCK.holder());
		}
		return ItemStack.EMPTY;
	}
}
