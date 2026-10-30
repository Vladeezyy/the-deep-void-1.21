package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.ResourceLocation;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
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
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).healthBoostLevel > 0) {
			if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.WEAVER_MASK_HELMET.get())) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.healthBoostLevel = 0;
					_vars.syncPlayerVariables(entity);
				}
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).removeModifier(ID_A2A1826B_7829_42ED_986E_984EBF45427B);
			}
		}
		if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_HELMET.get())
				&& entity.getPersistentData().getBoolean("bloodstoneEquipHelmet") == true) {
			entity.getPersistentData().putBoolean("bloodstoneEquipHelmet", false);
		}
		if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_CHESTPLATE.get())
				&& entity.getPersistentData().getBoolean("bloodstoneEquipChest") == true) {
			entity.getPersistentData().putBoolean("bloodstoneEquipChest", false);
		}
		if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_LEGGINGS.get())
				&& entity.getPersistentData().getBoolean("bloodstoneEquipLeggings") == true) {
			entity.getPersistentData().putBoolean("bloodstoneEquipLeggings", false);
		}
		if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_BOOTS.get())
				&& entity.getPersistentData().getBoolean("bloodstoneEquipBoots") == true) {
			entity.getPersistentData().putBoolean("bloodstoneEquipBoots", false);
		}
	}
}
