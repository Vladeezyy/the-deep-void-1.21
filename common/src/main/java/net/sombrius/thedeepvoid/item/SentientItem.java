
package net.sombrius.thedeepvoid.item;

import java.util.Map;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.sombrius.thedeepvoid.port.platform.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.minecraft.core.Holder;
import java.util.EnumMap;
import net.minecraft.util.Util;
import net.sombrius.thedeepvoid.port.bus.RegisterEvent;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import java.util.List;

import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.Identifier;

@EventBusSubscriber
public abstract class SentientItem extends Item {
	public static final ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial(0, Map.of(ArmorType.BOOTS, 3, ArmorType.LEGGINGS, 6, ArmorType.CHESTPLATE, 7, ArmorType.HELMET, 3, ArmorType.BODY, 7), 18, net.minecraft.core.Holder.direct(net.minecraft.sounds.SoundEvent.createVariableRangeEvent(Identifier.parse("entity.wither_skeleton.ambient"))), 1f, 0f, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/misted_bone")),
			ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath("the_deep_void", "sentient")));


	public SentientItem(ArmorType type, Item.Properties properties) {
		super(properties.humanoidArmor(ARMOR_MATERIAL, type));
	}

	public static class Helmet extends SentientItem {
		public Helmet() {
			super(ArmorType.HELMET, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(0));
		}
	}

	public static class Chestplate extends SentientItem {
		public Chestplate() {
			super(ArmorType.CHESTPLATE, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(0));
		}
	}

	public static class Leggings extends SentientItem {
		public Leggings() {
			super(ArmorType.LEGGINGS, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(0));
		}
	}

	public static class Boots extends SentientItem {
		public Boots() {
			super(ArmorType.BOOTS, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(0));
		}
	}
}
