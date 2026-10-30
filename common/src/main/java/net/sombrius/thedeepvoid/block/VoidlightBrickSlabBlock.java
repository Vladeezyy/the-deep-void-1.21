
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class VoidlightBrickSlabBlock extends SlabBlock {
	public VoidlightBrickSlabBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().sound(SoundType.SHROOMLIGHT).strength(1f, 2f).lightLevel(s -> 6).dynamicShape());
	}
}
