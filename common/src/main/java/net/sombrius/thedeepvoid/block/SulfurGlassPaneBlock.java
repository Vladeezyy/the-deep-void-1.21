
package net.sombrius.thedeepvoid.block;

import net.minecraft.util.ARGB;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class SulfurGlassPaneBlock extends IronBarsBlock {
	public SulfurGlassPaneBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).strength(2f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	public Integer getBeaconColorMultiplier(BlockState state, LevelReader world, BlockPos pos, BlockPos beaconPos) {
		return ARGB.colorFromFloat(1f, 0.6901960784f, 0.5450980392f, 0f);
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 0;
	}
}
