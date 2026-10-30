
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class VoidlightBricksBlock extends Block {
	public VoidlightBricksBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().sound(SoundType.SHROOMLIGHT).strength(1f, 2f).lightLevel(s -> 6));
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}
}
