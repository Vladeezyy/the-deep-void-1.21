
package net.sombrius.thedeepvoid.jei_recipes;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModJeiPlugin;

public class TreasureBagJeiTypeRecipeCategory implements IRecipeCategory<TreasureBagJeiTypeRecipe> {
	public final static Identifier UID = Identifier.fromNamespaceAndPath("the_deep_void", "treasure_bag_jei_type");
	public final static Identifier TEXTURE = Identifier.fromNamespaceAndPath("the_deep_void", "textures/screens/treasure_bag_jei.png");
	private final IDrawable background;
	private final IDrawable icon;

	public TreasureBagJeiTypeRecipeCategory(IGuiHelper helper) {
		this.background = helper.createDrawable(TEXTURE, 0, 0, 163, 85);
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(TheDeepVoidModItems.APOSTLE_OF_CATASTROPHE_TREASURE_BAG.get()));
	}

	@Override
	public mezz.jei.api.recipe.types.IRecipeType<TreasureBagJeiTypeRecipe> getRecipeType() {
		return TheDeepVoidModJeiPlugin.TreasureBagJeiType_Type;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("text.the_deep_void.port.treasure_bag");
	}

	@Override
	public int getWidth() {
		return this.background.getWidth();
	}

	@Override
	public int getHeight() {
		return this.background.getHeight();
	}

	@Override
	public void draw(TreasureBagJeiTypeRecipe recipe, mezz.jei.api.gui.ingredient.IRecipeSlotsView recipeSlotsView, net.minecraft.client.gui.GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
		this.background.draw(guiGraphics);
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, TreasureBagJeiTypeRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 34, 35).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 114, 35).addItemStack(recipe.getResultItem(null));
	}
}
