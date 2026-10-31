
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TwistingSpineBlock extends SugarCaneBlock {
	public TwistingSpineBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().mapColor(MapColor.COLOR_LIGHT_GRAY).randomTicks().sound(SoundType.BONE_BLOCK).strength(0.8f, 1f).noCollision().offsetType(BlockBehaviour.OffsetType.NONE).pushReaction(PushReaction.POPPED));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		Vec3 offset = state.getOffset(pos);
		return box(6, 0, 6, 10, 16, 10).move(offset.x, offset.y, offset.z);
	}


	@Override
	public void randomTick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		if (world.isEmptyBlock(pos.above())) {
			int i = 1;
			for (; world.getBlockState(pos.below(i)).is(this); ++i);
			if (i < 14) {
				int j = blockstate.getValue(AGE);
				if (net.sombrius.thedeepvoid.port.platform.Platform.INSTANCE.canCropGrow(world, pos, blockstate, true)) {
					if (j == 15) {
						world.setBlockAndUpdate(pos.above(), defaultBlockState());
						net.sombrius.thedeepvoid.port.platform.Platform.INSTANCE.fireCropGrowPost(world, pos.above(), defaultBlockState());
						world.setBlock(pos, blockstate.setValue(AGE, 0), 4);
					} else {
						world.setBlock(pos, blockstate.setValue(AGE, j + 1), 4);
					}
				}
			}
		}
	}
}
