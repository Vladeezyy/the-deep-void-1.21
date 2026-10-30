package net.sombrius.thedeepvoid.neoforge.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.neoforged.neoforge.fluids.FluidType;

import net.sombrius.thedeepvoid.port.neo.BaseFlowingFluid;

/** The common fluid base gives NeoForge the fluid type it was built with */
@Mixin(BaseFlowingFluid.class)
public abstract class BaseFlowingFluidMixin {
	public FluidType getFluidType() {
		return (FluidType) ((BaseFlowingFluid) (Object) this).fluidTypeObject();
	}
}
