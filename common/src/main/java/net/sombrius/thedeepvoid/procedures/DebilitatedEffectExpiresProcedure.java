package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.Identifier;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class DebilitatedEffectExpiresProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR).removeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "b62dcadb-d9b4-46da-b37c-6378ea720d21"));
		((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR_TOUGHNESS).removeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "d747b3a8-1b6a-48bb-a3fe-d9de22cb7fc8"));
	}
}
