
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class AncientDeepslateSlabBlock extends SlabBlock {
	public AncientDeepslateSlabBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE).strength(4.5f, 7.5f).requiresCorrectToolForDrops().dynamicShape());
	}
}
