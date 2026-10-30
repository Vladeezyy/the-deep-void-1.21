
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class DarkSteelScrapItem extends Item {
	public DarkSteelScrapItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().trimMaterial(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.TRIM_MATERIAL, net.minecraft.resources.Identifier.parse("the_deep_void:dark_steel_trim"))).stacksTo(64).rarity(Rarity.COMMON));
	}
}
