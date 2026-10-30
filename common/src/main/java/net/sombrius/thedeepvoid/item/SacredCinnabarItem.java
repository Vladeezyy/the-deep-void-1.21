
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class SacredCinnabarItem extends Item {
	public SacredCinnabarItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(64).fireResistant().rarity(Rarity.UNCOMMON));
	}
}
