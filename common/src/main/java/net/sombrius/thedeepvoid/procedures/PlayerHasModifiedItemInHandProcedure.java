package net.sombrius.thedeepvoid.procedures;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;

import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;

import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

import java.util.UUID;

@EventBusSubscriber
public class PlayerHasModifiedItemInHandProcedure {
	private static final ResourceLocation ID_0947265B_4005_4A6C_83E1_2C0F2B7390AC = ResourceLocation.fromNamespaceAndPath("the_deep_void", "0947265b-4005-4a6c-83e1-2c0f2b7390ac");
	private static final ResourceLocation ID_24AB05C9_3824_49F6_ADF3_029FA9B4194B = ResourceLocation.fromNamespaceAndPath("the_deep_void", "24ab05c9-3824-49f6-adf3-029fa9b4194b");
	private static final ResourceLocation ID_2580EB30_85A0_4616_88DC_F9A0C7E9D71E = ResourceLocation.fromNamespaceAndPath("the_deep_void", "2580eb30-85a0-4616-88dc-f9a0c7e9d71e");
	private static final ResourceLocation ID_3B71BDCC_6DDF_4B2C_A521_B67097A89EE4 = ResourceLocation.fromNamespaceAndPath("the_deep_void", "3b71bdcc-6ddf-4b2c-a521-b67097a89ee4");
	private static final ResourceLocation ID_7083DBD7_AE8B_4BA4_B931_F8AD37579EE7 = ResourceLocation.fromNamespaceAndPath("the_deep_void", "7083dbd7-ae8b-4ba4-b931-f8ad37579ee7");
	private static final ResourceLocation ID_740510EC_164B_4253_B513_3367BC1740AF = ResourceLocation.fromNamespaceAndPath("the_deep_void", "740510ec-164b-4253-b513-3367bc1740af");
	private static final ResourceLocation ID_8C4DDBF6_9DCA_4E0F_824D_BFEC53B34B53 = ResourceLocation.fromNamespaceAndPath("the_deep_void", "8c4ddbf6-9dca-4e0f-824d-bfec53b34b53");
	private static final ResourceLocation ID_BD693A85_8070_460A_99C7_A3E4335C7106 = ResourceLocation.fromNamespaceAndPath("the_deep_void", "bd693a85-8070-460a-99c7-a3e4335c7106");
	private static final ResourceLocation ID_FC2B6742_191E_4C45_9DFD_D3A22493C141 = ResourceLocation.fromNamespaceAndPath("the_deep_void", "fc2b6742-191e-4c45-9dfd-d3a22493c141");
	private static final TagKey<Item> ITEM_TAG_CLAWS = net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, ResourceLocation.parse("the_deep_void:claws"));
	private static final TagKey<Item> ITEM_TAG_SCYTHES = net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, ResourceLocation.parse("the_deep_void:scythes"));

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof SwordItem
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof PickaxeItem
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof AxeItem
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof ShovelItem
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof HoeItem
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof TridentItem) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("modification") == false) {
				ModifiersRemoveAttributesProcedure.execute(entity);
			} else {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("lavenditeSpeed") > 0
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("lavenditeDamage") > 0) {
					if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).hasModifier(ID_BD693A85_8070_460A_99C7_A3E4335C7106)))
						((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).addTransientModifier((new AttributeModifier(ID_BD693A85_8070_460A_99C7_A3E4335C7106,
								((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("lavenditeDamageLoss") * (-1)), AttributeModifier.Operation.ADD_VALUE)));
					if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).hasModifier(ID_FC2B6742_191E_4C45_9DFD_D3A22493C141)))
						((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).addTransientModifier((new AttributeModifier(ID_FC2B6742_191E_4C45_9DFD_D3A22493C141,
								((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("lavenditeSpeed")), AttributeModifier.Operation.ADD_VALUE)));
				}
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("rotSpeed") > 0) {
					if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).hasModifier(ID_3B71BDCC_6DDF_4B2C_A521_B67097A89EE4)))
						((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).addTransientModifier((new AttributeModifier(ID_3B71BDCC_6DDF_4B2C_A521_B67097A89EE4,
								((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("rotSpeed") * (-1)), AttributeModifier.Operation.ADD_VALUE)));
				}
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("darksteelSpeed") > 0
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("darksteelDamage") > 0) {
					if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).hasModifier(ID_740510EC_164B_4253_B513_3367BC1740AF)))
						((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).addTransientModifier((new AttributeModifier(ID_740510EC_164B_4253_B513_3367BC1740AF,
								((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("darksteelSpeed") * (-1)), AttributeModifier.Operation.ADD_VALUE)));
					if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).hasModifier(ID_24AB05C9_3824_49F6_ADF3_029FA9B4194B)))
						((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).addTransientModifier((new AttributeModifier(ID_24AB05C9_3824_49F6_ADF3_029FA9B4194B,
								((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("darksteelDamage")), AttributeModifier.Operation.ADD_VALUE)));
				}
			}
		} else if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof SwordItem)
				&& !((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof PickaxeItem)
				&& !((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof AxeItem)
				&& !((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof ShovelItem)
				&& !((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof HoeItem)
				&& !((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof TridentItem)) {
			ModifiersRemoveAttributesProcedure.execute(entity);
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ITEM_TAG_CLAWS)) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_BOOTS.get()
					&& !(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ID_0947265B_4005_4A6C_83E1_2C0F2B7390AC) != null)) {
				if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE)
						.hasModifier(ID_0947265B_4005_4A6C_83E1_2C0F2B7390AC)))
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).addTransientModifier(
							(new AttributeModifier(ID_0947265B_4005_4A6C_83E1_2C0F2B7390AC, ((double) DeepVoidConfigConfiguration.BLOODSTONEARMORBONUS.get()), AttributeModifier.Operation.ADD_VALUE)));
			} else if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ID_0947265B_4005_4A6C_83E1_2C0F2B7390AC) != null) {
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(ID_0947265B_4005_4A6C_83E1_2C0F2B7390AC);
			}
		} else if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ITEM_TAG_CLAWS))
				&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ID_0947265B_4005_4A6C_83E1_2C0F2B7390AC) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(ID_0947265B_4005_4A6C_83E1_2C0F2B7390AC);
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ITEM_TAG_CLAWS)) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HUNTERS_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HUNTERS_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HUNTERS_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HUNTERS_BOOTS.get()
					&& !(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ID_7083DBD7_AE8B_4BA4_B931_F8AD37579EE7) != null)) {
				if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE)
						.hasModifier(ID_7083DBD7_AE8B_4BA4_B931_F8AD37579EE7)))
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).addTransientModifier(
							(new AttributeModifier(ID_7083DBD7_AE8B_4BA4_B931_F8AD37579EE7, ((double) DeepVoidConfigConfiguration.HUNTERARMORBONUS.get()), AttributeModifier.Operation.ADD_VALUE)));
			} else if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ID_7083DBD7_AE8B_4BA4_B931_F8AD37579EE7) != null) {
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(ID_7083DBD7_AE8B_4BA4_B931_F8AD37579EE7);
			}
		} else if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ITEM_TAG_CLAWS))
				&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ID_0947265B_4005_4A6C_83E1_2C0F2B7390AC) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(ID_0947265B_4005_4A6C_83E1_2C0F2B7390AC);
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ITEM_TAG_SCYTHES)) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.PLAGUE_DOCTOR_BOOTS.get()
					&& !(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ID_8C4DDBF6_9DCA_4E0F_824D_BFEC53B34B53) != null)) {
				if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE)
						.hasModifier(ID_8C4DDBF6_9DCA_4E0F_824D_BFEC53B34B53)))
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).addTransientModifier(
							(new AttributeModifier(ID_8C4DDBF6_9DCA_4E0F_824D_BFEC53B34B53, ((double) DeepVoidConfigConfiguration.PLAGUEARMORBONUS.get()), AttributeModifier.Operation.ADD_VALUE)));
			} else if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ID_8C4DDBF6_9DCA_4E0F_824D_BFEC53B34B53) != null) {
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(ID_8C4DDBF6_9DCA_4E0F_824D_BFEC53B34B53);
			}
		} else if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ITEM_TAG_SCYTHES))
				&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ID_8C4DDBF6_9DCA_4E0F_824D_BFEC53B34B53) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(ID_8C4DDBF6_9DCA_4E0F_824D_BFEC53B34B53);
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ITEM_TAG_SCYTHES)) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_BOOTS.get()
					&& !(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ID_2580EB30_85A0_4616_88DC_F9A0C7E9D71E) != null)) {
				if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).hasModifier(ID_2580EB30_85A0_4616_88DC_F9A0C7E9D71E)))
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).addTransientModifier(
							(new AttributeModifier(ID_2580EB30_85A0_4616_88DC_F9A0C7E9D71E, ((double) DeepVoidConfigConfiguration.GRIMROTTENARMORBONUS.get()), AttributeModifier.Operation.ADD_VALUE)));
			} else if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ID_2580EB30_85A0_4616_88DC_F9A0C7E9D71E) != null) {
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(ID_2580EB30_85A0_4616_88DC_F9A0C7E9D71E);
			}
		} else if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ITEM_TAG_SCYTHES))
				&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getModifier(ID_2580EB30_85A0_4616_88DC_F9A0C7E9D71E) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(ID_2580EB30_85A0_4616_88DC_F9A0C7E9D71E);
		}
	}
}
