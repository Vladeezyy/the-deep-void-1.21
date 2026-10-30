
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class SmoothAncientDeepslateSlabBlock extends SlabBlock {
	public SmoothAncientDeepslateSlabBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.DEEPSLATE).sound(SoundType.DEEPSLATE_TILES).strength(4.5f, 7.5f).requiresCorrectToolForDrops().dynamicShape());
	}
}
