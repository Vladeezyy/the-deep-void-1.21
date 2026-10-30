package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.sombrius.thedeepvoid.entity.StalkerEntity;

public class DistractedOnEffectActiveTickProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof StalkerEntity) {
			if (entity instanceof Mob _entity)
				_entity.getNavigation().moveTo((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("deep_void:xTreat")), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("deep_void:yTreat")), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("deep_void:zTreat")), 1.1);
		}
	}
}
