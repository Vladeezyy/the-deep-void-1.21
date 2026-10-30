package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.Identifier;

import java.util.UUID;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class TheCatcherLivingEntityIsHitWithItemProcedure {
	public static void execute(Entity sourceentity, ItemStack itemstack) {
		if (sourceentity == null)
			return;
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("state", 0) == 1) {
			{
				final String _tagName = "state";
				final double _tagValue = 0;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
			}
			if (((LivingEntity) sourceentity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(Identifier.fromNamespaceAndPath("the_deep_void", "681aea3b-26bb-484f-8f6a-fa1c1fac1fa7")) != null) {
				((LivingEntity) sourceentity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "681aea3b-26bb-484f-8f6a-fa1c1fac1fa7"));
			}
		}
	}
}
