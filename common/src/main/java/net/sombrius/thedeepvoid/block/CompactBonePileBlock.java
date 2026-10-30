
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CompactBonePileBlock extends Block {
	public CompactBonePileBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().sound(SoundType.BONE_BLOCK).strength(0.8f, 8f));
	}

	@Override
	public int getLightDampening(BlockState state) {
		return 15;
	}
}
