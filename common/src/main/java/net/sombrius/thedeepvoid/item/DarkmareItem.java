package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

public class DarkmareItem extends Item {
	public DarkmareItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).rarity(Rarity.EPIC).jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.parse("the_deep_void:darkmare"))));
	}
}
