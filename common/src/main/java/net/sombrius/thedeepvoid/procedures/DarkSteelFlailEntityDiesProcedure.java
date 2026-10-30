package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;

public class DarkSteelFlailEntityDiesProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof TamableAnimal _tamEnt ? _tamEnt.isTame() : false) {
			if ((entity instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) instanceof Player) {
				net.sombrius.thedeepvoid.port.PortUtil.data((entity instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null)).putDouble("flailUsed", 0);
			}
		}
	}
}
