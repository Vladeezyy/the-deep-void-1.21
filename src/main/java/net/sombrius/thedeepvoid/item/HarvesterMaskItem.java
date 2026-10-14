
package net.sombrius.thedeepvoid.item;

import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;

public abstract class HarvesterMaskItem extends ArmorItem {
	public HarvesterMaskItem(ArmorItem.Type type, Item.Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(ArmorItem.Type type) {
				return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 80;
			}

			@Override
			public int getDefenseForType(ArmorItem.Type type) {
				return new int[]{0, 0, 0, 4}[type.getSlot().getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 45;
			}

			@Override
			public SoundEvent getEquipSound() {
				return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.soul_sand.place"));
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(TheDeepVoidModItems.SOUL_SHARD.get()));
			}

			@Override
			public String getName() {
				return "harvester_mask";
			}

			@Override
			public float getToughness() {
				return 1f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.1f;
			}
		}, type, properties);
	}

	public static class Helmet extends HarvesterMaskItem {
		public Helmet() {
			super(ArmorItem.Type.HELMET, new Item.Properties().fireResistant());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "the_deep_void:textures/models/armor/soul_spite__layer_1.png";
		}
	}
}
