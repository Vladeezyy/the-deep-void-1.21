package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.Identifier;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class IchorGreatswordItemInHandTickProcedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("block", 0) == 1) {
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)
					.hasModifier(Identifier.fromNamespaceAndPath("the_deep_void", "b1079042-106a-4a8f-b930-8ee4313ca118"))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)
						.addTransientModifier((new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "b1079042-106a-4a8f-b930-8ee4313ca118"), (-0.3), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)));
		} else if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(Identifier.fromNamespaceAndPath("the_deep_void", "b1079042-106a-4a8f-b930-8ee4313ca118")) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).removeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "b1079042-106a-4a8f-b930-8ee4313ca118"));
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= 6
				&& !(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(Identifier.fromNamespaceAndPath("the_deep_void", "1d2f1ec2-2ca0-4064-8370-f6792a310918")) != null)) {
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE)
					.hasModifier(Identifier.fromNamespaceAndPath("the_deep_void", "1d2f1ec2-2ca0-4064-8370-f6792a310918"))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).addTransientModifier(
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "1d2f1ec2-2ca0-4064-8370-f6792a310918"), ((double) DeepVoidConfigConfiguration.ROTTENTONGUEDAMAGE.get() * 1.2), AttributeModifier.Operation.ADD_VALUE)));
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > 6
				&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(Identifier.fromNamespaceAndPath("the_deep_void", "1d2f1ec2-2ca0-4064-8370-f6792a310918")) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "1d2f1ec2-2ca0-4064-8370-f6792a310918"));
		}
		if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(Identifier.fromNamespaceAndPath("the_deep_void", "2b844396-8d38-433c-a9a3-15b2ff4183f4")) != null) {
			TheDeepVoidMod.queueServerWork(20, () -> {
				if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ICHOR_GREATSWORD.get())
						&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(Identifier.fromNamespaceAndPath("the_deep_void", "1d2f1ec2-2ca0-4064-8370-f6792a310918")) != null) {
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "1d2f1ec2-2ca0-4064-8370-f6792a310918"));
				}
			});
		}
	}
}
