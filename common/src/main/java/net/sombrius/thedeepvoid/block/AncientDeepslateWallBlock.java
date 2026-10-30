
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class AncientDeepslateWallBlock extends WallBlock {
	public AncientDeepslateWallBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE).strength(4.5f, 7.5f).requiresCorrectToolForDrops().dynamicShape().forceSolidOn());
	}
}
