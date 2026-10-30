
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.sombrius.thedeepvoid.procedures.CinnabarCrateBlockDestroyedByPlayerProcedure;

public class CinnabarCrateBlock extends Block implements net.sombrius.thedeepvoid.port.PortBlockHooks.DestroyedByPlayer {
	public CinnabarCrateBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.BONE_BLOCK).strength(0.2f, 16f));
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}

	public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
		boolean retval = net.sombrius.thedeepvoid.port.PortUtil.destroyedByPlayer(world, pos, fluid);
		CinnabarCrateBlockDestroyedByPlayerProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), entity);
		return retval;
	}
}
