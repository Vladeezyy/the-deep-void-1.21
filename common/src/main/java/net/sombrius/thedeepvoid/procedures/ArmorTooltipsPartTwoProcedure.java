package net.sombrius.thedeepvoid.procedures;

import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class ArmorTooltipsPartTwoProcedure {
	public static void execute(Entity entity, ItemStack itemstack, List<Component> tooltip) {
		if (entity == null || tooltip == null)
			return;
		if (itemstack.getItem() == TheDeepVoidModItems.HARVESTER_MASK_HELMET.get()) {
			if (Screen.hasShiftDown()) {
				tooltip.add(Component.translatable("text.the_deep_void.port.counts_toward_the_full_set"));
				tooltip.add(Component.translatable("text.the_deep_void.port.when_worn"));
				tooltip.add(Component.translatable("text.the_deep_void.port.the_wearer_is_immune_to_hexed"));
				tooltip.add(Component.translatable("text.the_deep_void.port.when_attacking_a_foe_with_hexed_bare"));
				tooltip.add(Component.literal(
						" \u00A79-Removes Hexed from the foe, dealing more damage than if the effect would end naturally. The removed Hexed transforms into Absorption with the level based on the damage build up of Hexed. The Absorption has no limits, but is temporary"));
				tooltip.add(Component.translatable("text.the_deep_void.port.upon_killing_a_foe"));
				tooltip.add(Component.translatable("text.the_deep_void.port.transforms_their_soul_into_a_lethal_projectile"));
			} else {
				tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
			}
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HARVESTER_MASK_HELMET.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.WEAVER_ROBE_CHESTPLATE.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.WEAVER_LEGGINGS_LEGGINGS.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.WEAVER_BOOTS_BOOTS.get()) {
			if (itemstack.getItem() == TheDeepVoidModItems.HARVESTER_MASK_HELMET.get()
					|| (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.WEAVER_ROBE_CHESTPLATE.get()
					|| (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.WEAVER_LEGGINGS_LEGGINGS.get()
					|| (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.WEAVER_BOOTS_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.translatable("text.the_deep_void.port.full_armor"));
					tooltip.add(Component.translatable("text.the_deep_void.port.slowly_withers_away_the_soul_of_nearby"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.SOUL_WINGS_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.SOUL_WINGS_FLY_CHESTPLATE.get()) {
			if (Screen.hasShiftDown()) {
				tooltip.add(Component.translatable("text.the_deep_void.port.on_armor_ability_key_pressed"));
				tooltip.add(Component.translatable("text.the_deep_void.port.fly_vertically_rapidly_but_with_bad_horizontal"));
				tooltip.add(Component.translatable("text.the_deep_void.port.sneak_while_flying_to_fly_straight_upward"));
				tooltip.add(Component.translatable("text.the_deep_void.port.when_worn"));
				tooltip.add(Component.translatable("text.the_deep_void.port.negates_fall_damage"));
			} else {
				tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.BLOODY_RIB_CAGE_CHESTPLATE.get()) {
			if (Screen.hasShiftDown()) {
				tooltip.add(Component.translatable("text.the_deep_void.port.when_worn"));
				tooltip.add(Component.translatable("text.the_deep_void.port.fleshkin_won_t_attack_the_wearer"));
				tooltip.add(Component.translatable("text.the_deep_void.port.grants_immunity_to_poison_and_nausea"));
				tooltip.add(Component.translatable("text.the_deep_void.port.teeth_won_t_damage_the_wearer_teeth"));
			} else {
				tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
			}
		}
	}
}
