package net.sombrius.thedeepvoid.port;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.item.crafting.RecipeType;

import net.sombrius.thedeepvoid.jei_recipes.GluttonBlockJEITypeRecipe;
import net.sombrius.thedeepvoid.jei_recipes.SoulForgeJEITypeRecipe;
import net.sombrius.thedeepvoid.jei_recipes.TreasureBagJeiTypeRecipe;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * The mod's JEI recipe types on the client. Since 1.21.2 the client has no recipe manager: the server sends these
 * types on datapack sync (NeoForge OnDatapackSyncEvent.sendRecipes, Fabric RecipeSynchronization) and the loaders
 * hand the received recipes here before JEI reads them.
 */
public final class PortRecipeSync {
	/** The synced recipe types (JEI only shows them; their recipes never match) */
	public static final List<Supplier<RecipeType<?>>> TYPES = List.of(() -> GluttonBlockJEITypeRecipe.Type.INSTANCE, () -> SoulForgeJEITypeRecipe.Type.INSTANCE,
			() -> TreasureBagJeiTypeRecipe.Type.INSTANCE);
	private static RecipeMap recipes = RecipeMap.EMPTY;

	private PortRecipeSync() {
	}

	/** 26.3: Fabric hands the received recipes over (a recipe map is made from a registry only) */
	private static Collection<RecipeHolder<?>> received = List.of();

	public static void setRecipes(RecipeMap received) {
		recipes = received;
	}

	public static void setRecipes(Collection<RecipeHolder<?>> list) {
		received = List.copyOf(list);
	}

	@SuppressWarnings("unchecked")
	public static <I extends RecipeInput, T extends Recipe<I>> Collection<RecipeHolder<T>> recipes(RecipeType<T> type) {
		if (!received.isEmpty())
			return received.stream().filter(r -> r.value().getType() == type).map(r -> (RecipeHolder<T>) r).toList();
		return recipes.byType(type);
	}
}
