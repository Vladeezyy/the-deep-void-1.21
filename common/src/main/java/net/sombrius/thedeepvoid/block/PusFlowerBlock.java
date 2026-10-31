
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;

public class PusFlowerBlock extends FlowerBlock {
	public PusFlowerBlock() {
		super(MobEffects.SPEED, 100, net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().mapColor(MapColor.NETHER).sound(SoundType.SCULK_VEIN).instabreak().noCollision().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.POPPED));
	}

	@Override
	public boolean mayPlaceOn(BlockState groundState, BlockGetter worldIn, BlockPos pos) {
		return groundState.is(TheDeepVoidModBlocks.FLESH_BLOCK.get()) || groundState.is(TheDeepVoidModBlocks.PUSTULENT_FLESH_BLOCK.get()) || groundState.is(TheDeepVoidModBlocks.EYE_FLESH_BLOCK.get())
				|| groundState.is(TheDeepVoidModBlocks.AMBROSIAL_FLESH_BLOCK.get()) || groundState.is(TheDeepVoidModBlocks.POROUS_FLESH.get()) || groundState.is(TheDeepVoidModBlocks.GAPING_POROUS_FLESH.get())
				|| groundState.is(TheDeepVoidModBlocks.MESSY_FLESH.get()) || groundState.is(TheDeepVoidModBlocks.ACTIVE_FLESH_BLOCK.get());
	}

	@Override
	public boolean canSurvive(BlockState blockstate, LevelReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		BlockState groundState = worldIn.getBlockState(blockpos);
		return this.mayPlaceOn(groundState, worldIn, blockpos);
	}
}
