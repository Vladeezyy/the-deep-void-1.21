
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.InteractionResult;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.procedures.BallAndChainDescriptionProcedure;
import net.sombrius.thedeepvoid.procedures.BallAndChainItemInInventoryTickProcedure;
import net.sombrius.thedeepvoid.procedures.BallAndChainRightclickedProcedure;

public class BallAndChainItem extends Item {
	public BallAndChainItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).rarity(Rarity.COMMON).attributes(ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 11d, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.4, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()));
	}


	@Override
	public void appendHoverText(ItemStack itemstack, Item.TooltipContext level, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, tooltipDisplay, list, flag);
		Entity entity = itemstack.getEntityRepresentation();
		list.accept(Component.literal(BallAndChainDescriptionProcedure.execute()));
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		BallAndChainRightclickedProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, entity.getItemInHand(hand));
		return ar;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
		int slot = equipmentSlot == null ? -1 : equipmentSlot.getIndex();
		BallAndChainItemInInventoryTickProcedure.execute(itemstack);
	}
}
