
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class HardFleshStairsBlock extends StairBlock {
	public HardFleshStairsBlock() {
		super(Blocks.AIR.defaultBlockState(), net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().sound(SoundType.PACKED_MUD).strength(10f, 8f).friction(0.7f).dynamicShape());
	}

	@Override
	public float getExplosionResistance() {
		return 8f;
	}
}
