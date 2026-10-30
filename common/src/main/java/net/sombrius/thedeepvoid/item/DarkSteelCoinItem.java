
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class DarkSteelCoinItem extends Item {
	public DarkSteelCoinItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(64).fireResistant().rarity(Rarity.UNCOMMON));
	}
}
