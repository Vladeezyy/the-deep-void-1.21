package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;

public class HookProjectileHitsBlockProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof ServerLevel _serverLevel) {
			Entity entityinstance = TheDeepVoidModEntities.HOOK_END.get().create(_serverLevel, null, BlockPos.containing(x, y, z), EntitySpawnReason.MOB_SUMMONED, false, false);
			if (entityinstance != null) {
				entityinstance.setYRot(world.getRandom().nextFloat() * 360.0F);
				net.sombrius.thedeepvoid.port.PortUtil.data(entityinstance).putString("owner", (entity.getStringUUID()));
				_serverLevel.addFreshEntity(entityinstance);
			}
		}
	}
}
