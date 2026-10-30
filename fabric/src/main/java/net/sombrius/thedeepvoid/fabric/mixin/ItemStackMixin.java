package net.sombrius.thedeepvoid.fabric.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import net.sombrius.thedeepvoid.port.bus.ItemAttributeModifierEvent;
import net.sombrius.thedeepvoid.port.bus.ModBus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * NeoForge's ItemAttributeModifierEvent: the stack's modifiers when they are applied (equipment changes), as NeoForge
 * asks for them; at that time the tags are bound and the config is loaded (a default component at init had neither).
 */
@Mixin(ItemStack.class)
abstract class ItemStackMixin {
	@ModifyExpressionValue(method = {"forEachModifier(Lnet/minecraft/world/entity/EquipmentSlotGroup;Lorg/apache/commons/lang3/function/TriConsumer;)V",
			"forEachModifier(Lnet/minecraft/world/entity/EquipmentSlot;Ljava/util/function/BiConsumer;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getOrDefault(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;"))
	private Object deepvoid$modifiers(Object modifiers) {
		if (!ModBus.hasListeners(ItemAttributeModifierEvent.class) || !(modifiers instanceof ItemAttributeModifiers base))
			return modifiers;
		ItemStack self = (ItemStack) (Object) this;
		ItemAttributeModifiers.Builder[] added = {null};
		ModBus.post(new ItemAttributeModifierEvent(self, (attribute, modifier, slot) -> {
			if (added[0] == null) {
				added[0] = ItemAttributeModifiers.builder();
				base.modifiers().forEach(entry -> added[0].add(entry.attribute(), entry.modifier(), entry.slot()));
			}
			added[0].add(attribute, modifier, slot);
		}));
		return added[0] == null ? modifiers : added[0].build();
	}
}
