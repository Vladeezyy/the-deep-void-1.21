
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
import net.sombrius.thedeepvoid.procedures.DeepslateLavaGeyserOnTickUpdateProcedure;

public class DeepslateLavaGeyserBlock extends Block {
	public DeepslateLavaGeyserBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.DEEPSLATE).sound(SoundType.DEEPSLATE).strength(3f, 6f).requiresCorrectToolForDrops().randomTicks());
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}

	@Override
	public void randomTick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		super.randomTick(blockstate, world, pos, random);
		DeepslateLavaGeyserOnTickUpdateProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}
