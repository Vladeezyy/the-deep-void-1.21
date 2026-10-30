
package net.sombrius.thedeepvoid.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.ArmorMaterial;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.OnlyIn;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.neo.IClientItemExtensions;
import net.sombrius.thedeepvoid.port.platform.RegistryEntry;
import net.sombrius.thedeepvoid.port.bus.RegisterEvent;
import net.sombrius.thedeepvoid.client.model.Modelvulture_chestplate;
import net.sombrius.thedeepvoid.client.model.Modelvulture_mask;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

@EventBusSubscriber
public abstract class VultureItem extends Item {
	public static final ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial(35, Map.of(ArmorType.BOOTS, 3, ArmorType.LEGGINGS, 6, ArmorType.CHESTPLATE, 6, ArmorType.HELMET, 3, ArmorType.BODY, 6), 25, net.minecraft.core.Holder.direct(net.minecraft.sounds.SoundEvent.createVariableRangeEvent(Identifier.parse("item.armor.equip_leather"))), 1f, 0f, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/death_vulture_feather")),
			ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath("the_deep_void", "vulture")));


	public VultureItem(ArmorType type, Item.Properties properties) {
		super(properties.humanoidArmor(ARMOR_MATERIAL, type));
	}

	public static class Helmet extends VultureItem implements net.sombrius.thedeepvoid.port.neo.HasClientExtensions.Item {
		public Helmet() {
			super(ArmorType.HELMET, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps());
		}

		public IClientItemExtensions clientExtensions() {
			return new IClientItemExtensions() {
				private HumanoidModel<?> armorModel;

				@Override
				public net.minecraft.client.model.Model getHumanoidArmorModel(ItemStack stack, net.minecraft.client.resources.model.EquipmentClientInfo.LayerType layerType, net.minecraft.client.model.Model original) {
					if (this.armorModel == null)
						this.armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
							Map.of("head", net.sombrius.thedeepvoid.port.PortArmorModels.head(new Modelvulture_mask(Minecraft.getInstance().getEntityModels().bakeLayer(Modelvulture_mask.LAYER_LOCATION)).Head), "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_arm",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					return this.armorModel;
				}

				public Identifier getArmorTexture(ItemStack stack, net.minecraft.client.resources.model.EquipmentClientInfo.LayerType type, net.minecraft.client.resources.model.EquipmentClientInfo.Layer layer, Identifier _default) {
					return Identifier.parse("the_deep_void:textures/entities/vulture_mask.png");
				}
			};
		}
	}

	public static class Chestplate extends VultureItem implements net.sombrius.thedeepvoid.port.neo.HasClientExtensions.Item {
		public Chestplate() {
			super(ArmorType.CHESTPLATE, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps());
		}

		public IClientItemExtensions clientExtensions() {
			return new IClientItemExtensions() {
				private HumanoidModel<?> armorModel;

				@Override
				@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
				public net.minecraft.client.model.Model getHumanoidArmorModel(ItemStack stack, net.minecraft.client.resources.model.EquipmentClientInfo.LayerType layerType, net.minecraft.client.model.Model original) {
					if (this.armorModel == null)
						this.armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of("body", new Modelvulture_chestplate(Minecraft.getInstance().getEntityModels().bakeLayer(Modelvulture_chestplate.LAYER_LOCATION)).Body,
							"left_arm", new Modelvulture_chestplate(Minecraft.getInstance().getEntityModels().bakeLayer(Modelvulture_chestplate.LAYER_LOCATION)).LeftArm, "right_arm",
							new Modelvulture_chestplate(Minecraft.getInstance().getEntityModels().bakeLayer(Modelvulture_chestplate.LAYER_LOCATION)).RightArm, "head", net.sombrius.thedeepvoid.port.PortArmorModels.head(new ModelPart(Collections.emptyList(), Collections.emptyMap())), "hat",
							new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					return this.armorModel;
				}

				public Identifier getArmorTexture(ItemStack stack, net.minecraft.client.resources.model.EquipmentClientInfo.LayerType type, net.minecraft.client.resources.model.EquipmentClientInfo.Layer layer, Identifier _default) {
					return Identifier.parse("the_deep_void:textures/entities/vulture_chestplate.png");
				}
			};
		}
	}

	public static class Leggings extends VultureItem {
		public Leggings() {
			super(ArmorType.LEGGINGS, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps());
		}
	}

	public static class Boots extends VultureItem {
		public Boots() {
			super(ArmorType.BOOTS, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps());
		}
	}
}
