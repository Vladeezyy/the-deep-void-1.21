
/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.FMLClientSetupEvent;
import net.sombrius.thedeepvoid.port.platform.RegistryEntry;
import net.sombrius.thedeepvoid.port.platform.Registrar;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.fluid.LiquidIchorFluid;
import net.sombrius.thedeepvoid.fluid.LiquidVoidFluid;

public class TheDeepVoidModFluids {
	public static final Registrar<Fluid> REGISTRY = Registrar.create(BuiltInRegistries.FLUID, TheDeepVoidMod.MODID);
	public static final RegistryEntry<Fluid, FlowingFluid> LIQUID_VOID = REGISTRY.register("liquid_void", () -> new LiquidVoidFluid.Source());
	public static final RegistryEntry<Fluid, FlowingFluid> FLOWING_LIQUID_VOID = REGISTRY.register("flowing_liquid_void", () -> new LiquidVoidFluid.Flowing());
	public static final RegistryEntry<Fluid, FlowingFluid> LIQUID_ICHOR = REGISTRY.register("liquid_ichor", () -> new LiquidIchorFluid.Source());
	public static final RegistryEntry<Fluid, FlowingFluid> FLOWING_LIQUID_ICHOR = REGISTRY.register("flowing_liquid_ichor", () -> new LiquidIchorFluid.Flowing());

	@EventBusSubscriber(value = Dist.CLIENT)
	public static class FluidsClientSideHandler {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			net.sombrius.thedeepvoid.port.PortClient.setRenderLayer(LIQUID_VOID.get(), net.minecraft.client.renderer.chunk.ChunkSectionLayer.TRANSLUCENT);
			net.sombrius.thedeepvoid.port.PortClient.setRenderLayer(FLOWING_LIQUID_VOID.get(), net.minecraft.client.renderer.chunk.ChunkSectionLayer.TRANSLUCENT);
			net.sombrius.thedeepvoid.port.PortClient.setRenderLayer(LIQUID_ICHOR.get(), net.minecraft.client.renderer.chunk.ChunkSectionLayer.TRANSLUCENT);
			net.sombrius.thedeepvoid.port.PortClient.setRenderLayer(FLOWING_LIQUID_ICHOR.get(), net.minecraft.client.renderer.chunk.ChunkSectionLayer.TRANSLUCENT);
		}
	}
}
