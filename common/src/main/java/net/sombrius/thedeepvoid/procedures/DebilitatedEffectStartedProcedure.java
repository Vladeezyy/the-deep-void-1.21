package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class DebilitatedEffectStartedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("debiliBaseArmor", ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR).getValue());
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("debiliBaseArmorTough", ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR_TOUGHNESS).getValue());
	}
}
