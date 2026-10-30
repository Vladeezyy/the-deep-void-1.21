package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.ResourceLocation;

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

public class RottenTongueLivingEntityIsHitWithToolProcedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("block") == 1) {
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)
					.hasModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "fc5078cc-59fd-4dba-8fa6-4617649b3c1d"))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)
						.addTransientModifier((new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "fc5078cc-59fd-4dba-8fa6-4617649b3c1d"), (-0.4), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)));
		} else if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "fc5078cc-59fd-4dba-8fa6-4617649b3c1d")) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).removeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "fc5078cc-59fd-4dba-8fa6-4617649b3c1d"));
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= 5
				&& !(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "2b844396-8d38-433c-a9a3-15b2ff4183f4")) != null)) {
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE)
					.hasModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "2b844396-8d38-433c-a9a3-15b2ff4183f4"))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).addTransientModifier(
						(new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "2b844396-8d38-433c-a9a3-15b2ff4183f4"), ((double) DeepVoidConfigConfiguration.ROTTENTONGUEDAMAGE.get()), AttributeModifier.Operation.ADD_VALUE)));
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > 5
				&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "2b844396-8d38-433c-a9a3-15b2ff4183f4")) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "2b844396-8d38-433c-a9a3-15b2ff4183f4"));
		}
		if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "2b844396-8d38-433c-a9a3-15b2ff4183f4")) != null) {
			TheDeepVoidMod.queueServerWork(20, () -> {
				if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ROT_TONGUE.get())
						&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "2b844396-8d38-433c-a9a3-15b2ff4183f4")) != null) {
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "2b844396-8d38-433c-a9a3-15b2ff4183f4"));
				}
			});
		}
	}
}
