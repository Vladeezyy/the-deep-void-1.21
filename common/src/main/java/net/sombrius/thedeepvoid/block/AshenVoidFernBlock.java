
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;

public class AshenVoidFernBlock extends FlowerBlock {
	public AshenVoidFernBlock() {
		super(MobEffects.SPEED, 100,
				net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().mapColor(MapColor.COLOR_BLACK).sound(SoundType.TWISTING_VINES).strength(0.5f, 0.5f).noCollision().replaceable().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.POPPED));
	}

	@Override
	public boolean mayPlaceOn(BlockState groundState, BlockGetter worldIn, BlockPos pos) {
		return groundState.is(TheDeepVoidModBlocks.ANCIENT_DEEPSLATE.get()) || groundState.is(Blocks.COBBLED_DEEPSLATE) || groundState.is(Blocks.DEEPSLATE) || groundState.is(TheDeepVoidModBlocks.BLOCK_OF_BONE_PILE.get())
				|| groundState.is(TheDeepVoidModBlocks.MOSSY_BONE_PILE.get()) || groundState.is(TheDeepVoidModBlocks.ASH_BLOCK.get()) || groundState.is(TheDeepVoidModBlocks.VOID_BLOSSOM.get());
	}

	@Override
	public boolean canSurvive(BlockState blockstate, LevelReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		BlockState groundState = worldIn.getBlockState(blockpos);
		return this.mayPlaceOn(groundState, worldIn, blockpos);
	}

}
