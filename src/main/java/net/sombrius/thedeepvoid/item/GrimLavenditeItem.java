
package net.sombrius.thedeepvoid.item;

import java.util.EnumMap;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public abstract class GrimLavenditeItem extends ArmorItem {
	public static Holder<ArmorMaterial> ARMOR_MATERIAL = null;

	@SubscribeEvent
	public static void registerArmorMaterial(RegisterEvent event) {
		event.register(Registries.ARMOR_MATERIAL, registerHelper -> {
			ArmorMaterial armorMaterial = new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
				map.put(ArmorItem.Type.BOOTS, 4);
				map.put(ArmorItem.Type.LEGGINGS, 7);
				map.put(ArmorItem.Type.CHESTPLATE, 7);
				map.put(ArmorItem.Type.HELMET, 4);
				map.put(ArmorItem.Type.BODY, 7);
			}), 28, DeferredHolder.create(Registries.SOUND_EVENT, ResourceLocation.parse("block.amethyst_block.step")), () -> Ingredient.of(new ItemStack(TheDeepVoidModItems.LAVENDITE.get())),
					List.of(new ArmorMaterial.Layer(ResourceLocation.parse("the_deep_void:grim_lavendite_"))), 1f, 0.1f);
			registerHelper.register(ResourceLocation.parse("the_deep_void:grim_lavendite"), armorMaterial);
			ARMOR_MATERIAL = BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(armorMaterial);
		});
	}

	public GrimLavenditeItem(ArmorItem.Type type, Item.Properties properties) {
		super(ARMOR_MATERIAL, type, properties);
	}

	public static class Helmet extends GrimLavenditeItem {
		public Helmet() {
			super(ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(30)).fireResistant());
		}
	}

	public static class Chestplate extends GrimLavenditeItem {
		public Chestplate() {
			super(ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(30)).fireResistant());
		}
	}

	public static class Leggings extends GrimLavenditeItem {
		public Leggings() {
			super(ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(30)).fireResistant());
		}
	}

	public static class Boots extends GrimLavenditeItem {
		public Boots() {
			super(ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(30)).fireResistant());
		}
	}
}
