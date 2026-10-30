
package net.sombrius.thedeepvoid.item;

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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.entity.InvisibleEntity;
import net.sombrius.thedeepvoid.procedures.BlackHoleCanUseRangedItemProcedure;
import net.sombrius.thedeepvoid.procedures.BlackHoleInventoryProcedure;
import net.sombrius.thedeepvoid.procedures.BlackHolePlayerFinishesUsingItemProcedure;
import net.sombrius.thedeepvoid.procedures.BlackHoleRightClickProcedure;
import net.sombrius.thedeepvoid.procedures.BlackHoleStopUsingProcedure;

public class BlackHoleItem extends Item {
	public BlackHoleItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(1561).fireResistant().rarity(Rarity.COMMON));
	}

	@Override
	public int getUseDuration(ItemStack itemstack, LivingEntity entity) {
		return 25;
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = InteractionResult.FAIL;
		if (BlackHoleCanUseRangedItemProcedure.execute(entity.getItemInHand(hand)))
			if (entity.getAbilities().instabuild || findAmmo(entity) != ItemStack.EMPTY) {
				ar = InteractionResult.SUCCESS;
				entity.startUsingItem(hand);
			}
		BlackHoleRightClickProcedure.execute(world, entity, entity.getItemInHand(hand));
		return ar;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		BlackHolePlayerFinishesUsingItemProcedure.execute(entity, itemstack);
		return retval;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
		int slot = equipmentSlot == null ? -1 : equipmentSlot.getIndex();
		BlackHoleInventoryProcedure.execute(world, entity, itemstack);
	}

	@Override
	public boolean releaseUsing(ItemStack itemstack, Level world, LivingEntity entity, int time) {
		BlackHoleStopUsingProcedure.execute(itemstack);
		if (!world.isClientSide() && entity instanceof ServerPlayer player) {
			ItemStack stack = findAmmo(player);
			if (player.getAbilities().instabuild || stack != ItemStack.EMPTY) {
				InvisibleEntity projectile = InvisibleEntity.shoot(world, entity, world.getRandom());
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
		ItemStack stack = ProjectileWeaponItem.getHeldProjectile(player, e -> e.getItem() == InvisibleEntity.PROJECTILE_ITEM.getItem());
		if (stack == ItemStack.EMPTY) {
			for (int i = 0; i < player.getInventory().getNonEquipmentItems().size(); i++) {
				ItemStack teststack = player.getInventory().getNonEquipmentItems().get(i);
				if (teststack != null && teststack.getItem() == InvisibleEntity.PROJECTILE_ITEM.getItem()) {
					stack = teststack;
					break;
				}
			}
		}
		return stack;
	}
}
