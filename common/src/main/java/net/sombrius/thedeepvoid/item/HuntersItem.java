
package net.sombrius.thedeepvoid.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.ArmorMaterial;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import com.google.common.collect.Iterables;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.neo.IClientItemExtensions;
import net.sombrius.thedeepvoid.port.platform.RegistryEntry;
import net.sombrius.thedeepvoid.port.bus.RegisterEvent;
import net.sombrius.thedeepvoid.client.model.Modelhunter_mask;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.procedures.HuntersBootsTickEventProcedure;
import net.sombrius.thedeepvoid.procedures.HuntersHelmetTickEventProcedure;

@EventBusSubscriber
public abstract class HuntersItem extends Item {
	public static final ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial(37, Map.of(ArmorType.BOOTS, 4, ArmorType.LEGGINGS, 6, ArmorType.CHESTPLATE, 7, ArmorType.HELMET, 4, ArmorType.BODY, 7), 15, net.minecraft.core.Holder.direct(net.minecraft.sounds.SoundEvent.createVariableRangeEvent(Identifier.parse("block.soul_sand.place"))), 1f, 0.1f, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/rotten_bone")),
			ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath("the_deep_void", "hunters")));


	public HuntersItem(ArmorType type, Item.Properties properties) {
		super(properties.humanoidArmor(ARMOR_MATERIAL, type));
	}

	public static class Helmet extends HuntersItem implements net.sombrius.thedeepvoid.port.neo.HasClientExtensions.Item {
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
							Map.of("head", net.sombrius.thedeepvoid.port.PortArmorModels.head(new Modelhunter_mask(Minecraft.getInstance().getEntityModels().bakeLayer(Modelhunter_mask.LAYER_LOCATION)).Head), "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_arm",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg",
									new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					return this.armorModel;
				}

				public Identifier getArmorTexture(ItemStack stack, net.minecraft.client.resources.model.EquipmentClientInfo.LayerType type, net.minecraft.client.resources.model.EquipmentClientInfo.Layer layer, Identifier _default) {
					return Identifier.parse("the_deep_void:textures/entities/hunter_mask.png");
				}
			};
		}

		@Override
		public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
			super.inventoryTick(itemstack, world, entity, equipmentSlot);
			boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
			int slot = equipmentSlot == null ? -1 : equipmentSlot.getIndex();
			if (entity instanceof Player player && net.sombrius.thedeepvoid.port.PortUtil.wearing(player, itemstack)) {
				HuntersHelmetTickEventProcedure.execute(world, entity, itemstack);
			}
		}
	}

	public static class Chestplate extends HuntersItem {
		public Chestplate() {
			super(ArmorType.CHESTPLATE, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps());
		}
	}

	public static class Leggings extends HuntersItem {
		public Leggings() {
			super(ArmorType.LEGGINGS, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps());
		}
	}

	public static class Boots extends HuntersItem {
		public Boots() {
			super(ArmorType.BOOTS, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps());
		}

		@Override
		public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
			super.inventoryTick(itemstack, world, entity, equipmentSlot);
			boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
			int slot = equipmentSlot == null ? -1 : equipmentSlot.getIndex();
			if (entity instanceof Player player && net.sombrius.thedeepvoid.port.PortUtil.wearing(player, itemstack)) {
				HuntersBootsTickEventProcedure.execute(entity, itemstack);
			}
		}
	}
}
