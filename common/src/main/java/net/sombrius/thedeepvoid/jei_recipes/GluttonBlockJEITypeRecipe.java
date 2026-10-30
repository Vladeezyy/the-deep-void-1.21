package net.sombrius.thedeepvoid.jei_recipes;

import net.minecraft.world.item.ItemStackTemplate;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class GluttonBlockJEITypeRecipe implements Recipe<RecipeInput> {
	private final ItemStackTemplate output;
	private final NonNullList<Ingredient> recipeItems;

	public GluttonBlockJEITypeRecipe(ItemStackTemplate output, NonNullList<Ingredient> recipeItems) {
		this.output = output;
		this.recipeItems = recipeItems;
	}

	@Override
	public boolean matches(RecipeInput pContainer, Level pLevel) {
		if (pLevel.isClientSide()) {
			return false;
		}
		return false;
	}

	public NonNullList<Ingredient> getIngredients() {
		return recipeItems;
	}

	@Override
	public ItemStack assemble(RecipeInput pContainer) {
		return output.create();
	}

	public ItemStack getResultItem(HolderLookup.Provider provider) {
		return output.create();
	}

	@Override
	public RecipeType<GluttonBlockJEITypeRecipe> getType() {
		return Type.INSTANCE;
	}

	@Override
	public RecipeSerializer<GluttonBlockJEITypeRecipe> getSerializer() {
		return Serializer.INSTANCE;
	}

	@Override
	public String group() {
		return "";
	}

	@Override
	public boolean showNotification() {
		return false;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public net.minecraft.world.item.crafting.PlacementInfo placementInfo() {
		return net.minecraft.world.item.crafting.PlacementInfo.NOT_PLACEABLE;
	}

	@Override
	public net.minecraft.world.item.crafting.RecipeBookCategory recipeBookCategory() {
		return net.minecraft.world.item.crafting.RecipeBookCategories.CRAFTING_MISC;
	}

	public static class Type implements RecipeType<GluttonBlockJEITypeRecipe> {
		private Type() {
		}

		public static final Type INSTANCE = new Type();
		public static final String ID = "glutton_block_jei_type";
	}

	public static class Serializer {
		private static final MapCodec<GluttonBlockJEITypeRecipe> CODEC = RecordCodecBuilder
				.mapCodec(builder -> builder.group(ItemStackTemplate.CODEC.fieldOf("output").forGetter(recipe -> recipe.output), Ingredient.CODEC.listOf().fieldOf("ingredients").flatXmap(ingredients -> {
					Ingredient[] aingredient = ingredients.toArray(Ingredient[]::new); // Skip the empty check and create the array.
					if (aingredient.length == 0) {
						return DataResult.error(() -> "No ingredients found in custom recipe");
					} else {
						return DataResult.success(NonNullList.of(aingredient[0], aingredient));
					}
				}, DataResult::success).forGetter(recipe -> recipe.recipeItems)).apply(builder, GluttonBlockJEITypeRecipe::new));
		public static final StreamCodec<RegistryFriendlyByteBuf, GluttonBlockJEITypeRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);
		public static final RecipeSerializer<GluttonBlockJEITypeRecipe> INSTANCE = new RecipeSerializer<>(CODEC, STREAM_CODEC);


		private static GluttonBlockJEITypeRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
			int size = buf.readVarInt();
			NonNullList<Ingredient> inputs = NonNullList.createWithCapacity(size);
			for (int i = 0; i < size; i++)
				inputs.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
			return new GluttonBlockJEITypeRecipe(ItemStackTemplate.STREAM_CODEC.decode(buf), inputs);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buf, GluttonBlockJEITypeRecipe recipe) {
			buf.writeVarInt(recipe.getIngredients().size());
			for (Ingredient ing : recipe.getIngredients()) {
				Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ing);
			}
			ItemStackTemplate.STREAM_CODEC.encode(buf, recipe.output);
		}
	}
}
