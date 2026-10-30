
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class UmbriumGemItem extends Item {
	public UmbriumGemItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(64).fireResistant().rarity(Rarity.RARE));
	}
}
