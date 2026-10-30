
/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import net.neoforged.neoforge.registries.NeoForgeRegistries;

import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.fluid.types.LiquidIchorFluidType;
import net.sombrius.thedeepvoid.fluid.types.LiquidVoidFluidType;

public class TheDeepVoidModFluidTypes {
	public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, TheDeepVoidMod.MODID);
	public static final DeferredHolder<FluidType, FluidType> LIQUID_VOID_TYPE = REGISTRY.register("liquid_void", () -> new LiquidVoidFluidType());
	public static final DeferredHolder<FluidType, FluidType> LIQUID_ICHOR_TYPE = REGISTRY.register("liquid_ichor", () -> new LiquidIchorFluidType());
}
