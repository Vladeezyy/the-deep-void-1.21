
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class VoidLensItem extends Item {
	public VoidLensItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(64).rarity(Rarity.COMMON));
	}
}
