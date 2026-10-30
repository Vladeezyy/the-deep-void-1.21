
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class RottenBoneItem extends Item {
	public RottenBoneItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().trimMaterial(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.TRIM_MATERIAL, net.minecraft.resources.Identifier.parse("the_deep_void:rotten_trim"))).stacksTo(64).rarity(Rarity.COMMON));
	}
}
