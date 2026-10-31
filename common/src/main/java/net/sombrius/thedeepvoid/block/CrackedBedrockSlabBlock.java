
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

public class CrackedBedrockSlabBlock extends SlabBlock {
	public CrackedBedrockSlabBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(70f, 1200f).requiresCorrectToolForDrops().pushReaction(PushReaction.IMMOVEABLE).dynamicShape());
	}
}
