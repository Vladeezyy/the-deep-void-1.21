package net.sombrius.thedeepvoid.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.sombrius.thedeepvoid.port.platform.Registrar;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.jei_recipes.GluttonBlockJEITypeRecipe;
import net.sombrius.thedeepvoid.jei_recipes.SoulForgeJEITypeRecipe;
import net.sombrius.thedeepvoid.jei_recipes.TreasureBagJeiTypeRecipe;

public class TheDeepVoidModRecipeTypes {
	public static final Registrar<RecipeSerializer<?>> SERIALIZERS = Registrar.create(BuiltInRegistries.RECIPE_SERIALIZER, "the_deep_void");

	public static final Registrar<RecipeType<?>> TYPES = Registrar.create(BuiltInRegistries.RECIPE_TYPE, "the_deep_void");

	static {
		TYPES.register(GluttonBlockJEITypeRecipe.Type.ID, () -> GluttonBlockJEITypeRecipe.Type.INSTANCE);
		TYPES.register(SoulForgeJEITypeRecipe.Type.ID, () -> SoulForgeJEITypeRecipe.Type.INSTANCE);
		TYPES.register(TreasureBagJeiTypeRecipe.Type.ID, () -> TreasureBagJeiTypeRecipe.Type.INSTANCE);
		SERIALIZERS.register("glutton_block_jei_type", () -> GluttonBlockJEITypeRecipe.Serializer.INSTANCE);
		SERIALIZERS.register("soul_forge_jei_type", () -> SoulForgeJEITypeRecipe.Serializer.INSTANCE);
		SERIALIZERS.register("treasure_bag_jei_type", () -> TreasureBagJeiTypeRecipe.Serializer.INSTANCE);
	}
}
