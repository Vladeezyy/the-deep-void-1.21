
package net.sombrius.thedeepvoid.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;
import net.sombrius.thedeepvoid.procedures.CorpseNeighbourBlockChangesProcedure;
import net.sombrius.thedeepvoid.procedures.CorpseSpawnPlantDestroyedByPlayerProcedure;

public class CoveredCorpseSpawnBlock extends FlowerBlock implements net.sombrius.thedeepvoid.port.PortBlockHooks.DestroyedByPlayer {
	public CoveredCorpseSpawnBlock() {
		super(MobEffects.SPEED, 100, net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().mapColor(MapColor.PLANT).sound(SoundType.BONE_BLOCK).strength(0.6f, 1f).noCollision().offsetType(BlockBehaviour.OffsetType.NONE).pushReaction(PushReaction.POPPED));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		Vec3 offset = state.getOffset(pos);
		return box(0, 0, 0, 16, 16, 16).move(offset.x, offset.y, offset.z);
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state, boolean includeData) {
		return new ItemStack(TheDeepVoidModBlocks.OLD_COVERED_CORPSE.get());
	}

	@Override
	public boolean mayPlaceOn(BlockState groundState, BlockGetter worldIn, BlockPos pos) {
		return groundState.is(TheDeepVoidModBlocks.BLOCK_OF_BONE_PILE.get()) || groundState.is(TheDeepVoidModBlocks.ASH_BLOCK.get()) || groundState.is(TheDeepVoidModBlocks.BLOCK_OF_SKULL_PILE.get());
	}

	@Override
	public boolean canSurvive(BlockState blockstate, LevelReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		BlockState groundState = worldIn.getBlockState(blockpos);
		return this.mayPlaceOn(groundState, worldIn, blockpos);
	}


	@Override
	public void neighborChanged(BlockState blockstate, Level world, BlockPos pos, Block neighborBlock, @Nullable net.minecraft.world.level.redstone.Orientation orientation, boolean moving) {
		super.neighborChanged(blockstate, world, pos, neighborBlock, orientation, moving);
		BlockPos fromPos = pos;
		CorpseNeighbourBlockChangesProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}

	public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
		boolean retval = net.sombrius.thedeepvoid.port.PortUtil.destroyedByPlayer(world, pos, fluid);
		CorpseSpawnPlantDestroyedByPlayerProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
		return retval;
	}
}
