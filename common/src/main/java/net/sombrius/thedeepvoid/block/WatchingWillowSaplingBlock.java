
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.sombrius.thedeepvoid.procedures.WatchingWillowSaplingOnTickUpdateProcedure;

public class WatchingWillowSaplingBlock extends FlowerBlock implements BonemealableBlock {
	public WatchingWillowSaplingBlock() {
		super(MobEffects.SPEED, 100, net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().mapColor(MapColor.COLOR_GRAY).randomTicks().sound(SoundType.SCULK).instabreak().noCollision().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.POPPED));
	}


	@Override
	public void randomTick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		WatchingWillowSaplingOnTickUpdateProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState blockstate, net.minecraft.world.level.block.BonemealSource source) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState blockstate, net.minecraft.world.level.block.BonemealSource source) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState blockstate, net.minecraft.world.level.block.BonemealSource source) {
		WatchingWillowSaplingOnTickUpdateProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}
