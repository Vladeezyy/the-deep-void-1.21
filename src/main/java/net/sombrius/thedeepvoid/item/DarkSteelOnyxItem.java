
package net.sombrius.thedeepvoid.item;

import com.google.common.collect.Iterables;
import java.util.EnumMap;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.procedures.OnyxArmorHelmetTickEventProcedure;
import net.sombrius.thedeepvoid.procedures.OnyxFullArmorProcedure;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public abstract class DarkSteelOnyxItem extends ArmorItem {
	public static Holder<ArmorMaterial> ARMOR_MATERIAL = null;

	@SubscribeEvent
	public static void registerArmorMaterial(RegisterEvent event) {
		event.register(Registries.ARMOR_MATERIAL, registerHelper -> {
			ArmorMaterial armorMaterial = new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
				map.put(ArmorItem.Type.BOOTS, 4);
				map.put(ArmorItem.Type.LEGGINGS, 7);
				map.put(ArmorItem.Type.CHESTPLATE, 8);
				map.put(ArmorItem.Type.HELMET, 4);
				map.put(ArmorItem.Type.BODY, 8);
			}), 8, DeferredHolder.create(Registries.SOUND_EVENT, ResourceLocation.parse("item.armor.equip_netherite")), () -> Ingredient.of(new ItemStack(TheDeepVoidModItems.DARK_STEEL_SCRAP.get())),
					List.of(new ArmorMaterial.Layer(ResourceLocation.parse("the_deep_void:dark_steel_"))), 2.3f, 0.25f);
			registerHelper.register(ResourceLocation.parse("the_deep_void:dark_steel_onyx"), armorMaterial);
			ARMOR_MATERIAL = BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(armorMaterial);
		});
	}

	public DarkSteelOnyxItem(ArmorItem.Type type, Item.Properties properties) {
		super(ARMOR_MATERIAL, type, properties);
	}

	public static class Helmet extends DarkSteelOnyxItem {
		public Helmet() {
			super(ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(48)).fireResistant());
		}

		@Override
		public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
				OnyxArmorHelmetTickEventProcedure.execute(entity);
			}
		}
	}

	public static class Chestplate extends DarkSteelOnyxItem {
		public Chestplate() {
			super(ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(48)).fireResistant());
		}

		@Override
		public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
				OnyxFullArmorProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity);
			}
		}
	}

	public static class Leggings extends DarkSteelOnyxItem {
		public Leggings() {
			super(ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(48)).fireResistant());
		}

		@Override
		public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
				OnyxArmorHelmetTickEventProcedure.execute(entity);
			}
		}
	}

	public static class Boots extends DarkSteelOnyxItem {
		public Boots() {
			super(ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(48)).fireResistant());
		}

		@Override
		public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
				OnyxArmorHelmetTickEventProcedure.execute(entity);
			}
		}
	}
}
