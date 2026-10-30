
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.InteractionResult;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
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
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.OnlyIn;
import net.sombrius.thedeepvoid.procedures.EntityImmobilizerItemInInventoryTickProcedure;
import net.sombrius.thedeepvoid.procedures.EntityImmobilizerRightclickedProcedure;

public class EntityImmobilizerItem extends Item {
	public EntityImmobilizerItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).rarity(Rarity.EPIC));
	}

	@Override
	@net.neoforged.api.distmarker.OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT) @net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
	public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, context, tooltipDisplay, list, flag);
		list.accept(Component.translatable("text.the_deep_void.port.creative_mode_only"));
		list.accept(Component.translatable("text.the_deep_void.port.force_motion_vector_x_y_z_to"));
		list.accept(Component.translatable("text.the_deep_void.port.right_click_to_enable_glowing"));
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		EntityImmobilizerRightclickedProcedure.execute(entity, entity.getItemInHand(hand));
		return ar;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
		int slot = equipmentSlot == null ? -1 : equipmentSlot.getIndex();
		EntityImmobilizerItemInInventoryTickProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
	}
}
