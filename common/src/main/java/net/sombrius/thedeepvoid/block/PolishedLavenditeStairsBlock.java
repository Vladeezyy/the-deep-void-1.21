
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class PolishedLavenditeStairsBlock extends StairBlock {
	public PolishedLavenditeStairsBlock() {
		super(Blocks.AIR.defaultBlockState(), net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.AMETHYST).strength(1.8f, 3f).dynamicShape());
	}

	@Override
	public float getExplosionResistance() {
		return 3f;
	}
}
