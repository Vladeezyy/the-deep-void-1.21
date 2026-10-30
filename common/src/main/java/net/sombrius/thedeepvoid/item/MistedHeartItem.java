
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.sombrius.thedeepvoid.procedures.MistedHeartLivingEntityIsHitWithItemProcedure;

public class MistedHeartItem extends Item {
	public MistedHeartItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON));
	}

	@Override
	public void hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		super.hurtEnemy(itemstack, entity, sourceentity);
		MistedHeartLivingEntityIsHitWithItemProcedure.execute(entity, sourceentity);
	}
}
