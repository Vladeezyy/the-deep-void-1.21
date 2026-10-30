
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CompactBonePileSlabBlock extends SlabBlock {
	public CompactBonePileSlabBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().sound(SoundType.BONE_BLOCK).strength(0.8f, 8f).dynamicShape());
	}
}
