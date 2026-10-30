
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class ChiseledCutRottenBoneBlockBlock extends Block {
	public ChiseledCutRottenBoneBlockBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).sound(SoundType.BONE_BLOCK).strength(2.2f, 16f).requiresCorrectToolForDrops());
	}

	@Override
	public int getLightDampening(BlockState state) {
		return 15;
	}
}
