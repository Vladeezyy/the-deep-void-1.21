package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.Tag;

/** Saved to and loaded from a tag (NeoForge's interface) */
public interface INBTSerializable<T extends Tag> {
	T serializeNBT(HolderLookup.Provider provider);

	void deserializeNBT(HolderLookup.Provider provider, T nbt);
}
