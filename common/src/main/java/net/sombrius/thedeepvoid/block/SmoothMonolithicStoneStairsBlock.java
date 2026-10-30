
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class SmoothMonolithicStoneStairsBlock extends StairBlock {
	public SmoothMonolithicStoneStairsBlock() {
		super(Blocks.AIR.defaultBlockState(), net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(5f, 8f).requiresCorrectToolForDrops().dynamicShape());
	}

	@Override
	public float getExplosionResistance() {
		return 8f;
	}
}
