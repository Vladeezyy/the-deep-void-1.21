package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

public class LurkingMusicDiscItem extends Item {
	public LurkingMusicDiscItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.parse("the_deep_void:lurking_music_disc"))));
	}
}
