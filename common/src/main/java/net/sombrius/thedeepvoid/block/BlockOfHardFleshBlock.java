
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BlockOfHardFleshBlock extends Block {
	public BlockOfHardFleshBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().sound(SoundType.COPPER).strength(10f, 8f).friction(0.7f));
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}
}
