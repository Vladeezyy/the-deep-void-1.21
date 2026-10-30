
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class SulfurItem extends Item {
	public SulfurItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().trimMaterial(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.TRIM_MATERIAL, net.minecraft.resources.Identifier.parse("the_deep_void:sulfur_trim"))).stacksTo(64).rarity(Rarity.COMMON));
	}
}
