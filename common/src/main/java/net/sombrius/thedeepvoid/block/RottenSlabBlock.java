
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class RottenSlabBlock extends SlabBlock {
	public RottenSlabBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_GRAY).sound(SoundType.BASALT).strength(2.2f, 16f).dynamicShape());
	}
}
