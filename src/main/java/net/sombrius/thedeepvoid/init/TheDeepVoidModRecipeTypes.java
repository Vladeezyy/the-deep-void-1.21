package net.sombrius.thedeepvoid.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.jei_recipes.GluttonBlockJEITypeRecipe;
import net.sombrius.thedeepvoid.jei_recipes.SoulForgeJEITypeRecipe;
import net.sombrius.thedeepvoid.jei_recipes.TreasureBagJeiTypeRecipe;

@EventBusSubscriber(modid = TheDeepVoidMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class TheDeepVoidModRecipeTypes {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, "the_deep_void");

	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		event.enqueueWork(() -> {
			SERIALIZERS.register(bus);
			SERIALIZERS.register("glutton_block_jei_type", () -> GluttonBlockJEITypeRecipe.Serializer.INSTANCE);
			SERIALIZERS.register("soul_forge_jei_type", () -> SoulForgeJEITypeRecipe.Serializer.INSTANCE);
			SERIALIZERS.register("treasure_bag_jei_type", () -> TreasureBagJeiTypeRecipe.Serializer.INSTANCE);
		});
	}
}
