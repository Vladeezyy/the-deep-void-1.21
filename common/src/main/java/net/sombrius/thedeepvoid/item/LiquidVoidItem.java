
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModFluids;

public class LiquidVoidItem extends BucketItem {
	public LiquidVoidItem() {
		super(TheDeepVoidModFluids.LIQUID_VOID.get(), net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON));
	}
}
