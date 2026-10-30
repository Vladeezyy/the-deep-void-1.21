
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class PolishedLavenditeSlabBlock extends SlabBlock {
	public PolishedLavenditeSlabBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.AMETHYST).strength(1.8f, 3f).dynamicShape());
	}
}
