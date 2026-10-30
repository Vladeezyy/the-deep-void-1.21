package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.ResourceLocation;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class BrokenArmorEffectExpiresProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR).removeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "9cceef6b-390b-4032-9fad-e970a9faebbd"));
		((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR_TOUGHNESS).removeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "746632dc-c636-4bc1-9a2f-1c5f33da9aa1"));
	}
}
