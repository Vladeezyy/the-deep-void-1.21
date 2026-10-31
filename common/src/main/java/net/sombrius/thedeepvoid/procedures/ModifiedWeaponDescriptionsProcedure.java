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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

import java.util.List;

@EventBusSubscriber(value = Dist.CLIENT)
public class ModifiedWeaponDescriptionsProcedure {
	@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
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
		if (itemstack.is(net.minecraft.tags.ItemTags.SWORDS) || itemstack.is(net.minecraft.tags.ItemTags.PICKAXES) || itemstack.is(net.minecraft.tags.ItemTags.AXES) || itemstack.is(net.minecraft.tags.ItemTags.SHOVELS) || itemstack.is(net.minecraft.tags.ItemTags.HOES)
				|| itemstack.getItem() instanceof TridentItem) {
			if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("modification", false) == true) {
				tooltip.add(Component.literal(""));
				tooltip.add(Component.literal(("\u00A77" + Component.translatable("item.the_deep_void.weapon_tooltip.modification").getString())));
				if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("lavenditeDamageLoss", 0) > 0 || itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("darksteelDamage", 0) > 0) {
					tooltip.add(Component.literal(((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("darksteelDamage", 0) + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("lavenditeDamage", 0) * (-1) < 0 ? " \u00A7c" : " \u00A72+") + ""
							+ new java.text.DecimalFormat("##.##").format(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("darksteelDamage", 0) + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("lavenditeDamageLoss", 0) * (-1)) + " "
							+ Component.translatable("item.the_deep_void.weapon_tooltip.attack_damage").getString())));
				}
				if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("lavenditeSpeed", 0) > 0 || itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("rotSpeed", 0) > 0 || itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("darksteelSpeed", 0) > 0) {
					tooltip.add(Component.literal(
							((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("lavenditeSpeed", 0) + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("rotSpeed", 0) * (-1) + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("darksteelSpeed", 0) * (-1) < 0 ? " \u00A7c" : " \u00A72+") + ""
									+ new java.text.DecimalFormat("##.##")
											.format(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("lavenditeSpeed", 0) + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("rotSpeed", 0) * (-1) + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("darksteelSpeed", 0) * (-1))
									+ " " + Component.translatable("item.the_deep_void.weapon_tooltip.attack_speed").getString())));
				}
				if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("rotLevel", 0) > 0) {
					tooltip.add(
							Component.literal((" \u00A72" + Component.translatable("item.the_deep_void.weapon_tooltip.applies_rot").getString() + " (Lvl " + new java.text.DecimalFormat("##").format(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("rotLevel", 0))
									+ " [00:0" + new java.text.DecimalFormat("##").format(Math.round(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("rotDuration", 0) / 20)) + "])")));
				}
			}
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_HELMET.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_CHESTPLATE.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_LEGGINGS.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_BOOTS.get()
				&& itemstack.is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("the_deep_void:claws")))) {
			tooltip.add(Component.literal(""));
			tooltip.add(Component.literal(("\u00A77" + Component.translatable("item.the_deep_void.weapon_tooltip.bloodstone_armor").getString())));
			tooltip.add(Component
					.literal((" \u00A72+" + new java.text.DecimalFormat("##").format((double) DeepVoidConfigConfiguration.BLOODSTONEARMORBONUS.get()) + " " + Component.translatable("item.the_deep_void.weapon_tooltip.attack_damage").getString())));
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_HELMET.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_CHESTPLATE.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_LEGGINGS.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_BOOTS.get()
				&& itemstack.is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("the_deep_void:scythes")))) {
			tooltip.add(Component.literal(""));
			tooltip.add(Component.literal(("\u00A77" + Component.translatable("item.the_deep_void.weapon_tooltip.bloodstone_armor").getString())));
			tooltip.add(Component
					.literal((" \u00A72+" + new java.text.DecimalFormat("##").format((double) DeepVoidConfigConfiguration.PLAGUEARMORBONUS.get()) + " " + Component.translatable("item.the_deep_void.weapon_tooltip.attack_damage").getString())));
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_HELMET.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_CHESTPLATE.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_LEGGINGS.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_BOOTS.get()
				&& itemstack.is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("the_deep_void:scythes")))) {
			tooltip.add(Component.literal(""));
			tooltip.add(Component.literal(("\u00A77" + Component.translatable("item.the_deep_void.weapon_tooltip.bloodstone_armor").getString())));
			tooltip.add(Component
					.literal((" \u00A72+" + new java.text.DecimalFormat("##").format((double) DeepVoidConfigConfiguration.GRIMROTTENARMORBONUS.get()) + " " + Component.translatable("item.the_deep_void.weapon_tooltip.attack_damage").getString())));
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HUNTERS_HELMET.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HUNTERS_CHESTPLATE.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HUNTERS_LEGGINGS.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HUNTERS_BOOTS.get()
				&& itemstack.is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("the_deep_void:claws")))) {
			tooltip.add(Component.literal(""));
			tooltip.add(Component.literal(("\u00A77" + Component.translatable("item.the_deep_void.weapon_tooltip.bloodstone_armor").getString())));
			tooltip.add(Component
					.literal((" \u00A72+" + new java.text.DecimalFormat("##").format((double) DeepVoidConfigConfiguration.HUNTERARMORBONUS.get()) + " " + Component.translatable("item.the_deep_void.weapon_tooltip.attack_damage").getString())));
		}
	}
}
