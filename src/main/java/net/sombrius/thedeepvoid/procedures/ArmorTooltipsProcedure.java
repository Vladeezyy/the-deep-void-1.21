package net.sombrius.thedeepvoid.procedures;

import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class ArmorTooltipsProcedure {
	public static void execute(Entity entity, ItemStack itemstack, List<Component> tooltip) {
		if (entity == null || tooltip == null)
			return;
		if (itemstack.getItem() == TheDeepVoidModItems.ROTTEN_BONE_ARMOR_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.ROTTEN_BONE_ARMOR_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.ROTTEN_BONE_ARMOR_LEGGINGS.get()
				|| itemstack.getItem() == TheDeepVoidModItems.ROTTEN_BONE_ARMOR_BOOTS.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ROTTEN_BONE_ARMOR_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ROTTEN_BONE_ARMOR_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ROTTEN_BONE_ARMOR_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ROTTEN_BONE_ARMOR_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.literal(" "));
					tooltip.add(Component.translatable("text.the_deep_void.port.on_armor_ability_key_pressed"));
					tooltip.add(Component.translatable("text.the_deep_void.port.makes_the_wearer_invisible_when_in_this"));
				} else {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.ONYX_ARMOR_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.ONYX_ARMOR_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.ONYX_ARMOR_LEGGINGS.get()
				|| itemstack.getItem() == TheDeepVoidModItems.ONYX_ARMOR_BOOTS.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ONYX_ARMOR_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ONYX_ARMOR_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ONYX_ARMOR_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ONYX_ARMOR_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.translatable("text.the_deep_void.port.when_in_darkness"));
					tooltip.add(Component.translatable("text.the_deep_void.port.attacks_get_repeated_with_half_the_damage"));
					tooltip.add(Component.translatable("text.the_deep_void.port.allows_the_wearer_to_see_in_darkness"));
				} else {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_LEGGINGS.get()
				|| itemstack.getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_BOOTS.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.translatable("text.the_deep_void.port.on_armor_ability_key_pressed"));
					tooltip.add(Component.translatable("text.the_deep_void.port.makes_the_wearer_invisible_when_in_this"));
					tooltip.add(Component.translatable("text.the_deep_void.port.when_in_darkness_or_when_under_the"));
					tooltip.add(Component.translatable("text.the_deep_void.port.the_armor_becomes_harder"));
					tooltip.add(Component.translatable("text.the_deep_void.port.upon_killing_an_entity"));
					tooltip.add(Component.translatable("text.the_deep_void.port.resets_the_cooldown"));
				} else {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.LAVENDITE_ARMOR_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.LAVENDITE_ARMOR_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.LAVENDITE_ARMOR_LEGGINGS.get()
				|| itemstack.getItem() == TheDeepVoidModItems.LAVENDITE_ARMOR_BOOTS.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.LAVENDITE_ARMOR_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.LAVENDITE_ARMOR_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.LAVENDITE_ARMOR_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.LAVENDITE_ARMOR_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.translatable("text.the_deep_void.port.when_attacked"));
					tooltip.add(Component.translatable("text.the_deep_void.port.deflects_any_projectile"));
				} else {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.GRIM_LAVENDITE_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.GRIM_LAVENDITE_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.GRIM_LAVENDITE_LEGGINGS.get()
				|| itemstack.getItem() == TheDeepVoidModItems.GRIM_LAVENDITE_BOOTS.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_LAVENDITE_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_LAVENDITE_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_LAVENDITE_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_LAVENDITE_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.translatable("text.the_deep_void.port.when_attacked"));
					tooltip.add(Component.translatable("text.the_deep_void.port.deflects_any_projectile_back_at_the_shooter"));
				} else {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.BISMUTH_NETHERITE_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.BISMUTH_NETHERITE_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.BISMUTH_NETHERITE_LEGGINGS.get()
				|| itemstack.getItem() == TheDeepVoidModItems.BISMUTH_NETHERITE_BOOTS.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BISMUTH_NETHERITE_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BISMUTH_NETHERITE_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BISMUTH_NETHERITE_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.BISMUTH_NETHERITE_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.translatable("text.the_deep_void.port.when_worn"));
					tooltip.add(Component.translatable("text.the_deep_void.port.xp_heals_the_wearer"));
				} else {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.VOIDRIUM_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_HELMET.get()) {
			if (Screen.hasShiftDown()) {
				tooltip.add(Component.translatable("text.the_deep_void.port.when_in_darkness"));
				tooltip.add(Component.translatable("text.the_deep_void.port.allows_the_wearer_to_see_in_darkness"));
			} else {
				if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_HELMET.get()
						&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_CHESTPLATE.get()
						&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_LEGGINGS.get()
						&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_BOOTS.get())
						&& !((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_HELMET.get()
								&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_CHESTPLATE.get()
								&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_LEGGINGS.get()
								&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_BOOTS.get())) {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.VOIDRIUM_LEGGINGS.get() || itemstack.getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_LEGGINGS.get()) {
			if (Screen.hasShiftDown()) {
				tooltip.add(Component.translatable("text.the_deep_void.port.when_in_darkness"));
				tooltip.add(Component.translatable("text.the_deep_void.port.the_wearer_can_run_faster"));
			} else {
				if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_HELMET.get()
						&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_CHESTPLATE.get()
						&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_LEGGINGS.get()
						&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_BOOTS.get())
						&& !((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_HELMET.get()
								&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_CHESTPLATE.get()
								&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_LEGGINGS.get()
								&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_BOOTS.get())) {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.VOIDRIUM_BOOTS.get() || itemstack.getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_BOOTS.get()) {
			if (Screen.hasShiftDown()) {
				tooltip.add(Component.translatable("text.the_deep_void.port.when_worn"));
				tooltip.add(Component.translatable("text.the_deep_void.port.negates_fall_damage"));
			} else {
				if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_HELMET.get()
						&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_CHESTPLATE.get()
						&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_LEGGINGS.get()
						&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_BOOTS.get())
						&& !((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_HELMET.get()
								&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_CHESTPLATE.get()
								&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_LEGGINGS.get()
								&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_BOOTS.get())) {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.VOIDRIUM_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_CHESTPLATE.get()) {
			if (Screen.hasShiftDown()) {
				tooltip.add(Component.translatable("text.the_deep_void.port.when_at_half_health"));
				tooltip.add(Component.translatable("text.the_deep_void.port.the_wearer_is_granted_resistance_and_strength"));
			} else {
				if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_HELMET.get()
						&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_CHESTPLATE.get()
						&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_LEGGINGS.get()
						&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_BOOTS.get())
						&& !((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_HELMET.get()
								&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_CHESTPLATE.get()
								&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_LEGGINGS.get()
								&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_BOOTS.get())) {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.VOIDRIUM_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.VOIDRIUM_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.VOIDRIUM_LEGGINGS.get()
				|| itemstack.getItem() == TheDeepVoidModItems.VOIDRIUM_BOOTS.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.VOIDRIUM_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.translatable("text.the_deep_void.port.when_in_darkness"));
					tooltip.add(Component.translatable("text.the_deep_void.port.attacks_get_repeated_with_half_the_damage"));
					tooltip.add(Component.translatable("text.the_deep_void.port.on_armor_ability_key_pressed"));
					tooltip.add(Component.translatable("text.the_deep_void.port.teleports_the_wearer"));
					tooltip.add(Component.translatable("text.the_deep_void.port.when_attacked"));
					tooltip.add(Component.translatable("text.the_deep_void.port.damages_the_attacker"));
				} else {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.VOID_CLOAK_CHESTPLATE.get()) {
			if (Screen.hasShiftDown()) {
				tooltip.add(Component.translatable("text.the_deep_void.port.on_armor_ability_key_pressed"));
				tooltip.add(Component.translatable("text.the_deep_void.port.allows_the_wearer_to_phase_through_walls"));
			} else {
				tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.FLESH_ARMOR_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.FLESH_ARMOR_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.FLESH_ARMOR_LEGGINGS.get()
				|| itemstack.getItem() == TheDeepVoidModItems.FLESH_ARMOR_BOOTS.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.FLESH_ARMOR_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.FLESH_ARMOR_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.FLESH_ARMOR_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.FLESH_ARMOR_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.translatable("text.the_deep_void.port.when_attacked"));
					tooltip.add(Component.translatable("text.the_deep_void.port.the_armor_becomes_more_resilient_and_heavier"));
					tooltip.add(Component.translatable("text.the_deep_void.port.on_armor_ability_key_pressed"));
					tooltip.add(Component.translatable("text.the_deep_void.port.unleashes_a_devastating_roar_that_damages_every"));
				} else {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.ALL_SEEING_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.ALL_SEEING_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.ALL_SEEING_LEGGINGS.get()
				|| itemstack.getItem() == TheDeepVoidModItems.ALL_SEEING_BOOTS.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ALL_SEEING_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ALL_SEEING_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ALL_SEEING_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ALL_SEEING_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.translatable("text.the_deep_void.port.when_worn_2"));
					tooltip.add(Component.translatable("text.the_deep_void.port.any_player_around_the_wearer_will_glow"));
					tooltip.add(Component.translatable("text.the_deep_void.port.when_entity_targets_the_wearer"));
					tooltip.add(Component.translatable("text.the_deep_void.port.the_entity_is_given_the_glowing_effect"));
					tooltip.add(Component.translatable("text.the_deep_void.port.the_wearer_gets_empowered_with_strength"));
					tooltip.add(Component.translatable("text.the_deep_void.port.on_armor_ability_key_pressed"));
					tooltip.add(Component.translatable("text.the_deep_void.port.allows_the_wearer_to_dash"));
				} else {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.CRAWLER_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.CRAWLER_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.CRAWLER_LEGGINGS.get()
				|| itemstack.getItem() == TheDeepVoidModItems.CRAWLER_BOOTS.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CRAWLER_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CRAWLER_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CRAWLER_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CRAWLER_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.translatable("text.the_deep_void.port.when_worn"));
					tooltip.add(Component.translatable("text.the_deep_void.port.immunity_to_rot"));
					tooltip.add(Component.translatable("text.the_deep_void.port.the_wearer_becomes_invisible_faster_and_more"));
					tooltip.add(Component.translatable("text.the_deep_void.port.bone_crawlers_won_t_attack_the_wearer"));
					tooltip.add(Component.translatable("text.the_deep_void.port.eating_bone_mush_or_other_food_made"));
				} else {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.GRIM_CRAWLER_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.CRAWLER_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.CRAWLER_ROYALTY_CHESTPLATE.get()) {
			if (Screen.hasShiftDown()) {
				tooltip.add(Component.translatable("text.the_deep_void.port.on_armor_ability_key_pressed"));
				tooltip.add(Component.translatable("text.the_deep_void.port.the_wearer_can_slow_fall"));
			} else {
				if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_CRAWLER_HELMET.get()
						&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_CRAWLER_CHESTPLATE.get()
						&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_CRAWLER_LEGGINGS.get()
						&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_CRAWLER_BOOTS.get())
						&& !((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CRAWLER_HELMET.get()
								&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CRAWLER_CHESTPLATE.get()
								&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CRAWLER_LEGGINGS.get()
								&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CRAWLER_BOOTS.get())) {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.GRIM_CRAWLER_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.GRIM_CRAWLER_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.GRIM_CRAWLER_LEGGINGS.get()
				|| itemstack.getItem() == TheDeepVoidModItems.GRIM_CRAWLER_BOOTS.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_CRAWLER_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_CRAWLER_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_CRAWLER_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_CRAWLER_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.translatable("text.the_deep_void.port.when_worn"));
					tooltip.add(Component.translatable("text.the_deep_void.port.immunity_to_rot"));
					tooltip.add(Component.translatable("text.the_deep_void.port.the_wearer_becomes_invisible_faster_and_more"));
					tooltip.add(Component.translatable("text.the_deep_void.port.bone_crawlers_won_t_attack_the_wearer"));
					tooltip.add(Component.translatable("text.the_deep_void.port.when_in_darkness"));
					tooltip.add(Component.translatable("text.the_deep_void.port.all_attacks_inflict_rot_i"));
					tooltip.add(Component.translatable("text.the_deep_void.port.allows_the_wearer_to_see_in_darkness"));
				} else {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.CRAWLER_ROYALTY_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.CRAWLER_ROYALTY_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.CRAWLER_ROYALTY_LEGGINGS.get()
				|| itemstack.getItem() == TheDeepVoidModItems.CRAWLER_ROYALTY_BOOTS.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CRAWLER_ROYALTY_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CRAWLER_ROYALTY_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CRAWLER_ROYALTY_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CRAWLER_ROYALTY_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.translatable("text.the_deep_void.port.when_worn"));
					tooltip.add(Component.translatable("text.the_deep_void.port.immunity_to_rot"));
					tooltip.add(Component.translatable("text.the_deep_void.port.the_wearer_becomes_invisible_faster_and_more"));
					tooltip.add(Component.translatable("text.the_deep_void.port.bone_crawlers_won_t_attack_the_wearer"));
					tooltip.add(Component.translatable("text.the_deep_void.port.all_attacks_inflict_rot_i"));
					tooltip.add(Component.translatable("text.the_deep_void.port.when_in_darkness"));
					tooltip.add(Component.translatable("text.the_deep_void.port.allows_the_wearer_to_see_in_darkness"));
				} else {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.CULT_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.CULT_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.CULT_LEGGINGS.get()
				|| itemstack.getItem() == TheDeepVoidModItems.CULT_BOOTS.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CULT_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CULT_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CULT_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.CULT_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.translatable("text.the_deep_void.port.when_worn"));
					tooltip.add(Component.translatable("text.the_deep_void.port.grants_void_s_blessing"));
					tooltip.add(Component.translatable("text.the_deep_void.port.the_wearer_runs_faster"));
					tooltip.add(Component.translatable("text.the_deep_void.port.light_damages_the_wearer"));
					tooltip.add(Component.translatable("text.the_deep_void.port.when_attacked"));
					tooltip.add(Component.translatable("text.the_deep_void.port.doubles_any_damage_received"));
				} else {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.WEAVER_MASK_HELMET.get()) {
			if (Screen.hasShiftDown()) {
				tooltip.add(Component.translatable("text.the_deep_void.port.when_worn"));
				tooltip.add(Component.translatable("text.the_deep_void.port.built_overseers_around_the_wearer_are_empowered"));
				tooltip.add(Component.translatable("text.the_deep_void.port.the_wearer_can_no_longer_heal_naturally"));
				tooltip.add(Component.translatable("text.the_deep_void.port.when_killing_a_foe"));
				tooltip.add(Component.translatable("text.the_deep_void.port.the_wearer_s_health_is_enhanced_until"));
			} else {
				tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.WEAVER_ROBE_CHESTPLATE.get()) {
			if (Screen.hasShiftDown()) {
				tooltip.add(Component.translatable("text.the_deep_void.port.when_attacking"));
				tooltip.add(Component.translatable("text.the_deep_void.port.each_4_hit_the_wearer_is_teleported"));
			} else {
				tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.WEAVER_LEGGINGS_LEGGINGS.get()) {
			if (Screen.hasShiftDown()) {
				tooltip.add(Component.translatable("text.the_deep_void.port.when_attacked"));
				tooltip.add(Component.translatable("text.the_deep_void.port.the_wearer_has_a_chance_to_dodge"));
			} else {
				tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.WEAVER_BOOTS_BOOTS.get()) {
			if (Screen.hasShiftDown()) {
				tooltip.add(Component.translatable("text.the_deep_void.port.on_no_gravity_key_pressed"));
				tooltip.add(Component.translatable("text.the_deep_void.port.allows_the_wearer_to_walk_on_air"));
				tooltip.add(Component.translatable("text.the_deep_void.port.on_armor_ability_key_pressed"));
				tooltip.add(Component.translatable("text.the_deep_void.port.the_wearer_will_dash_where_they_re"));
			} else {
				tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
			}
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.WEAVER_MASK_HELMET.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.WEAVER_ROBE_CHESTPLATE.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.WEAVER_LEGGINGS_LEGGINGS.get()
				&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.WEAVER_BOOTS_BOOTS.get()) {
			if (itemstack.getItem() == TheDeepVoidModItems.WEAVER_MASK_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.WEAVER_ROBE_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.WEAVER_LEGGINGS_LEGGINGS.get()
					|| itemstack.getItem() == TheDeepVoidModItems.WEAVER_BOOTS_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.translatable("text.the_deep_void.port.full_armor"));
					tooltip.add(Component.translatable("text.the_deep_void.port.slowly_withers_away_the_soul_of_nearby"));
				}
			}
		}
		if (itemstack.getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_HELMET.get() || itemstack.getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_LEGGINGS.get()
				|| itemstack.getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_BOOTS.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_HELMET.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_CHESTPLATE.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_LEGGINGS.get()
					&& (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SACRED_VOIDRIUM_BOOTS.get()) {
				if (Screen.hasShiftDown()) {
					tooltip.add(Component.translatable("text.the_deep_void.port.when_in_darkness"));
					tooltip.add(Component.translatable("text.the_deep_void.port.attacks_get_repeated_with_a_bit_more"));
					tooltip.add(Component.translatable("text.the_deep_void.port.on_armor_ability_key_pressed"));
					tooltip.add(Component.translatable("text.the_deep_void.port.teleports_the_wearer_damaging_the_very_soul"));
					tooltip.add(Component.translatable("text.the_deep_void.port.when_attacked"));
					tooltip.add(Component.translatable("text.the_deep_void.port.damages_the_attacker"));
					tooltip.add(Component.translatable("text.the_deep_void.port.summons_shadow_hands_to_attack"));
				} else {
					tooltip.add(Component.translatable("text.the_deep_void.port.press_shift_to_show_abilities"));
				}
			}
		}
	}
}
