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
				_entity.getNavigation().moveTo((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xTreat", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yTreat", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zTreat", 0)), 1.1);
		}
	}
}
