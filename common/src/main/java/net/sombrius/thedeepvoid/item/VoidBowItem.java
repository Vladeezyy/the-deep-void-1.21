
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.InteractionResult;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.sombrius.thedeepvoid.port.PortUtil;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.entity.VoidArrowEntity;
import net.sombrius.thedeepvoid.procedures.VoidBowInInventoryProcedure;
import net.sombrius.thedeepvoid.procedures.VoidBowRightClickedProcedure;
import net.sombrius.thedeepvoid.procedures.VoidBowStoppedUsingProcedure;

public class VoidBowItem extends Item {
	public VoidBowItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().enchantable(12).durability(850).rarity(Rarity.COMMON));
	}

	@Override
	public ItemUseAnimation getUseAnimation(ItemStack itemstack) {
		return ItemUseAnimation.BOW;
	}

	@Override
	public int getUseDuration(ItemStack itemstack, LivingEntity entity) {
		return 72000;
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = InteractionResult.FAIL;
		if (entity.getAbilities().instabuild || findAmmo(entity) != ItemStack.EMPTY) {
			ar = InteractionResult.SUCCESS;
			entity.startUsingItem(hand);
		}
		VoidBowRightClickedProcedure.execute(entity.getItemInHand(hand));
		return ar;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
		int slot = equipmentSlot == null ? -1 : equipmentSlot.getIndex();
		VoidBowInInventoryProcedure.execute(entity, itemstack);
	}

	@Override
	public boolean releaseUsing(ItemStack itemstack, Level world, LivingEntity entity, int time) {
		VoidBowStoppedUsingProcedure.execute(world, entity, itemstack);
		if (!world.isClientSide() && entity instanceof ServerPlayer player) {
			float pullingPower = BowItem.getPowerForTime(this.getUseDuration(itemstack, entity) - time);
			if (pullingPower < 0.1)
				return true;
			ItemStack stack = findAmmo(player);
			if (player.getAbilities().instabuild || stack != ItemStack.EMPTY) {
				VoidArrowEntity projectile = VoidArrowEntity.shoot(world, entity, world.getRandom(), pullingPower);
				itemstack.hurtAndBreak(1, entity, entity.getUsedItemHand().asEquipmentSlot());
				if (player.getAbilities().instabuild) {
					projectile.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
				} else {
					if (stack.isDamageableItem()) {
						PortUtil.hurtAndBreak(stack, 1, player);
					} else {
						stack.shrink(1);
					}
				}
			}
		}
		return true;
	}

	private ItemStack findAmmo(Player player) {
		ItemStack stack = ProjectileWeaponItem.getHeldProjectile(player, e -> e.getItem() == VoidArrowEntity.PROJECTILE_ITEM.getItem());
		if (stack == ItemStack.EMPTY) {
			for (int i = 0; i < player.getInventory().getNonEquipmentItems().size(); i++) {
				ItemStack teststack = player.getInventory().getNonEquipmentItems().get(i);
				if (teststack != null && teststack.getItem() == VoidArrowEntity.PROJECTILE_ITEM.getItem()) {
					stack = teststack;
					break;
				}
			}
		}
		return stack;
	}
}
