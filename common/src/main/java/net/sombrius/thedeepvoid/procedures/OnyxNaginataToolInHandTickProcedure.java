package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.ResourceLocation;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class OnyxNaginataToolInHandTickProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "2b644960-a218-4287-927a-2e21362ba9a0")) != null)) {
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE)
					.hasModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "2b644960-a218-4287-927a-2e21362ba9a0"))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE).addTransientModifier(
						(new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "2b644960-a218-4287-927a-2e21362ba9a0"), ((double) DeepVoidConfigConfiguration.ONYXNAGINATAREACHADDITION.get()), AttributeModifier.Operation.ADD_VALUE)));
		}
		TheDeepVoidMod.queueServerWork(20, () -> {
			if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ONYX_NAGINATA.get())
					&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "2b644960-a218-4287-927a-2e21362ba9a0")) != null) {
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE).removeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "2b644960-a218-4287-927a-2e21362ba9a0"));
			}
		});
	}
}
