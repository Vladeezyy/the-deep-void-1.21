
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.InteractionResult;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.procedures.IncenseBurnerDescriptionProcedure;
import net.sombrius.thedeepvoid.procedures.IncenseBurnerRightclickedProcedure;

public class IncenseBurnerItem extends Item {
	public IncenseBurnerItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).rarity(Rarity.UNCOMMON));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Item.TooltipContext level, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, tooltipDisplay, list, flag);
		Entity entity = itemstack.getEntityRepresentation();
		list.accept(Component.literal(IncenseBurnerDescriptionProcedure.execute()));
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		IncenseBurnerRightclickedProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, entity.getItemInHand(hand));
		return ar;
	}
}
