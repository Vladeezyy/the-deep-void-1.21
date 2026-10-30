
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.sombrius.thedeepvoid.procedures.InfestedRottenRootsTickProcedure;

public class InfestedRottenRootsBlock extends Block {
	public InfestedRottenRootsBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_GRAY).sound(SoundType.BASALT).strength(2.2f, 16f).randomTicks());
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}

	@Override
	public void randomTick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		super.randomTick(blockstate, world, pos, random);
		InfestedRottenRootsTickProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}
