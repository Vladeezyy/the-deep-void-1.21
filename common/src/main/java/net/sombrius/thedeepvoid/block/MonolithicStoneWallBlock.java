
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class MonolithicStoneWallBlock extends WallBlock {
	public MonolithicStoneWallBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.NETHER_BRICKS).strength(5f, 8f).requiresCorrectToolForDrops().dynamicShape().forceSolidOn());
	}
}
