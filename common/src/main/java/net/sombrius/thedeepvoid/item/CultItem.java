
package net.sombrius.thedeepvoid.item;

import java.util.Map;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.ArmorMaterial;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import com.google.common.collect.Iterables;
import java.util.EnumMap;
import java.util.List;
import net.minecraft.util.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.platform.RegistryEntry;
import net.sombrius.thedeepvoid.port.bus.RegisterEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.procedures.CultFullArmorProcedure;

@EventBusSubscriber
public abstract class CultItem extends Item {
	public static final ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial(15, Map.of(ArmorType.BOOTS, 2, ArmorType.LEGGINGS, 3, ArmorType.CHESTPLATE, 4, ArmorType.HELMET, 2, ArmorType.BODY, 4), 15, net.minecraft.core.Holder.direct(net.minecraft.sounds.SoundEvent.createVariableRangeEvent(Identifier.parse("block.soul_sand.place"))), 0.2f, 0f, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/mourner_hide")),
			ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath("the_deep_void", "cult")));


	public CultItem(ArmorType type, Item.Properties properties) {
		super(properties.humanoidArmor(ARMOR_MATERIAL, type));
	}

	public static class Helmet extends CultItem {
		public Helmet() {
			super(ArmorType.HELMET, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps());
		}
	}

	public static class Chestplate extends CultItem {
		public Chestplate() {
			super(ArmorType.CHESTPLATE, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps());
		}

		@Override
		public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
			super.inventoryTick(itemstack, world, entity, equipmentSlot);
			boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
			int slot = equipmentSlot == null ? -1 : equipmentSlot.getIndex();
			if (entity instanceof Player player && net.sombrius.thedeepvoid.port.PortUtil.wearing(player, itemstack)) {
				CultFullArmorProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity);
			}
		}
	}

	public static class Leggings extends CultItem {
		public Leggings() {
			super(ArmorType.LEGGINGS, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps());
		}
	}

	public static class Boots extends CultItem {
		public Boots() {
			super(ArmorType.BOOTS, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps());
		}
	}
}
