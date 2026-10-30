package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.entity.StalkerEntity;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class CallOfTheVoidEffectExpiresProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!world.getEntitiesOfClass(StalkerEntity.class, AABB.ofSize(new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), 350, 350, 350), e -> true).isEmpty() && entity.onGround()) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.playerCallOfTheVoid = true;
				_vars.syncPlayerVariables(entity);
			}
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:xDig", (entity.getX()));
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:yDig", (entity.getY()));
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:zDig", (entity.getZ()));
		}
	}
}
