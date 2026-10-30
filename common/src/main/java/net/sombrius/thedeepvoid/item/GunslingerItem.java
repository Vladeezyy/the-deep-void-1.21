
package net.sombrius.thedeepvoid.item;

import java.util.Map;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.ArmorMaterial;
import java.util.EnumMap;
import java.util.List;
import net.minecraft.util.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.platform.RegistryEntry;
import net.sombrius.thedeepvoid.port.bus.RegisterEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

@EventBusSubscriber
public abstract class GunslingerItem extends Item {
	public static final ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial(35, Map.of(ArmorType.BOOTS, 2, ArmorType.LEGGINGS, 4, ArmorType.CHESTPLATE, 5, ArmorType.HELMET, 2, ArmorType.BODY, 5), 25, net.minecraft.core.Holder.direct(net.minecraft.sounds.SoundEvent.createVariableRangeEvent(Identifier.parse("item.armor.equip_leather"))), 0f, 0f, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/dark_cloth")),
			ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath("the_deep_void", "gunslinger")));


	public GunslingerItem(ArmorType type, Item.Properties properties) {
		super(properties.humanoidArmor(ARMOR_MATERIAL, type));
	}

	public static class Helmet extends GunslingerItem {
		public Helmet() {
			super(ArmorType.HELMET, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps());
		}
	}

	public static class Chestplate extends GunslingerItem {
		public Chestplate() {
			super(ArmorType.CHESTPLATE, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps());
		}
	}

	public static class Leggings extends GunslingerItem {
		public Leggings() {
			super(ArmorType.LEGGINGS, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps());
		}
	}

	public static class Boots extends GunslingerItem {
		public Boots() {
			super(ArmorType.BOOTS, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps());
		}
	}
}
