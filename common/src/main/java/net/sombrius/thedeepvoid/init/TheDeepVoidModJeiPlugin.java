
package net.sombrius.thedeepvoid.init;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.sombrius.thedeepvoid.jei_recipes.GluttonBlockJEITypeRecipe;
import net.sombrius.thedeepvoid.jei_recipes.GluttonBlockJEITypeRecipeCategory;
import net.sombrius.thedeepvoid.jei_recipes.SoulForgeJEITypeRecipe;
import net.sombrius.thedeepvoid.jei_recipes.SoulForgeJEITypeRecipeCategory;
import net.sombrius.thedeepvoid.jei_recipes.TreasureBagJeiTypeRecipe;
import net.sombrius.thedeepvoid.jei_recipes.TreasureBagJeiTypeRecipeCategory;

@JeiPlugin
public class TheDeepVoidModJeiPlugin implements IModPlugin {
	public static mezz.jei.api.recipe.types.IRecipeType<GluttonBlockJEITypeRecipe> GluttonBlockJEIType_Type = mezz.jei.api.recipe.types.IRecipeType.create(GluttonBlockJEITypeRecipeCategory.UID, GluttonBlockJEITypeRecipe.class);
	public static mezz.jei.api.recipe.types.IRecipeType<SoulForgeJEITypeRecipe> SoulForgeJEIType_Type = mezz.jei.api.recipe.types.IRecipeType.create(SoulForgeJEITypeRecipeCategory.UID, SoulForgeJEITypeRecipe.class);
	public static mezz.jei.api.recipe.types.IRecipeType<TreasureBagJeiTypeRecipe> TreasureBagJeiType_Type = mezz.jei.api.recipe.types.IRecipeType.create(TreasureBagJeiTypeRecipeCategory.UID, TreasureBagJeiTypeRecipe.class);

	@Override
	public Identifier getPluginUid() {
		return Identifier.parse("the_deep_void:jei_plugin");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new GluttonBlockJEITypeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new SoulForgeJEITypeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new TreasureBagJeiTypeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		List<GluttonBlockJEITypeRecipe> GluttonBlockJEITypeRecipes = net.sombrius.thedeepvoid.port.PortRecipeSync.recipes(GluttonBlockJEITypeRecipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());
		registration.addRecipes(GluttonBlockJEIType_Type, GluttonBlockJEITypeRecipes);
		List<SoulForgeJEITypeRecipe> SoulForgeJEITypeRecipes = net.sombrius.thedeepvoid.port.PortRecipeSync.recipes(SoulForgeJEITypeRecipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());
		registration.addRecipes(SoulForgeJEIType_Type, SoulForgeJEITypeRecipes);
		List<TreasureBagJeiTypeRecipe> TreasureBagJeiTypeRecipes = net.sombrius.thedeepvoid.port.PortRecipeSync.recipes(TreasureBagJeiTypeRecipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());
		registration.addRecipes(TreasureBagJeiType_Type, TreasureBagJeiTypeRecipes);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(TheDeepVoidModBlocks.GLUTTON_BLOCK.get().asItem()), GluttonBlockJEIType_Type);
		registration.addRecipeCatalyst(new ItemStack(TheDeepVoidModBlocks.SOUL_FORGE.get().asItem()), SoulForgeJEIType_Type);
	}
}
