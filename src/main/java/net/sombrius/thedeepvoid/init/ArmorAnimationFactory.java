package net.sombrius.thedeepvoid.init;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.sombrius.thedeepvoid.item.FlyWingsFlyItem;
import net.sombrius.thedeepvoid.item.FlyWingsItem;
import net.sombrius.thedeepvoid.item.SoulWingsFlyItem;
import net.sombrius.thedeepvoid.item.SoulWingsItem;
import software.bernie.geckolib.animatable.GeoItem;

@EventBusSubscriber
public class ArmorAnimationFactory {
	@SubscribeEvent
	public static void animatedArmors(PlayerTickEvent.Post event) {
		String animation = "";
		if (event.getEntity().getItemBySlot(EquipmentSlot.HEAD).getItem() != (ItemStack.EMPTY).getItem() && event.getEntity().getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof GeoItem) {
			if (!event.getEntity().getItemBySlot(EquipmentSlot.HEAD).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim").equals("")) {
				animation = event.getEntity().getItemBySlot(EquipmentSlot.HEAD).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
				CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getItemBySlot(EquipmentSlot.HEAD), tag -> tag.putString("geckoAnim", ""));
				if (event.getEntity().getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SoulWingsItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
				if (event.getEntity().getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SoulWingsFlyItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
				if (event.getEntity().getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof FlyWingsItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
				if (event.getEntity().getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof FlyWingsFlyItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
			}
		}
		if (event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem() != (ItemStack.EMPTY).getItem() && event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof GeoItem) {
			if (!event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim").equals("")) {
				animation = event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
				CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getItemBySlot(EquipmentSlot.CHEST), tag -> tag.putString("geckoAnim", ""));
				if (event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SoulWingsItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
				if (event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SoulWingsFlyItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
				if (event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof FlyWingsItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
				if (event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof FlyWingsFlyItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
			}
		}
		if (event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem() != (ItemStack.EMPTY).getItem() && event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof GeoItem) {
			if (!event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim").equals("")) {
				animation = event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
				CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getItemBySlot(EquipmentSlot.HEAD), tag -> tag.putString("geckoAnim", ""));
				if (event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SoulWingsItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
				if (event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SoulWingsFlyItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
				if (event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof FlyWingsItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
				if (event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof FlyWingsFlyItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
			}
		}
		if (event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem() != (ItemStack.EMPTY).getItem() && event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem() instanceof GeoItem) {
			if (!event.getEntity().getItemBySlot(EquipmentSlot.FEET).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim").equals("")) {
				animation = event.getEntity().getItemBySlot(EquipmentSlot.FEET).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
				CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getItemBySlot(EquipmentSlot.FEET), tag -> tag.putString("geckoAnim", ""));
				if (event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SoulWingsItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
				if (event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SoulWingsFlyItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
				if (event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem() instanceof FlyWingsItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
				if (event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem() instanceof FlyWingsFlyItem animatable && event.getEntity().level().isClientSide())
					animatable.animationprocedure = animation;
			}
		}
	}
}
