package net.sombrius.thedeepvoid.procedures;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class CrossToolInHandTickProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE).getModifier(UUID.fromString("25077204-40c9-4acd-ae7d-9f396bbbd614")) != null)) {
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE).hasModifier(
					(new AttributeModifier(UUID.fromString("25077204-40c9-4acd-ae7d-9f396bbbd614"), "penitentCross_reach", ((double) DeepVoidConfigConfiguration.PENITENTCROSSREACHADDITION.get()), AttributeModifier.Operation.ADD_VALUE)))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE).addTransientModifier(
						(new AttributeModifier(UUID.fromString("25077204-40c9-4acd-ae7d-9f396bbbd614"), "penitentCross_reach", ((double) DeepVoidConfigConfiguration.PENITENTCROSSREACHADDITION.get()), AttributeModifier.Operation.ADD_VALUE)));
		}
		if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_KNOCKBACK).getModifier(UUID.fromString("cd21e4b9-a572-464f-b715-eb4e5081851a")) != null)) {
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_KNOCKBACK)
					.hasModifier((new AttributeModifier(UUID.fromString("cd21e4b9-a572-464f-b715-eb4e5081851a"), "penitentCross_knockback", 1.3, AttributeModifier.Operation.ADD_VALUE)))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_KNOCKBACK)
						.addTransientModifier((new AttributeModifier(UUID.fromString("cd21e4b9-a572-464f-b715-eb4e5081851a"), "penitentCross_knockback", 1.3, AttributeModifier.Operation.ADD_VALUE)));
		}
		TheDeepVoidMod.queueServerWork(20, () -> {
			if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PENITENT_CROSS.get())) {
				if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE).getModifier(UUID.fromString("25077204-40c9-4acd-ae7d-9f396bbbd614")) != null) {
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE).removeModifier(UUID.fromString("25077204-40c9-4acd-ae7d-9f396bbbd614"));
				}
				if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_KNOCKBACK).getModifier(UUID.fromString("cd21e4b9-a572-464f-b715-eb4e5081851a")) != null) {
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_KNOCKBACK).removeModifier(UUID.fromString("cd21e4b9-a572-464f-b715-eb4e5081851a"));
				}
			}
		});
	}
}
