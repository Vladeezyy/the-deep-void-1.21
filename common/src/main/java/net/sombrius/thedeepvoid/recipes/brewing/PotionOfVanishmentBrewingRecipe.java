
package net.sombrius.thedeepvoid.recipes.brewing;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.neo.IBrewingRecipe;
import net.sombrius.thedeepvoid.port.bus.RegisterBrewingRecipesEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;
import net.sombrius.thedeepvoid.init.TheDeepVoidModPotions;

@EventBusSubscriber
public class PotionOfVanishmentBrewingRecipe implements IBrewingRecipe {
	@SubscribeEvent
	public static void init(RegisterBrewingRecipesEvent event) {
		event.getBuilder().addRecipe(new PotionOfVanishmentBrewingRecipe());
	}

	@Override
	public boolean isInput(ItemStack input) {
		Item inputItem = input.getItem();
		Optional<Holder<Potion>> optionalPotion = input.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion();
		return (inputItem == Items.POTION || inputItem == Items.SPLASH_POTION || inputItem == Items.LINGERING_POTION) && !optionalPotion.isEmpty() && optionalPotion.get().is(Potions.INVISIBILITY);
	}

	@Override
	public boolean isIngredient(ItemStack ingredient) {
		return Ingredient.of(TheDeepVoidModBlocks.BLOCK_OF_MISTED_BONE_PILE.get()).test(ingredient);
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		if (isInput(input) && isIngredient(ingredient)) {
			return PotionContents.createItemStack(input.getItem(), TheDeepVoidModPotions.VANISHMENT.holder());
		}
		return ItemStack.EMPTY;
	}
}
