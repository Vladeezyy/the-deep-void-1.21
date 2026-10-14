
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CompactBonePileStairsBlock extends StairBlock {
	public CompactBonePileStairsBlock() {
		super(Blocks.AIR.defaultBlockState(), BlockBehaviour.Properties.of().sound(SoundType.BONE_BLOCK).strength(0.8f, 8f).dynamicShape());
	}

	@Override
	public float getExplosionResistance() {
		return 8f;
	}
}
