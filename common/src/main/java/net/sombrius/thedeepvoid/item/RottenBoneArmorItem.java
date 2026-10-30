
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
public abstract class RottenBoneArmorItem extends Item {
	public static final ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial(0, Map.of(ArmorType.BOOTS, 2, ArmorType.LEGGINGS, 4, ArmorType.CHESTPLATE, 5, ArmorType.HELMET, 2, ArmorType.BODY, 5), 15, net.minecraft.core.Holder.direct(net.minecraft.sounds.SoundEvent.createVariableRangeEvent(Identifier.parse("item.armor.equip_leather"))), 1f, 0f, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/rotten_bone")),
			ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath("the_deep_void", "rotten_bone_armor")));


	public RottenBoneArmorItem(ArmorType type, Item.Properties properties) {
		super(properties.humanoidArmor(ARMOR_MATERIAL, type));
	}

	public static class Helmet extends RottenBoneArmorItem {
		public Helmet() {
			super(ArmorType.HELMET, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(220));
		}
	}

	public static class Chestplate extends RottenBoneArmorItem {
		public Chestplate() {
			super(ArmorType.CHESTPLATE, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(320));
		}
	}

	public static class Leggings extends RottenBoneArmorItem {
		public Leggings() {
			super(ArmorType.LEGGINGS, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(300));
		}
	}

	public static class Boots extends RottenBoneArmorItem {
		public Boots() {
			super(ArmorType.BOOTS, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(260));
		}
	}
}
