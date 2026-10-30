package net.sombrius.thedeepvoid.jei_recipes;

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

public class SoulForgeJEITypeRecipe implements Recipe<RecipeInput> {
	private final ItemStack output;
	private final NonNullList<Ingredient> recipeItems;

	public SoulForgeJEITypeRecipe(ItemStack output, NonNullList<Ingredient> recipeItems) {
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
	public ItemStack assemble(RecipeInput pContainer, HolderLookup.Provider provider) {
		return output;
	}

	public ItemStack getResultItem(HolderLookup.Provider provider) {
		return output.copy();
	}

	@Override
	public RecipeType<SoulForgeJEITypeRecipe> getType() {
		return Type.INSTANCE;
	}

	@Override
	public RecipeSerializer<SoulForgeJEITypeRecipe> getSerializer() {
		return Serializer.INSTANCE;
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

	public static class Type implements RecipeType<SoulForgeJEITypeRecipe> {
		private Type() {
		}

		public static final Type INSTANCE = new Type();
		public static final String ID = "soul_forge_jei_type";
	}

	public static class Serializer implements RecipeSerializer<SoulForgeJEITypeRecipe> {
		public static final Serializer INSTANCE = new Serializer();
		private static final MapCodec<SoulForgeJEITypeRecipe> CODEC = RecordCodecBuilder
				.mapCodec(builder -> builder.group(ItemStack.STRICT_CODEC.fieldOf("output").forGetter(recipe -> recipe.output), Ingredient.CODEC.listOf().fieldOf("ingredients").flatXmap(ingredients -> {
					Ingredient[] aingredient = ingredients.toArray(Ingredient[]::new); // Skip the empty check and create the array.
					if (aingredient.length == 0) {
						return DataResult.error(() -> "No ingredients found in custom recipe");
					} else {
						return DataResult.success(NonNullList.of(aingredient[0], aingredient));
					}
				}, DataResult::success).forGetter(recipe -> recipe.recipeItems)).apply(builder, SoulForgeJEITypeRecipe::new));
		public static final StreamCodec<RegistryFriendlyByteBuf, SoulForgeJEITypeRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

		@Override
		public MapCodec<SoulForgeJEITypeRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, SoulForgeJEITypeRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static SoulForgeJEITypeRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
			int size = buf.readVarInt();
			NonNullList<Ingredient> inputs = NonNullList.createWithCapacity(size);
			for (int i = 0; i < size; i++)
				inputs.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
			return new SoulForgeJEITypeRecipe(ItemStack.STREAM_CODEC.decode(buf), inputs);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buf, SoulForgeJEITypeRecipe recipe) {
			buf.writeVarInt(recipe.getIngredients().size());
			for (Ingredient ing : recipe.getIngredients()) {
				Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ing);
			}
			ItemStack.STREAM_CODEC.encode(buf, recipe.getResultItem(null));
		}
	}
}
