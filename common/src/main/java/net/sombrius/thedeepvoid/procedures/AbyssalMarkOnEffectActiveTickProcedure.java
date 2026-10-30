package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;

public class AbyssalMarkOnEffectActiveTickProcedure {
	public static void execute(Entity entity, double amplifier) {
		if (entity == null)
			return;
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("abyssalMarkLevel") != amplifier) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("abyssalMarkLevel", amplifier);
		}
	}
}
