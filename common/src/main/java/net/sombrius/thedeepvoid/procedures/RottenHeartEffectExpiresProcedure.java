package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.Identifier;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class RottenHeartEffectExpiresProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).removeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "92b763d9-4eaf-423a-b147-1f7ea4ce6867"));
	}
}
