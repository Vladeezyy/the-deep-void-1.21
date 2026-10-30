package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;

public class BoneCageEntityDiesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!entity.level().isClientSide())
			entity.discard();
		world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(TheDeepVoidModBlocks.ROTTEN_BONE_BLOCK.get().defaultBlockState()));
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).getBooleanOr("bone_cage_trapped", false) == true) {
					net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).putBoolean("bone_cage_trapped", false);
				}
			}
		}
	}
}
