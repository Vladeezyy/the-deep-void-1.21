
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
import net.minecraft.world.level.material.MapColor;
import net.sombrius.thedeepvoid.procedures.DarkSteelDepositAdditionalHarvestConditionProcedure;
import net.sombrius.thedeepvoid.procedures.OnyxOreBlockDestroyedByPlayerProcedure;

public class DarkSteelOreBlock extends Block implements net.sombrius.thedeepvoid.port.PortBlockHooks.DestroyedByPlayer {
	public DarkSteelOreBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.TERRACOTTA_BLACK).sound(SoundType.NETHER_ORE).strength(30f, 1200f).requiresCorrectToolForDrops());
	}

	@Override
	public int getLightDampening(BlockState state) {
		return 15;
	}

		public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
		return player.hasCorrectToolForDrops(state) && DarkSteelDepositAdditionalHarvestConditionProcedure.execute(player);
	}

	public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
		boolean retval = net.sombrius.thedeepvoid.port.PortUtil.destroyedByPlayer(world, pos, fluid);
		OnyxOreBlockDestroyedByPlayerProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), entity);
		return retval;
	}
}
