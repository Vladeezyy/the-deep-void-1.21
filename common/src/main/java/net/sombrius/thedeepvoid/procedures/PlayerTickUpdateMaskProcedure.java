package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.ResourceLocation;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class PlayerTickUpdateMaskProcedure {
	private static final ResourceLocation ID_A2A1826B_7829_42ED_986E_984EBF45427B = ResourceLocation.fromNamespaceAndPath("the_deep_void", "a2a1826b-7829-42ed-986e-984ebf45427b");

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (TheDeepVoidModVariables.of(entity).healthBoostLevel > 0) {
			if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.WEAVER_MASK_HELMET.get())) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.healthBoostLevel = 0;
					_vars.syncPlayerVariables(entity);
				}
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).removeModifier(ID_A2A1826B_7829_42ED_986E_984EBF45427B);
			}
		}
		if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_HELMET.get())
				&& net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBoolean("bloodstoneEquipHelmet") == true) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("bloodstoneEquipHelmet", false);
		}
		if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_CHESTPLATE.get())
				&& net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBoolean("bloodstoneEquipChest") == true) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("bloodstoneEquipChest", false);
		}
		if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_LEGGINGS.get())
				&& net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBoolean("bloodstoneEquipLeggings") == true) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("bloodstoneEquipLeggings", false);
		}
		if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_BOOTS.get())
				&& net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBoolean("bloodstoneEquipBoots") == true) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("bloodstoneEquipBoots", false);
		}
	}
}
