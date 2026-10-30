
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.InteractionResult;
import net.sombrius.thedeepvoid.port.PortUtil;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.entity.RottenDartProjectileEntity;
import net.sombrius.thedeepvoid.procedures.RottenDartRangedItemUsedProcedure;

public class RottenDartItem extends Item {
	public RottenDartItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public ItemUseAnimation getUseAnimation(ItemStack itemstack) {
		return ItemUseAnimation.SPEAR;
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
		return ar;
	}

	@Override
	public void onUseTick(Level world, LivingEntity entity, ItemStack itemstack, int count) {
		if (!world.isClientSide() && entity instanceof ServerPlayer player) {
			ItemStack stack = findAmmo(player);
			if (player.getAbilities().instabuild || stack != ItemStack.EMPTY) {
				RottenDartProjectileEntity projectile = RottenDartProjectileEntity.shoot(world, entity, world.getRandom());
				if (player.getAbilities().instabuild) {
					projectile.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
				} else {
					if (stack.isDamageableItem()) {
						PortUtil.hurtAndBreak(stack, 1, player);
					} else {
						stack.shrink(1);
					}
				}
				RottenDartRangedItemUsedProcedure.execute(entity, itemstack);
			}
			entity.releaseUsingItem();
		}
	}

	private ItemStack findAmmo(Player player) {
		ItemStack stack = ProjectileWeaponItem.getHeldProjectile(player, e -> e.getItem() == RottenDartProjectileEntity.PROJECTILE_ITEM.getItem());
		if (stack == ItemStack.EMPTY) {
			for (int i = 0; i < player.getInventory().getNonEquipmentItems().size(); i++) {
				ItemStack teststack = player.getInventory().getNonEquipmentItems().get(i);
				if (teststack != null && teststack.getItem() == RottenDartProjectileEntity.PROJECTILE_ITEM.getItem()) {
					stack = teststack;
					break;
				}
			}
		}
		return stack;
	}
}
