
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.InteractionResult;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.procedures.MiniatureCofferRightclickedProcedure;

public class MiniatureCofferItem extends Item {
	public MiniatureCofferItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).rarity(Rarity.EPIC));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Item.TooltipContext level, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, tooltipDisplay, list, flag);
		list.accept(Component.translatable("text.the_deep_void.port.creative_mode_only"));
		list.accept(Component.translatable("text.the_deep_void.port.summons_the_undertaker_naturally"));
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		MiniatureCofferRightclickedProcedure.execute(world, entity, entity.getItemInHand(hand));
		return ar;
	}
}
