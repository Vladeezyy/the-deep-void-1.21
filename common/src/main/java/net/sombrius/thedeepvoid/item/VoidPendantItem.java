
package net.sombrius.thedeepvoid.item;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.OnlyIn;
import net.sombrius.thedeepvoid.procedures.VoidPendantHasItemGlowingEffectProcedure;
import net.sombrius.thedeepvoid.procedures.VoidPendantItemInHandTickProcedure;
import net.sombrius.thedeepvoid.procedures.VoidPendantPlayerFinishesUsingItemProcedure;
import net.sombrius.thedeepvoid.procedures.VoidPendantRightclickedOnBlockProcedure;

public class VoidPendantItem extends Item {
	public VoidPendantItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(3).rarity(Rarity.UNCOMMON));
	}

	@Override
	@net.neoforged.api.distmarker.OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT) @net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
	public boolean isFoil(ItemStack itemstack) {
		return VoidPendantHasItemGlowingEffectProcedure.execute(itemstack);
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		VoidPendantPlayerFinishesUsingItemProcedure.execute(world, entity, entity.getItemInHand(hand));
		return ar;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		VoidPendantRightclickedOnBlockProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getItemInHand());
		return InteractionResult.SUCCESS;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
		int slot = equipmentSlot == null ? -1 : equipmentSlot.getIndex();
		if (selected)
			VoidPendantItemInHandTickProcedure.execute(entity, itemstack);
	}
}
