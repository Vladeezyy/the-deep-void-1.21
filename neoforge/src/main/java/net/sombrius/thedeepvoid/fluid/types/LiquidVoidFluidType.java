
package net.sombrius.thedeepvoid.fluid.types;

import java.util.function.Consumer;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;

public class LiquidVoidFluidType extends FluidType {
	public LiquidVoidFluidType() {
		super(FluidType.Properties.create().fallDistanceModifier(0F).canExtinguish(true).supportsBoating(true).canHydrate(true).motionScale(-0.021D).viscosity(1500).temperature(100).canConvertToSource(true)
				.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH));
	}

	public IClientFluidTypeExtensions clientExtensions() {
		return new IClientFluidTypeExtensions() {
			private static final Identifier STILL_TEXTURE = Identifier.parse("the_deep_void:block/liquid_void"), FLOWING_TEXTURE = Identifier.parse("the_deep_void:block/liquid_void");

			@Override
			public Identifier getStillTexture() {
				return STILL_TEXTURE;
			}

			@Override
			public Identifier getFlowingTexture() {
				return FLOWING_TEXTURE;
			}
		};
	}
}
