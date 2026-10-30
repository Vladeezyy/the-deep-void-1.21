
package net.sombrius.thedeepvoid.item;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.OnlyIn;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.procedures.DevourerShieldSpecialInformationProcedure;

public class DevourerShieldItem extends ShieldItem {
	public DevourerShieldItem() {
		super(new Item.Properties().durability(540).fireResistant());
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemstack, ItemStack repairitem) {
		return Ingredient
				.of(new ItemStack(TheDeepVoidModBlocks.FLESH_BLOCK.get()), new ItemStack(TheDeepVoidModBlocks.PUSTULENT_FLESH_BLOCK.get()), new ItemStack(TheDeepVoidModBlocks.TENDRILS.get()), new ItemStack(TheDeepVoidModItems.DEVOURER_TENDRIL.get()))
				.test(repairitem);
	}

	@Override
	@net.neoforged.api.distmarker.OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT) @net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
	public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, context, list, flag);
		Entity entity = itemstack.getEntityRepresentation() != null ? itemstack.getEntityRepresentation() : Minecraft.getInstance().player;
		list.add(Component.literal(DevourerShieldSpecialInformationProcedure.execute()));
	}
}
