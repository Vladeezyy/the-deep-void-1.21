package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EquipmentSlotGroup;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.ItemAttributeModifierEvent;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

@EventBusSubscriber
public class SetItemAttributesProcedure {
	@SubscribeEvent
	public static void addAttributeModifier(ItemAttributeModifierEvent event) {
		execute(event, event.getItemStack());
	}

	public static void execute(ItemStack itemstack) {
		execute(null, itemstack);
	}

	private static void execute(@Nullable Event event, ItemStack itemstack) {
		if (event instanceof ItemAttributeModifierEvent _event) {
			if (itemstack.getItem() == TheDeepVoidModItems.ONYX_NAGINATA.get() || itemstack.getItem() == TheDeepVoidModItems.DARK_STEEL_NAGINATA.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "c2d764d5-1e7e-4f13-8e8a-5a6d073a2b9d"), ((double) DeepVoidConfigConfiguration.ONYXNAGINATAREACHADDITION.get()), AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.PENITENT_CROSS.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "1d949c6f-0aa0-4cb1-b4d1-0da21d149162"), ((double) DeepVoidConfigConfiguration.PENITENTCROSSREACHADDITION.get()), AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_KNOCKBACK, (new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "daa20447-a292-4a1f-be0b-5a1d395597bc"), 1.3, AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.EXECUTIONER_SWORD.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "91c3cc39-df93-477d-878a-3f8a1c0a3747"), 1, AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.STINGER.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED, (new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "3ebf6c26-26e9-4652-8ed1-054178166bea"), 1.4, AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.KNIVES_GLOVE.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED, (new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "77b8b253-e87d-4b69-a7eb-83208a542bda"), 0.4, AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
			}
			if (itemstack.is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("the_deep_void:dark_steel_tools")))) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_KNOCKBACK,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "582300b7-5e68-4807-a822-01bebd809fd3"), 1, AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
			}
			if (itemstack.is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("the_deep_void:scythes")))) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "ebf794e9-482e-4e28-81b3-2f8d23258894"), ((double) DeepVoidConfigConfiguration.SCYTHESREACH.get()), AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.VOIDRIUM_BULWARK.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE, (new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "ea9dbfe9-9f46-4046-b57a-d47dbe387cca"), 5, AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.ROT_TONGUE.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "113f833e-af9f-460e-b241-4d8f43a07a86"), 0.6, AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.ICHOR_GREATSWORD.get()) {
				_event.addModifier(Attributes.ENTITY_INTERACTION_RANGE, (new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "35a80e0b-12f5-4144-ae1f-a97eb041cfa7"), 0.6, AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.BLOODY_CHAINSAW.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_KNOCKBACK,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "500adec7-d9f3-4d82-8e04-35c74fcd939b"), (-0.9), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)), EquipmentSlotGroup.MAINHAND);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.JUDGEMENT.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "6d6c2b75-a398-4232-9e1c-1547c735cf8a"), 0.55, AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.VOID_MATTER_GREATSWORD.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "48f3899b-36d3-4114-bf7e-e331e1095109"), 0.55, AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.CLAW_SCYTHE.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED, (new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "34336e81-e420-44ed-9b7c-5823ad725ae7"), (-0.8), AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.BALL_AND_CHAIN.get()) {
				_event.addModifier(Attributes.ENTITY_INTERACTION_RANGE, (new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "5e859aad-6ca6-489d-89fe-aa1f057764a5"), (-5), AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.CEREMONIAL_DAGGER.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ENTITY_INTERACTION_RANGE,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "c06f24ef-d58e-4c6c-8731-a6ca8764b269"), (-0.5), AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE, (new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "e2bd140a-7c62-4aac-b997-e3dcb3e25846"),
						(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("kills", 0)), AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.MAINHAND);
			}
		}
		if (event instanceof ItemAttributeModifierEvent _event) {
			if (itemstack.getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_HELMET.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "aa5c1f7d-57c5-4cf4-bc5a-a33220af9855"), (-1), AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.HEAD);
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "e3e28def-8ac7-4786-bf1b-9c40b2adbe13"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)), EquipmentSlotGroup.HEAD);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.VULTURE_HELMET.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "20becca3-d644-43ef-8598-24659f89f3b3"), 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)), EquipmentSlotGroup.HEAD);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.DARK_STEEL_BISMUTH_NETHERITE_HELMET.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "02a154be-a982-4399-bdbf-47feb640e42f"), (-0.05), AttributeModifier.Operation.ADD_MULTIPLIED_BASE)), EquipmentSlotGroup.HEAD);
			}
		}
		if (event instanceof ItemAttributeModifierEvent _event) {
			if (itemstack.getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_CHESTPLATE.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "a9a9f5bc-1598-48bf-b126-576cd3e43583"), (-1), AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.CHEST);
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "3d44d829-1981-4ce1-ad8d-0b61f55544e3"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)), EquipmentSlotGroup.CHEST);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.VULTURE_CHESTPLATE.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "c3ec1923-603c-48b7-838d-b45534890f9b"), 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)), EquipmentSlotGroup.CHEST);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.DARK_STEEL_BISMUTH_NETHERITE_CHESTPLATE.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "337de984-8c20-47fe-aaa5-8d7ff1f8db59"), (-0.05), AttributeModifier.Operation.ADD_MULTIPLIED_BASE)), EquipmentSlotGroup.CHEST);
			}
		}
		if (event instanceof ItemAttributeModifierEvent _event) {
			if (itemstack.getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_LEGGINGS.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "086d99dd-744f-4f1b-b221-0498a2e55ae2"), (-1), AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.LEGS);
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "01d34c3f-48cc-4963-ae79-18fd27890ea1"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)), EquipmentSlotGroup.LEGS);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.VULTURE_LEGGINGS.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "56a76483-c4d7-4dc0-9c35-0387fc9b134d"), 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)), EquipmentSlotGroup.LEGS);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.DARK_STEEL_BISMUTH_NETHERITE_LEGGINGS.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "0294bfa7-a54b-40e6-b2ad-16284859dbaf"), (-0.05), AttributeModifier.Operation.ADD_MULTIPLIED_BASE)), EquipmentSlotGroup.LEGS);
			}
		}
		if (event instanceof ItemAttributeModifierEvent _event) {
			if (itemstack.getItem() == TheDeepVoidModItems.BLOODSTONE_ARMOR_BOOTS.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "d6e38656-aace-4147-bf0e-0a95c42bb0f1"), (-1), AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.FEET);
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "5efe39a4-375f-4a75-a4f6-55d7f2aaf10f"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)), EquipmentSlotGroup.FEET);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.HUNTERS_BOOTS.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.STEP_HEIGHT, (new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "905f3797-a937-48a0-89e0-7492943a9219"), 0.4, AttributeModifier.Operation.ADD_VALUE)), EquipmentSlotGroup.FEET);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.VULTURE_BOOTS.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "f43fe2fd-417c-4108-8c72-91aa179184f2"), 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)), EquipmentSlotGroup.FEET);
			}
			if (itemstack.getItem() == TheDeepVoidModItems.DARK_STEEL_BISMUTH_NETHERITE_BOOTS.get()) {
				_event.addModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED,
						(new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "7cf3def5-b317-48c4-9958-9cd0e57bb6a7"), (-0.05), AttributeModifier.Operation.ADD_MULTIPLIED_BASE)), EquipmentSlotGroup.FEET);
			}
		}
	}
}
