package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiConsumer;

/** The attribute modifiers of a stack are computed (server and client) */
public class ItemAttributeModifierEvent extends Event {
	private final ItemStack stack;
	private final Sink sink;

	public interface Sink {
		void add(Holder<Attribute> attribute, AttributeModifier modifier, EquipmentSlotGroup slot);
	}

	public ItemAttributeModifierEvent(ItemStack stack, Sink sink) {
		this.stack = stack;
		this.sink = sink;
	}

	public ItemStack getItemStack() {
		return this.stack;
	}

	public void addModifier(Holder<Attribute> attribute, AttributeModifier modifier, EquipmentSlotGroup slot) {
		this.sink.add(attribute, modifier, slot);
	}
}
