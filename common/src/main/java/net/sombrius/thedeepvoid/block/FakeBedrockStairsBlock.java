
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

public class FakeBedrockStairsBlock extends StairBlock {
	public FakeBedrockStairsBlock() {
		super(Blocks.AIR.defaultBlockState(), net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(70f, 1200f).requiresCorrectToolForDrops().pushReaction(PushReaction.IMMOVEABLE).dynamicShape());
	}

	@Override
	public float getExplosionResistance() {
		return 1200f;
	}
}
