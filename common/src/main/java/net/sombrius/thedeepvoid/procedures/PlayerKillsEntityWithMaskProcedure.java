package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.ResourceLocation;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.LivingDeathEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class PlayerKillsEntityWithMaskProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(Entity entity, Entity sourceentity) {
		execute(null, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if ((sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.WEAVER_MASK_HELMET.get()) {
			if (TheDeepVoidModVariables.of(sourceentity).healthBoostLevel < 5 && ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).getValue() > ((LivingEntity) sourceentity)
					.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).getValue()) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(sourceentity);
					_vars.healthBoostLevel = TheDeepVoidModVariables.of(sourceentity).healthBoostLevel + 1;
					_vars.syncPlayerVariables(sourceentity);
				}
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(sourceentity);
					_vars.healthMask = sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1;
					_vars.syncPlayerVariables(sourceentity);
				}
				((LivingEntity) sourceentity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).removeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "a2a1826b-7829-42ed-986e-984ebf45427b"));
				if (!(((LivingEntity) sourceentity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).hasModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "a2a1826b-7829-42ed-986e-984ebf45427b"))))
					((LivingEntity) sourceentity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).addTransientModifier((new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "a2a1826b-7829-42ed-986e-984ebf45427b"),
							(4 * TheDeepVoidModVariables.of(sourceentity).healthBoostLevel), AttributeModifier.Operation.ADD_VALUE)));
				if (sourceentity instanceof LivingEntity _entity)
					_entity.setHealth((float) TheDeepVoidModVariables.of(sourceentity).healthMask);
			} else {
				if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 160, 0, false, false));
				if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 160, 1, false, false));
				if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 160, 1, false, false));
			}
		}
		if (TheDeepVoidModVariables.of(entity).healthBoostLevel > 0) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).removeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "a2a1826b-7829-42ed-986e-984ebf45427b"));
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.healthBoostLevel = 0;
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}
