
/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.fluid.LiquidIchorFluid;
import net.sombrius.thedeepvoid.fluid.LiquidVoidFluid;

public class TheDeepVoidModFluids {
	public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(BuiltInRegistries.FLUID, TheDeepVoidMod.MODID);
	public static final DeferredHolder<Fluid, FlowingFluid> LIQUID_VOID = REGISTRY.register("liquid_void", () -> new LiquidVoidFluid.Source());
	public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_LIQUID_VOID = REGISTRY.register("flowing_liquid_void", () -> new LiquidVoidFluid.Flowing());
	public static final DeferredHolder<Fluid, FlowingFluid> LIQUID_ICHOR = REGISTRY.register("liquid_ichor", () -> new LiquidIchorFluid.Source());
	public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_LIQUID_ICHOR = REGISTRY.register("flowing_liquid_ichor", () -> new LiquidIchorFluid.Flowing());

	@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class FluidsClientSideHandler {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			ItemBlockRenderTypes.setRenderLayer(LIQUID_VOID.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_LIQUID_VOID.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(LIQUID_ICHOR.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_LIQUID_ICHOR.get(), RenderType.translucent());
		}
	}
}
