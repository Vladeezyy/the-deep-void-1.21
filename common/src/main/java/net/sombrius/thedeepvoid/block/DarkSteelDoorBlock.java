
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class DarkSteelDoorBlock extends DoorBlock {
	public DarkSteelDoorBlock() {
		super(BlockSetType.IRON, net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().sound(SoundType.METAL).strength(20f, 1200f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false).dynamicShape());
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 0;
	}
}
