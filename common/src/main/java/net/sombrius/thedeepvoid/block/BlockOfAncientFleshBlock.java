
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BlockOfAncientFleshBlock extends Block {
	public BlockOfAncientFleshBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().sound(SoundType.NETHERRACK).strength(10f, 8f).friction(0.7f));
	}

	@Override
	public int getLightDampening(BlockState state) {
		return 15;
	}
}
