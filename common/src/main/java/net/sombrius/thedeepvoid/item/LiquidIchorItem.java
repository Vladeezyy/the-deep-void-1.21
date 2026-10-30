
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModFluids;

public class LiquidIchorItem extends BucketItem {
	public LiquidIchorItem() {
		super(TheDeepVoidModFluids.LIQUID_ICHOR.get(), net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON));
	}
}
