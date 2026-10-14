package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.sombrius.thedeepvoid.entity.RideableEyekinEntity;
import net.sombrius.thedeepvoid.entity.RideableFlyingEyekinEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

@EventBusSubscriber
public class EyekinSaddleRightClickEntityProcedure {
	@SubscribeEvent
	public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
		if (event.getHand() != event.getEntity().getUsedItemHand())
			return;
		execute(event, event.getTarget(), event.getEntity());
	}

	public static void execute(Entity entity, Entity sourceentity) {
		execute(null, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.EYEKIN_SADDLE.get()) {
			if (entity instanceof RideableEyekinEntity || entity instanceof RideableFlyingEyekinEntity) {
				if (entity instanceof TamableAnimal _tamIsTamedBy && sourceentity instanceof LivingEntity _livEnt ? _tamIsTamedBy.isOwnedBy(_livEnt) : false) {
					if (!entity.level().isClientSide())
						entity.discard();
					{
						final String _tagName = "deep_void:eyekinSummoned";
						final boolean _tagValue = false;
						CustomData.update(DataComponents.CUSTOM_DATA, (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
					}
				}
			}
		}
	}
}
