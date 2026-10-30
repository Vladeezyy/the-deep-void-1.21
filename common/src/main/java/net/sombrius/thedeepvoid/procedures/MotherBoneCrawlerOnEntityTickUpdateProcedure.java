package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;

public class MotherBoneCrawlerOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:eggMax", 0) >= 3)) {
			if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:lay_egg", 0) >= 400) {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:lay_egg", 0);
				if (Math.random() < 0.4) {
					if (!world.getBlockState(BlockPos.containing(x, y, z)).canOcclude()) {
						net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:eggMax", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:eggMax", 0) + 1));
						world.setBlock(BlockPos.containing(x, y, z), TheDeepVoidModBlocks.BONE_CRAWLER_EGG.get().defaultBlockState(), 3);
					}
				}
			} else {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:lay_egg", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:lay_egg", 0) + 1));
			}
		}
	}
}
