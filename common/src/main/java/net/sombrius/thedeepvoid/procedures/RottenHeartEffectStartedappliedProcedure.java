package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.ResourceLocation;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class RottenHeartEffectStartedappliedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)
				.hasModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "92b763d9-4eaf-423a-b147-1f7ea4ce6867"))))
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)
					.addTransientModifier((new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "92b763d9-4eaf-423a-b147-1f7ea4ce6867"),
							(0.2 * (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.ROTTEN_HEART.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.ROTTEN_HEART.holder()).getAmplifier() : 0)),
							AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)));
	}
}
