
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
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;
import net.sombrius.thedeepvoid.init.TheDeepVoidModJeiPlugin;

public class SoulForgeJEITypeRecipeCategory implements IRecipeCategory<SoulForgeJEITypeRecipe> {
	public final static Identifier UID = Identifier.fromNamespaceAndPath("the_deep_void", "soul_forge_jei_type");
	public final static Identifier TEXTURE = Identifier.fromNamespaceAndPath("the_deep_void", "textures/screens/soul_forge_jei.png");
	private final IDrawable background;
	private final IDrawable icon;

	public SoulForgeJEITypeRecipeCategory(IGuiHelper helper) {
		this.background = helper.createDrawable(TEXTURE, 0, 0, 175, 85);
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(TheDeepVoidModBlocks.SOUL_FORGE.get().asItem()));
	}

	@Override
	public mezz.jei.api.recipe.types.IRecipeType<SoulForgeJEITypeRecipe> getRecipeType() {
		return TheDeepVoidModJeiPlugin.SoulForgeJEIType_Type;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("text.the_deep_void.port.soul_forging");
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
	public void draw(SoulForgeJEITypeRecipe recipe, mezz.jei.api.gui.ingredient.IRecipeSlotsView recipeSlotsView, net.minecraft.client.gui.GuiGraphics guiGraphics, double mouseX, double mouseY) {
		this.background.draw(guiGraphics);
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, SoulForgeJEITypeRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 25, 44).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.INPUT, 70, 44).addIngredients(recipe.getIngredients().get(1));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 124, 44).addItemStack(recipe.getResultItem(null));
	}
}
