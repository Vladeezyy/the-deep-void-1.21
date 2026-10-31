package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TridentItem;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class NecrosedSkinBackpackToolSlotItemPutProcedure {
	public static boolean execute(ItemStack itemstack) {
		if (itemstack.is(net.minecraft.tags.ItemTags.SWORDS) || itemstack.is(net.minecraft.tags.ItemTags.PICKAXES) || itemstack.is(net.minecraft.tags.ItemTags.AXES) || itemstack.is(net.minecraft.tags.ItemTags.SHOVELS) || itemstack.is(net.minecraft.tags.ItemTags.HOES)
				|| itemstack.getItem() instanceof BowItem || itemstack.getItem() instanceof CrossbowItem || itemstack.getItem() instanceof ShieldItem || itemstack.getItem() instanceof TridentItem
				|| itemstack.getItem() == TheDeepVoidModItems.ONYX_FLARE_GUN.get() || itemstack.getItem() == Items.SHEARS || itemstack.getItem() == TheDeepVoidModItems.LICKER_HOOK.get()
				|| itemstack.getItem() == TheDeepVoidModItems.GRIM_LICKER_HOOK.get() || itemstack.getItem() == TheDeepVoidModItems.VENGEFUL_LICKER_HOOK.get() || itemstack.getItem() == TheDeepVoidModItems.BONE_CLIMBING_PICK.get()) {
			return false;
		}
		return true;
	}
}
