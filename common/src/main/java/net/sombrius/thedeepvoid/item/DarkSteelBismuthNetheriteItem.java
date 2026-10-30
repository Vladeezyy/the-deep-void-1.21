
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

import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.registries.BuiltInRegistries;

@EventBusSubscriber
public abstract class DarkSteelBismuthNetheriteItem extends Item {
	public static final ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial(0, Map.of(ArmorType.BOOTS, 4, ArmorType.LEGGINGS, 8, ArmorType.CHESTPLATE, 8, ArmorType.HELMET, 4, ArmorType.BODY, 8), 18, net.minecraft.core.Holder.direct(net.minecraft.sounds.SoundEvent.createVariableRangeEvent(Identifier.parse("item.armor.equip_netherite"))), 4.8f, 0.25f, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/none")),
			ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath("the_deep_void", "dark_steel_bismuth_netherite")));


	public DarkSteelBismuthNetheriteItem(ArmorType type, Item.Properties properties) {
		super(properties.humanoidArmor(ARMOR_MATERIAL, type));
	}

	public static class Helmet extends DarkSteelBismuthNetheriteItem {
		public Helmet() {
			super(ArmorType.HELMET, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(572));
		}
	}

	public static class Chestplate extends DarkSteelBismuthNetheriteItem {
		public Chestplate() {
			super(ArmorType.CHESTPLATE, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(832));
		}
	}

	public static class Leggings extends DarkSteelBismuthNetheriteItem {
		public Leggings() {
			super(ArmorType.LEGGINGS, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(780));
		}
	}

	public static class Boots extends DarkSteelBismuthNetheriteItem {
		public Boots() {
			super(ArmorType.BOOTS, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(676));
		}
	}
}
