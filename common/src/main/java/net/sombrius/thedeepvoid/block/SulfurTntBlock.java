
package net.sombrius.thedeepvoid.block;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.sombrius.thedeepvoid.procedures.SulfurTntBlockChangesProcedure;
import net.sombrius.thedeepvoid.procedures.SulfurTntBlockDestroyedByExplosionProcedure;
import net.sombrius.thedeepvoid.procedures.SulfurTntRedstoneProcedure;

public class SulfurTntBlock extends Block {
	public SulfurTntBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().sound(SoundType.GRASS).instabreak());
	}

	@Override
	public int getLightDampening(BlockState state) {
		return 15;
	}

	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 15;
	}

	@Override
	public void neighborChanged(BlockState blockstate, Level world, BlockPos pos, Block neighborBlock, @Nullable net.minecraft.world.level.redstone.Orientation orientation, boolean moving) {
		super.neighborChanged(blockstate, world, pos, neighborBlock, orientation, moving);
		BlockPos fromPos = pos;
		if (world.getBestNeighborSignal(pos) > 0) {
			SulfurTntRedstoneProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
		}
		SulfurTntBlockChangesProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public void wasExploded(ServerLevel world, BlockPos pos, Explosion e) {
		super.wasExploded(world, pos, e);
		SulfurTntBlockDestroyedByExplosionProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}
