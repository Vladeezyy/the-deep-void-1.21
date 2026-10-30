package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.ResourceLocation;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class AdaptedEffectStartedappliedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player) {
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).hasModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "1391819b-b9bf-4411-962d-6467ffc0d952"))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).addTransientModifier((new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "1391819b-b9bf-4411-962d-6467ffc0d952"),
						((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.ADAPTED.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.ADAPTED.holder()).getAmplifier() : 0) / 2), AttributeModifier.Operation.ADD_VALUE)));
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).hasModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "04216f37-84d0-49cb-8a00-a6449067cbe4"))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).addTransientModifier((new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "04216f37-84d0-49cb-8a00-a6449067cbe4"),
						((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.ADAPTED.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.ADAPTED.holder()).getAmplifier() : 0) / 10), AttributeModifier.Operation.ADD_VALUE)));
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).hasModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "3ba7ef6a-16bb-4071-8921-925675742209"))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)
						.addTransientModifier((new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "3ba7ef6a-16bb-4071-8921-925675742209"),
								((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.ADAPTED.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.ADAPTED.holder()).getAmplifier() : 0) / 5),
								AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)));
		}
	}
}
