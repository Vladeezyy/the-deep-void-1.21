package net.sombrius.thedeepvoid.procedures;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.OnlyIn;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.ItemTooltipEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

@EventBusSubscriber(value = {Dist.CLIENT})
public class PlagueDoctorDescriptionProcedure {
	@net.neoforged.api.distmarker.OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT) @net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event) {
		execute(event, event.getEntity(), event.getItemStack(), event.getToolTip());
	}

	public static void execute(Entity entity, ItemStack itemstack, List<Component> tooltip) {
		execute(null, entity, itemstack, tooltip);
	}

	private static void execute(@Nullable Event event, Entity entity, ItemStack itemstack, List<Component> tooltip) {
		if (entity == null || tooltip == null)
			return;
		if (itemstack.getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_LEGGINGS.get()
				|| itemstack.getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_BOOTS.get()) {
			tooltip.add(Component.literal(""));
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_BOOTS.get()) {
				if (net.minecraft.client.Minecraft.getInstance().hasShiftDown()) {
					tooltip.add(Component.literal(("\u00A76" + Component.translatable("armors.the_deep_void.crawler.when_worn").getString())));
					tooltip.add(Component.literal((" \u00A77-" + Component.translatable("armors.the_deep_void.plague.worn1").getString())));
					tooltip.add(Component.literal((" \u00A77-" + Component.translatable("armors.the_deep_void.plague.worn2").getString())));
					tooltip.add(Component.literal((" \u00A77-" + Component.translatable("armors.the_deep_void.plague.worn3").getString())));
					tooltip.add(Component.literal((" \u00A77-" + Component.translatable("armors.the_deep_void.plague.worn4").getString())));
					tooltip.add(Component.literal(("\u00A76" + Component.translatable("armors.the_deep_void.plague.uponDeath").getString())));
					tooltip.add(Component.literal((" \u00A77-" + Component.translatable("armors.the_deep_void.plague.death1").getString())));
				} else {
					tooltip.add(Component.literal((Component.translatable("armors.the_deep_void.press_shift").getString())));
				}
			} else {
				if (net.minecraft.client.Minecraft.getInstance().hasShiftDown()) {
					tooltip.add(Component.literal(("\u00A77" + Component.translatable("armors.the_deep_void.crawler.when_worn").getString())));
					tooltip.add(Component.literal((" \u00A77-" + Component.translatable("armors.the_deep_void.plague.worn1").getString())));
					tooltip.add(Component.literal((" \u00A77-" + Component.translatable("armors.the_deep_void.plague.worn2").getString())));
					tooltip.add(Component.literal((" \u00A77-" + Component.translatable("armors.the_deep_void.plague.worn3").getString())));
					tooltip.add(Component.literal((" \u00A77-" + Component.translatable("armors.the_deep_void.plague.worn4").getString())));
					tooltip.add(Component.literal(("\u00A77" + Component.translatable("armors.the_deep_void.plague.uponDeath").getString())));
					tooltip.add(Component.literal((" \u00A77-" + Component.translatable("armors.the_deep_void.plague.death1").getString())));
				} else {
					tooltip.add(Component.literal(("\u00A77" + Component.translatable("armors.the_deep_void.press_shift_nofull").getString())));
				}
			}
		}
	}
}
