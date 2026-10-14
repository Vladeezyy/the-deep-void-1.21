package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.sombrius.thedeepvoid.entity.EverhungerEntity;

public class EverhungerThisEntityKillsAnotherOneProcedure {
	public static void execute(Entity sourceentity) {
		if (sourceentity == null)
			return;
		if (sourceentity instanceof EverhungerEntity _datEntSetL)
			_datEntSetL.getEntityData().set(EverhungerEntity.DATA_alert, false);
	}
}
