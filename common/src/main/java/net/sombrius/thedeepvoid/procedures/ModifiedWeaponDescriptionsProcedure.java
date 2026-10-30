package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;

import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.ItemTooltipEvent;
import net.sombrius.thedeepvoid.port.bus.OnlyIn;
import net.sombrius.thedeepvoid.port.bus.Dist;

import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

import java.util.List;

@EventBusSubscriber(value = Dist.CLIENT)
public class ModifiedWeaponDescriptionsProcedure {
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
		if (itemstack.getItem() instanceof SwordItem || itemstack.getItem() instanceof PickaxeItem || itemstack.getItem() instanceof AxeItem || itemstack.getItem() instanceof ShovelItem || itemstack.getItem() instanceof HoeItem
				|| itemstack.getItem() instanceof TridentItem) {
			if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("modification") == true) {
				tooltip.add(Component.literal(""));
				tooltip.add(Component.literal(("\u00A77" + Component.translatable("item.the_deep_void.weapon_tooltip.modification").getString())));
				if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("lavenditeDamageLoss") > 0 || itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("darksteelDamage") > 0) {
					tooltip.add(Component.literal(((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("darksteelDamage") + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("lavenditeDamage") * (-1) < 0 ? " \u00A7c" : " \u00A72+") + ""
							+ new java.text.DecimalFormat("##.##").format(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("darksteelDamage") + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("lavenditeDamageLoss") * (-1)) + " "
							+ Component.translatable("item.the_deep_void.weapon_tooltip.attack_damage").getString())));
				}
				if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("lavenditeSpeed") > 0 || itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("rotSpeed") > 0 || itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("darksteelSpeed") > 0) {
					tooltip.add(Component.literal(
							((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("lavenditeSpeed") + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("rotSpeed") * (-1) + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("darksteelSpeed") * (-1) < 0 ? " \u00A7c" : " \u00A72+") + ""
									+ new java.text.DecimalFormat("##.##")
											.format(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("lavenditeSpeed") + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("rotSpeed") * (-1) + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("darksteelSpeed") * (-1))
									+ " " + Component.translatable("item.the_deep_void.weapon_tooltip.attack_speed").getString())));
				}
				if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("rotLevel") > 0) {
					tooltip.add(
							Component.literal((" \u00A72" + Component.translatable("item.the_deep_void.weapon_tooltip.applies_rot").getString() + " (Lvl " + new java.text.DecimalFormat("##").format(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("rotLevel"))
									+ " [00:0" + new java.text.DecimalFormat("##").format(Math.round(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("rotDuration") / 20)) + "])")));
				}
			}
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_HELMET.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_CHESTPLATE.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_LEGGINGS.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_BOOTS.get()
				&& itemstack.is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, ResourceLocation.parse("the_deep_void:claws")))) {
			tooltip.add(Component.literal(""));
			tooltip.add(Component.literal(("\u00A77" + Component.translatable("item.the_deep_void.weapon_tooltip.bloodstone_armor").getString())));
			tooltip.add(Component
					.literal((" \u00A72+" + new java.text.DecimalFormat("##").format((double) DeepVoidConfigConfiguration.BLOODSTONEARMORBONUS.get()) + " " + Component.translatable("item.the_deep_void.weapon_tooltip.attack_damage").getString())));
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_HELMET.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_CHESTPLATE.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_LEGGINGS.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_BOOTS.get()
				&& itemstack.is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, ResourceLocation.parse("the_deep_void:scythes")))) {
			tooltip.add(Component.literal(""));
			tooltip.add(Component.literal(("\u00A77" + Component.translatable("item.the_deep_void.weapon_tooltip.bloodstone_armor").getString())));
			tooltip.add(Component
					.literal((" \u00A72+" + new java.text.DecimalFormat("##").format((double) DeepVoidConfigConfiguration.PLAGUEARMORBONUS.get()) + " " + Component.translatable("item.the_deep_void.weapon_tooltip.attack_damage").getString())));
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_HELMET.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_CHESTPLATE.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_LEGGINGS.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_BOOTS.get()
				&& itemstack.is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, ResourceLocation.parse("the_deep_void:scythes")))) {
			tooltip.add(Component.literal(""));
			tooltip.add(Component.literal(("\u00A77" + Component.translatable("item.the_deep_void.weapon_tooltip.bloodstone_armor").getString())));
			tooltip.add(Component
					.literal((" \u00A72+" + new java.text.DecimalFormat("##").format((double) DeepVoidConfigConfiguration.GRIMROTTENARMORBONUS.get()) + " " + Component.translatable("item.the_deep_void.weapon_tooltip.attack_damage").getString())));
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HUNTERS_HELMET.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HUNTERS_CHESTPLATE.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HUNTERS_LEGGINGS.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HUNTERS_BOOTS.get()
				&& itemstack.is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, ResourceLocation.parse("the_deep_void:claws")))) {
			tooltip.add(Component.literal(""));
			tooltip.add(Component.literal(("\u00A77" + Component.translatable("item.the_deep_void.weapon_tooltip.bloodstone_armor").getString())));
			tooltip.add(Component
					.literal((" \u00A72+" + new java.text.DecimalFormat("##").format((double) DeepVoidConfigConfiguration.HUNTERARMORBONUS.get()) + " " + Component.translatable("item.the_deep_void.weapon_tooltip.attack_damage").getString())));
		}
	}
}
