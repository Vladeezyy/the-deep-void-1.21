
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.checkerframework.checker.units.qual.s;

public class VoidlightBrickStairsBlock extends StairBlock {
	public VoidlightBrickStairsBlock() {
		super(Blocks.AIR.defaultBlockState(), BlockBehaviour.Properties.of().sound(SoundType.SHROOMLIGHT).strength(1f, 2f).lightLevel(s -> 6).dynamicShape());
	}

	@Override
	public float getExplosionResistance() {
		return 2f;
	}
}
