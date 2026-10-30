
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class BlockOfBoneBlock extends Block {
	public BlockOfBoneBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.BONE_BLOCK).strength(0.8f, 8f).speedFactor(0.7f).jumpFactor(0.9f));
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}
}
