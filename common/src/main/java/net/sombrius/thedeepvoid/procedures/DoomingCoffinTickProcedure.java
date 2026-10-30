package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.sombrius.thedeepvoid.entity.DoomingCoffinEntity;

public class DoomingCoffinTickProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof DoomingCoffinEntity _datEntSetI)
			_datEntSetI.getEntityData().set(DoomingCoffinEntity.DATA_despawn, (int) ((entity instanceof DoomingCoffinEntity _datEntI ? _datEntI.getEntityData().get(DoomingCoffinEntity.DATA_despawn) : 0) + 1));
		if ((entity instanceof DoomingCoffinEntity _datEntI ? _datEntI.getEntityData().get(DoomingCoffinEntity.DATA_despawn) : 0) == 200) {
			if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBoolean("deep_void:despawning") == false) {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("deep_void:despawning", true);
				if (entity instanceof DoomingCoffinEntity) {
					((DoomingCoffinEntity) entity).setAnimation("animation.coffin_despawn");
				}
			}
		} else if ((entity instanceof DoomingCoffinEntity _datEntI ? _datEntI.getEntityData().get(DoomingCoffinEntity.DATA_despawn) : 0) >= 220) {
			if (!entity.level().isClientSide())
				entity.discard();
		}
	}
}
