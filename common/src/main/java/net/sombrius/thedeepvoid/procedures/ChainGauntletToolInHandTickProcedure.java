package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.ResourceLocation;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ChainGauntletToolInHandTickProcedure {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if ((entity instanceof Player _plrCldRem1 ? _plrCldRem1.getCooldowns().getCooldownPercent(itemstack.getItem(), 0f) * 100 : 0) <= 0
				&& !(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "01387b5f-4dbd-4d94-b869-7f35d57d896e")) != null)) {
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE)
					.hasModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "01387b5f-4dbd-4d94-b869-7f35d57d896e"))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE)
						.addTransientModifier((new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "01387b5f-4dbd-4d94-b869-7f35d57d896e"), 12, AttributeModifier.Operation.ADD_VALUE)));
		} else if ((entity instanceof Player _plrCldRem6 ? _plrCldRem6.getCooldowns().getCooldownPercent(itemstack.getItem(), 0f) * 100 : 0) > 0
				&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "01387b5f-4dbd-4d94-b869-7f35d57d896e")) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE).removeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "01387b5f-4dbd-4d94-b869-7f35d57d896e"));
		}
	}
}
