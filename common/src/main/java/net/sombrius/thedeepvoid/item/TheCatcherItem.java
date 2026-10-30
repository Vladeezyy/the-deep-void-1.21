
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.InteractionResult;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.OnlyIn;
import net.sombrius.thedeepvoid.procedures.TheCatcherLivingEntityIsHitWithItemProcedure;
import net.sombrius.thedeepvoid.procedures.TheCatcherRightclickedProcedure;
import net.sombrius.thedeepvoid.procedures.TheLatcherDescriptionProcedure;

public class TheCatcherItem extends Item {
	public TheCatcherItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(850).fireResistant().rarity(Rarity.COMMON)
				.attributes(ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 7, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
						.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.4, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()));
	}

	@Override
	@net.neoforged.api.distmarker.OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT) @net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
	public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, context, tooltipDisplay, list, flag);
		Entity entity = itemstack.getEntityRepresentation() != null ? itemstack.getEntityRepresentation() : Minecraft.getInstance().player;
		list.accept(Component.literal(TheLatcherDescriptionProcedure.execute()));
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		TheCatcherRightclickedProcedure.execute(world, entity, entity.getItemInHand(hand));
		return ar;
	}

	@Override
	public void hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		itemstack.hurtAndBreak(1, entity, entity.getUsedItemHand().asEquipmentSlot());
		TheCatcherLivingEntityIsHitWithItemProcedure.execute(sourceentity, itemstack);
	}
}
