
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

public class VoidCoreBlock extends Block {
	public VoidCoreBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.LODESTONE).strength(2.5f, 10f).pushReaction(PushReaction.IMMOVEABLE));
	}

	@Override
	public int getLightDampening(BlockState state) {
		return 15;
	}
}
