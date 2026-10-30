package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AncientDeepslateSawTrapOnTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (new Object() {
			public double getValue(LevelAccessor world, BlockPos pos, String tag) {
				BlockEntity blockEntity = world.getBlockEntity(pos);
				if (blockEntity != null)
					return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
				return -1;
			}
		}.getValue(world, BlockPos.containing(x, y, z), "cooldown") > 0) {
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
					net.sombrius.thedeepvoid.port.PortUtil.data(_blockEntity).putDouble("cooldown", ((new Object() {
						public double getValue(LevelAccessor world, BlockPos pos, String tag) {
							BlockEntity blockEntity = world.getBlockEntity(pos);
							if (blockEntity != null)
								return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
							return -1;
						}
					}.getValue(world, BlockPos.containing(x, y, z), "cooldown")) - 1));
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
		}
	}
}
