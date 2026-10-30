
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class SmoothAncientDeepslatePressurePlateBlock extends PressurePlateBlock {
	public SmoothAncientDeepslatePressurePlateBlock() {
		super(BlockSetType.IRON, net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_TILES).strength(4.5f, 7.5f).requiresCorrectToolForDrops().dynamicShape().forceSolidOn());
	}
}
