
package net.sombrius.thedeepvoid.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.OnlyIn;
import net.sombrius.thedeepvoid.init.TheDeepVoidModFluids;
import net.sombrius.thedeepvoid.procedures.LiquidVoidBeforeReplacingABlockProcedure;
import net.sombrius.thedeepvoid.procedures.LiquidVoidOnTickUpdateProcedure;

public class LiquidVoidBlock extends LiquidBlock {
	public LiquidVoidBlock() {
		super(TheDeepVoidModFluids.LIQUID_VOID.get(), net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().mapColor(MapColor.COLOR_BLACK).strength(100f).noCollision().noLootTable().liquid().pushReaction(PushReaction.POPPED).sound(SoundType.EMPTY).replaceable());
	}

	@Override
	public int getLightDampening(BlockState state) {
		return 15;
	}

	@Override
	public void neighborChanged(BlockState blockstate, Level world, BlockPos pos, Block neighborBlock, @Nullable net.minecraft.world.level.redstone.Orientation orientation, boolean moving) {
		super.neighborChanged(blockstate, world, pos, neighborBlock, orientation, moving);
		BlockPos fromPos = pos;
		LiquidVoidBeforeReplacingABlockProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
	public void animateTick(BlockState blockstate, Level world, BlockPos pos, RandomSource random) {
		super.animateTick(blockstate, world, pos, random);
		LiquidVoidOnTickUpdateProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}
